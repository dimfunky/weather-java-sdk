package com.dimfunky.template;

import com.dimfunky.environment.WeatherEnvironment;
import com.dimfunky.client.WeatherHttpClient;
import com.dimfunky.support.DataProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.dimfunky.support.DataProvider.fakeApiKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class WeatherTemplateTest extends BaseTemplateTest {
    static WeatherTemplate weatherTemplate;

    @BeforeAll
    static void initialize() {
        environment = new WeatherEnvironment.OnDemand(fakeApiKey, cacheLimit);
        weatherHttpClient = mock(WeatherHttpClient.class);
        weatherTemplate = new WeatherTemplate(weatherHttpClient, environment);
        cache = environment.getCache();
    }


    @Test
    void getWeatherInfoShouldWork() {
        prepareStubs();
        var expectedData = DataProvider.prepareParisWeatherData();
        var weatherData = weatherTemplate.getWeatherInfo("Paris");

        assertEquals(expectedData, weatherData);
        assertEquals(3, cache.size());
        assertFalse(cache.contains("tokio"));
        assertTrue(cache.contains("london"));
        assertTrue(cache.contains("new york"));
        assertTrue(cache.contains("paris"));
    }

    @Test
    void getWeatherInfoJsonStringShouldWork() {
        prepareStubs();
        var expectedJson = DataProvider.readJsonFile("weather-data.json");
        var weatherDataJson = weatherTemplate.getWeatherInfoJsonString("Paris");

        assertEquals(expectedJson, weatherDataJson);
    }
}
