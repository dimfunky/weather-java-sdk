package com.dimfunky.template;

import com.dimfunky.environment.WeatherEnvironment;
import com.dimfunky.cache.MemoryCache;
import com.dimfunky.client.WeatherHttpClient;
import com.dimfunky.model.WeatherData;
import com.dimfunky.support.DataProvider;
import org.junit.jupiter.api.BeforeEach;

import java.time.Instant;

import static com.dimfunky.support.DataProvider.fakeApiKey;
import static com.dimfunky.support.DataProvider.prepareLondonWeatherData;
import static com.dimfunky.support.DataProvider.prepareNewYorkWeatherData;
import static com.dimfunky.support.DataProvider.prepareTokioWeatherData;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class BaseTemplateTest {
    static WeatherEnvironment environment;
    static WeatherHttpClient weatherHttpClient;
    static MemoryCache<WeatherData> cache;
    static int cacheLimit = 3;

    @BeforeEach
    void beforeEach() {
        cache.clear();
        prepareCache();
    }

    protected void prepareCache() {
        cache.put("london", prepareLondonWeatherData().fetchedAt(Instant.now().getEpochSecond() - 50));
        cache.put("new york", prepareNewYorkWeatherData().fetchedAt(Instant.now().getEpochSecond() - 600));
        cache.put("tokio", prepareTokioWeatherData().fetchedAt(Instant.now().getEpochSecond() - 650));
    }

    protected void prepareStubs() {
        var citiCoords = DataProvider.prepareParisCityCoords();
        when(weatherHttpClient.getCoordsByCityName("paris", fakeApiKey))
                .thenReturn(citiCoords);
        when(weatherHttpClient.getWeatherData(citiCoords, fakeApiKey))
                .thenReturn(DataProvider.prepareParisWeatherData());
    }

    protected void preparePollingStubs() {
        var londonCityCoords = DataProvider.prepareLondonCityCoords();
        var newYorkCityCoords = DataProvider.prepareNewYorkCityCoords();
        var tokioCityCoords = DataProvider.prepareTokioCityCoords();

        when(weatherHttpClient.getWeatherData(eq(londonCityCoords), eq(fakeApiKey)))
                .thenReturn(DataProvider.prepareLondonWeatherData());
        when(weatherHttpClient.getWeatherData(eq(newYorkCityCoords), eq(fakeApiKey)))
                .thenReturn(DataProvider.prepareLondonWeatherData());
        when(weatherHttpClient.getWeatherData(eq(tokioCityCoords), eq(fakeApiKey)))
                .thenReturn(DataProvider.prepareLondonWeatherData());
    }
}
