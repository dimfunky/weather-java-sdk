package com.dimfunky.template;

import com.dimfunky.environment.WeatherEnvironment;
import com.dimfunky.client.WeatherHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.dimfunky.support.DataProvider.fakeApiKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class WeatherTemplatePollingTest extends BaseTemplateTest {

    static WeatherTemplatePolling weatherTemplatePolling;

    @BeforeAll
    static void initialize() {
        environment = new WeatherEnvironment.Polling(fakeApiKey, cacheLimit, 2);
        weatherHttpClient = mock(WeatherHttpClient.class);
        weatherTemplatePolling = new WeatherTemplatePolling(weatherHttpClient, environment);
        cache = environment.getCache();
    }

    @Test
    void shouldPollingTwoTimesForEachCityInCache() throws InterruptedException {
        preparePollingStubs();
        weatherTemplatePolling.startPolling();
        Thread.sleep(3000);

        verify(weatherHttpClient, times(6)).getWeatherData(any(), eq(fakeApiKey));
        assertEquals(3, cache.size());

        weatherTemplatePolling.stopPolling();
        assertTrue(weatherTemplatePolling.getScheduler().isShutdown());
    }
}
