package com.dimfunky.validation;

import com.dimfunky.exception.WeatherServiceValidationException;

public class InputValidation {

    private static final String CITY_NAME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+$";
    private static final String API_KEY_REGEX = "^[a-fA-F0-9]{32}$";

    private InputValidation() {
    }

    public static void validateCityName(final String cityName) {
        if (!isStringValid(cityName, CITY_NAME_REGEX)) {
            throw new WeatherServiceValidationException("The city name must contain at least one character and consist only of letters, spaces, and hyphens");
        }
    }

    public static void validateApiKey(final String apiKey) {
        if (!isStringValid(apiKey, API_KEY_REGEX)) {
            throw new WeatherServiceValidationException("Not valid API Key. Must contains 32 characters and contains only digits and letters from 'a' to 'f'");
        }
    }

    public static void validateCacheLimit(final int cacheLimit) {
        if (cacheLimit <= 0) {
            throw new WeatherServiceValidationException("cacheLimit must be positive or 0");
        }
    }

    public static void validateCacheUpdatePeriod(final long updatePeriod) {
        if (updatePeriod <= 0) {
            throw new WeatherServiceValidationException("updatePeriod must be positive");
        }
    }

    private static boolean isStringValid(final String string, final String regex) {
        return string != null && string.matches(regex);
    }
}
