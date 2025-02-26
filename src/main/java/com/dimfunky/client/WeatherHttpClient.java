package com.dimfunky.client;

import com.dimfunky.exception.WeatherServiceException;
import com.dimfunky.mapper.WeatherMapper;
import com.dimfunky.model.CityCoords;
import com.dimfunky.model.WeatherData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Http client for receiving data from <a href="https://openweathermap.org">OpenWeatherMap</a>
 * <p>
 * Contains methods for geocoding cities names into the exact geographical coordinates
 * and retrieving weather data using this coordinates
 */

public class WeatherHttpClient {


    /**
     * Geocoding URL template
     */
    private static final String GEO_DECODE_URL = "http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s";


    /**
     * Weather data URL template with free access
     */
    private static final String WEATHER_DATA_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s";
    private final HttpClient httpClient;

    public WeatherHttpClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public WeatherHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * <pre>
     * </pre>
     * Method for retrieving weather data using coordinates from {@link CityCoords} object.
     * <p>
     * Builds the request with parameters and sends it to {@link com.dimfunky.client.WeatherHttpClient#WEATHER_DATA_URL}.
     * Maps the JSON response to a {@link WeatherData} object.
     * <p>
     * Due to response from the 'Data API' may include the name of a specific location within the city
     * (for example, api may return data with name "Palais-Royal" for "Paris" coordinates),
     * the name is taken from the {@link CityCoords} response.
     * <p>
     * Accept parameters:
     * <p>
     * {@link CityCoords}  {@code cityCoords} - object with geographical coordinates of location.
     * <p>
     * {@code String apiKey} - Your API key.
     *
     * @param cityCoords
     * @param apiKey
     * @return {@link WeatherData}
     * @throws com.dimfunky.exception.WeatherServiceException if API returns error (response status code >= 400)
     */
    public WeatherData getWeatherData(final CityCoords cityCoords, final String apiKey) {
        String path = String.format(WEATHER_DATA_URL, cityCoords.lat(), cityCoords.lon(), apiKey);
        String stringResponse = makeGetRequest(path);
        return WeatherMapper.toWeatherData(stringResponse)
                .name(cityCoords.name());
    }

    /**
     * <pre>
     * </pre>
     * Method for retrieving exact geographical coordinates by the specified name of a location
     * <p>
     * Encodes spaces in {@code cityName} for the HTTP request. Builds the full request with parameters
     * and sends it. Maps the JSON response to a {@link CityCoords} object.
     * <p>
     * Accept parameters:
     * <p>
     * {@code String cityName} - Name of locations or city.
     * <p>
     * {@code String apiKey} - Your API key.
     *
     * @param cityName
     * @param apiKey
     * @return {@link CityCoords}
     * @throws com.dimfunky.exception.WeatherServiceException if retrieved empty city coords list
     *                                                        or API returns error (response status code >= 400)
     */
    public CityCoords getCoordsByCityName(final String cityName, final String apiKey) {
        var encodedCityName = cityName.replace(" ", "%20");
        var path = String.format(GEO_DECODE_URL, encodedCityName, apiKey);
        var stringResponse = makeGetRequest(path);
        if (stringResponse.equals("[]")) {
            throw new WeatherServiceException("The city was not found. Try to write name in another format");
        }
        return WeatherMapper.toCityCoords(stringResponse);
    }

    private String makeGetRequest(final String path) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(path))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                throw new WeatherServiceException("openweathermap.org returned an error: " + response.body());
            }
            return response.body();
        } catch (Exception e) {
            throw new WeatherServiceException(e.getMessage(), e);
        }
    }
}
