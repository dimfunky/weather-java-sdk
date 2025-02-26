package com.dimfunky.template;

import com.dimfunky.cache.MemoryCache;
import com.dimfunky.client.WeatherHttpClient;
import com.dimfunky.environment.WeatherEnvironment;
import com.dimfunky.mapper.WeatherMapper;
import com.dimfunky.model.CityCoords;
import com.dimfunky.model.WeatherData;
import com.dimfunky.validation.InputValidation;

import java.time.Instant;
import java.util.Comparator;
import java.util.Map;

/**
 * Contains methods for retrieving weather data as a {@link WeatherData} object
 * or as a {@link String} in JSON format.
 * <p>
 * Also allows retrieving information about the current environment using the {@link com.dimfunky.template.WeatherTemplate#getEnvironment()} method.
 */
public class WeatherTemplate {
    protected final WeatherHttpClient httpClient;
    protected final WeatherEnvironment environment;


    /**
     * Retrieving information about the current environment
     *
     * @return {@link WeatherEnvironment}
     */
    public WeatherEnvironment getEnvironment() {
        return environment;
    }

    public WeatherTemplate(final WeatherHttpClient httpClient, final WeatherEnvironment environment) {
        this.httpClient = httpClient;
        this.environment = environment;
    }

    /**
     * <pre>
     * </pre>
     * Retrieve weather data for the city with the name {@code cityName} as a {@link String} in JSON format.
     * <p>
     * Calls the {@link com.dimfunky.template.WeatherTemplate#getWeatherInfo(String)} method and maps the result to a String.
     * <p>
     * Accept parameters:
     * <p>
     * {@code String cityName} - Name of city witch must contain at least one character and consist only of letters, spaces, and hyphens.
     * <p>
     *
     * @param cityName
     * @return {@link String}
     * @throws com.dimfunky.exception.WeatherServiceException           if errors occur during data retrieval and conversion.
     * @throws com.dimfunky.exception.WeatherServiceValidationException if cityName not valid.
     */
    public String getWeatherInfoJsonString(final String cityName) {
        WeatherData weatherData = getWeatherInfo(cityName);
        return WeatherMapper.weatherDataToJson(weatherData);
    }

    /**
     * <pre>
     * </pre>
     * Retrieve weather data for the city with the name {@code cityName} as a {@link WeatherData} object.
     * <p>
     * Validates the input, converts {@code cityName} to lowercase, and retrieves data from the cache.
     * If the data is missing or outdated, it makes a request to the API of <a href="https://openweathermap.org">OpenWeatherMap</a>,
     * fetches the data, updates the cache, and returns the retrieved data as an object {@link WeatherData}.
     * The relevance of the data is checked within the method {@link WeatherData#isExpired()}.
     * <p>
     * When retrieving weather data from the 'Data API', the cache is checked for an existing record of the city.
     * If found, the coordinates are taken from the cached entry to avoid an additional 'Geocode API' request.
     * If no data for the city exists in the cache, the coordinates are requested from the 'Geocode API'.
     * For the retrieved data, a timestamp of the update is set using {@link Instant#now()}.
     * <p>
     * On the cache updating, checks the presence of an existing record and whether the cache limit is exceeded.
     * If no record exists and the cache limit is exceeded, the oldest entry
     * (based on the {@link WeatherData#fetchedAt()}) is removed.
     * In all other cases, the record is either added or updates the existing one.
     * <p>
     * Accept parameters:
     * <p>
     * {@code String cityName} - Name of city witch must contain at least one character and consist only of letters, spaces, and hyphens.
     * <p>
     *
     * @param cityName
     * @return {@link WeatherData}
     * @throws com.dimfunky.exception.WeatherServiceException           if errors occur during data retrieval and conversion.
     * @throws com.dimfunky.exception.WeatherServiceValidationException if cityName not valid.
     */
    public WeatherData getWeatherInfo(final String cityName) {
        InputValidation.validateCityName(cityName);

        var cityKey = cityName.toLowerCase();
        var cachedData = getDataFromCache(cityKey);

        if (cachedData == null || cachedData.isExpired()) {
            var fetchedData = fetchData(cityKey, cachedData);
            updateCache(fetchedData, environment.getCache());
            return fetchedData;
        }
        return cachedData;
    }

    protected WeatherData getDataFromCache(final String cityName) {
        return environment.getCache().get(cityName);
    }

    protected WeatherData fetchData(final String cityName, final WeatherData cachedData) {
        var apiKey = environment.getApiKey();
        CityCoords cityCoords;
        if (cachedData == null) {
            cityCoords = httpClient.getCoordsByCityName(cityName, apiKey);
        } else {
            cityCoords = WeatherMapper.infoToCoords(cachedData);
        }
        return httpClient.getWeatherData(cityCoords, apiKey)
                .fetchedAt(Instant.now().getEpochSecond());
    }

    protected void updateCache(WeatherData value, MemoryCache<WeatherData> cache) {
        var cityKey = value.name().toLowerCase();

        if (!cache.contains(cityKey) && cache.size() >= environment.getCacheLimit()) {
            Map<String, WeatherData> store = cache.getAll();

            store.entrySet().stream()
                    .min(Comparator.comparingLong(entry -> entry.getValue().fetchedAt()))
                    .map(Map.Entry::getKey)
                    .ifPresent(store::remove);
        }

        cache.put(cityKey, value);
    }
}