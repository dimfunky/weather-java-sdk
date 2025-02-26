package com.dimfunky.model;

import com.dimfunky.mapper.WeatherMapper;
import com.dimfunky.support.DataProvider;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeatherDataExpiredTest {

    @Test
    void dataIsExpired() {
        var weatherData = DataProvider.prepareLondonWeatherData();

        assertFalse(weatherData.isExpired());

        weatherData.fetchedAt(Instant.now().getEpochSecond() - 601);

        assertTrue(weatherData.isExpired());
    }
}
