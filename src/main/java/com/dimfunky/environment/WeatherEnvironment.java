package com.dimfunky.environment;

import com.dimfunky.cache.MemoryCache;
import com.dimfunky.constant.WeatherUpdateMode;
import com.dimfunky.model.WeatherData;

import static com.dimfunky.validation.InputValidation.validateApiKey;
import static com.dimfunky.validation.InputValidation.validateCacheLimit;
import static com.dimfunky.validation.InputValidation.validateCacheUpdatePeriod;

/**
 * Contains environment parameters
 * <p>
 * {@link com.dimfunky.environment.WeatherEnvironment#apiKey} - API Key for
 * accessing the website <a href="https://openweathermap.org">OpenWeatherMap</a>
 * <p>
 * {@link com.dimfunky.environment.WeatherEnvironment#weatherUpdateMode} - Flag for
 * the cache data update mode {@link WeatherUpdateMode}
 * <p>
 * {@link com.dimfunky.environment.WeatherEnvironment#weatherCache} - In-memory cache
 * implemented using {@link java.util.concurrent.ConcurrentHashMap}
 * <p>
 * {@link com.dimfunky.environment.WeatherEnvironment#cacheLimit} - Size (limit) of
 * values stored in the cache
 * <p>
 * {@link com.dimfunky.environment.WeatherEnvironment#cacheUpdatePeriodSeconds} - Cache
 * auto-update period in seconds
 */
public class WeatherEnvironment {


    /**
     * API Key for accessing the website <a href="https://openweathermap.org">OpenWeatherMap</a>
     */
    private final String apiKey;


    /**
     * Flag for the cache data update mode
     */
    private final WeatherUpdateMode weatherUpdateMode;


    /**
     * In-memory cache implemented using {@link java.util.concurrent.ConcurrentHashMap}
     */
    private final MemoryCache<WeatherData> weatherCache;


    /**
     * Size (limit) of values stored in the cache
     */
    private final int cacheLimit;
    /**
     * Default cache size = 10
     */
    private static final int DEFAULT_CACHE_SIZE = 10;


    /**
     * Cache auto-update period in seconds
     */
    private final long cacheUpdatePeriodSeconds;
    /**
     * Default cache update period = 9 minutes
     */
    private static final long DEFAULT_CACHE_UPDATE_PERIOD_SECONDS = 9 * 60L;


    private WeatherEnvironment(String apiKey, WeatherUpdateMode weatherUpdateMode, int cacheLimit, long cacheUpdatePeriodSeconds) {
        validateParameters(apiKey, cacheLimit, cacheUpdatePeriodSeconds);
        this.apiKey = apiKey.toLowerCase();
        this.weatherUpdateMode = weatherUpdateMode;
        this.weatherCache = new MemoryCache<>();
        this.cacheLimit = cacheLimit;
        this.cacheUpdatePeriodSeconds = cacheUpdatePeriodSeconds;
    }

    public static class OnDemand extends WeatherEnvironment {
        /**
         * <pre>
         * </pre>
         * Creates an {@link WeatherEnvironment} with a manual cache update mode.
         * <p>
         * Accept parameters:
         * <p>
         * {@code String apiKey} - Your API key.
         * <p>
         * {@link com.dimfunky.environment.WeatherEnvironment#cacheLimit} is set to the default value - 10
         *
         * @param apiKey
         * @throws com.dimfunky.exception.WeatherServiceValidationException on parameters validation errors.
         */
        public OnDemand(String apiKey) {
            super(apiKey, WeatherUpdateMode.ON_DEMAND, DEFAULT_CACHE_SIZE, DEFAULT_CACHE_UPDATE_PERIOD_SECONDS);
        }

        /**
         * <pre>
         * </pre>
         * Creates an {@link WeatherEnvironment} with a manual cache update mode.
         * <p>
         * Accept parameters:
         * <p>
         * {@code String apiKey} - Your API key.
         * <p>
         * {@code int cacheLimit} - Size (limit) of values stored in the cache.
         *
         * @param apiKey
         * @param cacheLimit
         * @throws com.dimfunky.exception.WeatherServiceValidationException on parameters validation errors.
         */

