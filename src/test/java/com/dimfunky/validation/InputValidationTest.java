package com.dimfunky.validation;

import com.dimfunky.exception.WeatherServiceValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputValidationTest {

    @ParameterizedTest
    @ValueSource(strings = {"P", "San José-Los", "Düsseldorf"})
    void shouldPassForValidCityNames(final String validCityName) {
        assertDoesNotThrow(() -> InputValidation.validateCityName(validCityName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Paris123", "New@York", "Los_Angeles", "London!"})
    void shouldThrowExceptionForInvalidCityNames(final String notValidCityName) {
        assertThrows(WeatherServiceValidationException.class, () -> InputValidation.validateCityName(notValidCityName));
    }

    @Test
    void shouldThrowExceptionForEmptyOrNull() {
        assertThrows(WeatherServiceValidationException.class, () -> InputValidation.validateCityName(""));
        assertThrows(WeatherServiceValidationException.class, () -> InputValidation.validateCityName(null));
    }

    @Test
    void shouldPassForValidApiKey() {
        assertDoesNotThrow(() -> InputValidation.validateApiKey("1234567890ABCDEFabcdef1234567890"));
    }

    @Test
    void shouldThrowExceptionForInvalidApiKey() {
        Exception exception = assertThrows(WeatherServiceValidationException.class,
                () -> InputValidation.validateApiKey("invalid_api_key"));
        assertEquals("Not valid API Key. Must contains 32 characters and contains only digits and letters from 'a' to 'f'", exception.getMessage());
    }

    @Test
    void shouldPassForValidCacheLimit() {
        assertDoesNotThrow(() -> InputValidation.validateCacheLimit(10));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void shouldThrowExceptionForInvalidCacheLimit(final int notValidCacheLimit) {
        Exception exception = assertThrows(WeatherServiceValidationException.class,
                () -> InputValidation.validateCacheLimit(notValidCacheLimit));
        assertEquals("cacheLimit must be positive or 0", exception.getMessage());
    }

    @Test
    void shouldPassForValidCacheUpdatePeriod() {
        assertDoesNotThrow(() -> InputValidation.validateCacheUpdatePeriod(60));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -10})
    void shouldThrowExceptionForInvalidCacheUpdatePeriod(final int notValidUpdatePeriod) {
        Exception exception = assertThrows(WeatherServiceValidationException.class,
                () -> InputValidation.validateCacheUpdatePeriod(notValidUpdatePeriod));
        assertEquals("updatePeriod must be positive", exception.getMessage());
    }
}
