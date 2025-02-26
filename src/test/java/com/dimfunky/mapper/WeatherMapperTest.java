package com.dimfunky.mapper;

import com.dimfunky.exception.WeatherServiceException;
import com.dimfunky.support.DataProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeatherMapperTest {

    @Test
    void toCityCoords() {
        var json = DataProvider.readJsonFile("city-coords.json");

        var result = WeatherMapper.toCityCoords(json);

        assertNotNull(result);
        assertEquals("Paris", result.name());
        assertEquals(48.8588897, result.lat());
        assertEquals(2.3200410217200766, result.lon());
    }

    @Test
    void toWeatherData() {
        var json = DataProvider.readJsonFile("weather-data-response.json");

        var result = WeatherMapper.toWeatherData(json);
        assertNotNull(result);
        assertEquals(48.8615, result.coord().lat());
        assertEquals(2.3158, result.coord().lon());
        assertEquals("Rain", result.weather().main());
        assertEquals("light rain", result.weather().description());
        assertEquals(286.83, result.temperature().temp());
        assertEquals(286.08, result.temperature().feelsLike());
        assertEquals(10000, result.visibility());
        assertEquals(7.72, result.wind().speed());
        assertEquals(1740401525, result.dateTime());
        assertEquals(1740379356, result.sys().sunrise());
        assertEquals(1740417938, result.sys().sunset());
        assertEquals(3600, result.timeZone());
        assertEquals("Paris", result.name());
    }

    @Test
    void toWeatherDataThrowsEx() {
        var invalidJson = DataProvider.readJsonFile("invalid-json.json");

        assertThrows(WeatherServiceException.class, () ->
                WeatherMapper.toWeatherData(invalidJson)
        );
    }

    @Test
    void weatherDataToJson() {
        var weatherData = DataProvider.prepareParisWeatherData();
        var expectedJson = DataProvider.readJsonFile("weather-data.json");

        var resultJson = WeatherMapper.weatherDataToJson(weatherData);

        assertNotNull(resultJson);
        assertEquals(expectedJson, resultJson);
    }

    @Test
    void shouldConvertWeatherDataToCityCoords() {
        var weatherData = DataProvider.prepareParisWeatherData();

        var result = WeatherMapper.infoToCoords(weatherData);

        assertNotNull(result);
        assertEquals("Paris", result.name());
        assertEquals(48.8589, result.lat());
        assertEquals(2.32, result.lon());
    }
}