        public OnDemand(String apiKey, int cacheLimit) {
            super(apiKey, WeatherUpdateMode.ON_DEMAND, cacheLimit, DEFAULT_CACHE_UPDATE_PERIOD_SECONDS);
        }
    }

    public static class Polling extends WeatherEnvironment {
        /**
         * <pre>
         * </pre>
         * Creates an {@link WeatherEnvironment} with an auto cache update mode.
         * <p>
         * Accept parameters:
         * <p>
         * {@code String apiKey} - Your API key.
         * <p>
         * {@link com.dimfunky.environment.WeatherEnvironment#cacheLimit} is set to the default value - 10
         * <p>
         * {@link com.dimfunky.environment.WeatherEnvironment#cacheUpdatePeriodSeconds} is set to the default
         * value - 9 minutes
         *
         * @param apiKey
         * @throws com.dimfunky.exception.WeatherServiceValidationException on parameters validation errors.
         */
        public Polling(String apiKey) {
            super(apiKey, WeatherUpdateMode.POLLING, DEFAULT_CACHE_SIZE, DEFAULT_CACHE_UPDATE_PERIOD_SECONDS);
        }

        /**
         * <pre>
         * </pre>
         * Creates an {@link WeatherEnvironment} with an auto cache update mode.
         * <p>
         * Accept parameters:
         * <p>
         * {@code String apiKey} - Your API key.
         * <p>
         * {@code int cacheLimit} - Size (limit) of values stored in the cache.
         * <p>
         * {@link com.dimfunky.environment.WeatherEnvironment#cacheUpdatePeriodSeconds} is set to the default
         * value - 9 minutes
         *
         * @param apiKey
         * @param cacheLimit
         * @throws com.dimfunky.exception.WeatherServiceValidationException on parameters validation errors.
         */
        public Polling(String apiKey, int cacheLimit) {
            super(apiKey, WeatherUpdateMode.POLLING, cacheLimit, DEFAULT_CACHE_UPDATE_PERIOD_SECONDS);
        }

        /**
         * <pre>
         * </pre>
         * Creates an {@link WeatherEnvironment} with an auto cache update mode.
         * <p>
         * Accept parameters:
         * <p>
         * {@code String apiKey} - Your API key.
         * <p>
         * {@code long cacheUpdatePeriodSeconds} - Cache auto-update period in seconds
         * <p>
         * {@link com.dimfunky.environment.WeatherEnvironment#cacheLimit} is set to the default value - 10
         *
         * @param apiKey
         * @param cacheUpdatePeriodSeconds
         * @throws com.dimfunky.exception.WeatherServiceValidationException on parameters validation errors.
         */
        public Polling(String apiKey, long cacheUpdatePeriodSeconds) {
            super(apiKey, WeatherUpdateMode.POLLING, DEFAULT_CACHE_SIZE, cacheUpdatePeriodSeconds);
        }

        /**
         * <pre>
         * </pre>
         * Creates an {@link WeatherEnvironment} with an auto cache update mode.
         * <p>
         * Accept parameters:
         * <p>
         * {@code String apiKey} - Your API key.
         * <p>
         * {@code int cacheLimit} - Size (limit) of values stored in the cache.
         * <p>
         * {@code long cacheUpdatePeriodSeconds} - Cache auto-update period in seconds
         *
         * @param apiKey
         * @param cacheLimit
         * @param cacheUpdatePeriodSeconds
         * @throws com.dimfunky.exception.WeatherServiceValidationException on parameters validation errors.
         */
        public Polling(String apiKey, int cacheLimit, long cacheUpdatePeriodSeconds) {
            super(apiKey, WeatherUpdateMode.POLLING, cacheLimit, cacheUpdatePeriodSeconds);
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public WeatherUpdateMode getWeatherUpdateMode() {
        return weatherUpdateMode;
    }

    public MemoryCache<WeatherData> getCache() {
        return weatherCache;
    }

    public int getCacheLimit() {
        return cacheLimit;
    }

    public long getCacheUpdatePeriodSeconds() {
        return cacheUpdatePeriodSeconds;
    }

    private void validateParameters(final String apiKey, final int cacheLimit, final long cacheUpdatePeriodSeconds) {
        validateApiKey(apiKey);
        validateCacheLimit(cacheLimit);
        validateCacheUpdatePeriod(cacheUpdatePeriodSeconds);
    }
}
