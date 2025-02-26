package com.dimfunky;

import com.dimfunky.client.WeatherHttpClient;
import com.dimfunky.environment.WeatherEnvironment;
import com.dimfunky.template.Polling;
import com.dimfunky.template.WeatherTemplate;
import com.dimfunky.template.WeatherTemplatePolling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.dimfunky.constant.WeatherUpdateMode.POLLING;

/**
 * ! The SDK interacts only with the Free Access API, so be mindful of request limits when setting parameters !
 * <p>
 * The factory is designed for creating, retrieving, and deleting instances
 * of {@link WeatherTemplate} based on the provided apiKey.
 * <p>
 * The apiKey can be obtained from <a href="https://openweathermap.org">OpenWeatherMap</a>.
 * <p>
 * Creating multiple {@link WeatherTemplate} instances with the same apiKey is not allowed.
 */

public class WeatherTemplateFactory {
    private static final Map<String, WeatherTemplate> instances = new ConcurrentHashMap<>();

    private WeatherTemplateFactory() {
    }

    /**
     * Creates an instance of {@link WeatherTemplate} based on the provided {@link WeatherEnvironment}.
     * <p>
     * There are two types of environments that differ in their data cache update modes.
     * Data is considered to be up-to-date if less than 10 minutes have passed.
     * <p>
     * - Updates the weather information in cache only on customer requests:
     * <blockquote><pre>
     * {@link  WeatherEnvironment.OnDemand}
     * <p>
     * </pre></blockquote><p>
     * - Auto updates all data in cache. Creates {@link WeatherTemplatePolling}
     * and starts the scheduler thread for data updates.
     * <blockquote><pre>
     * {@link  WeatherEnvironment.Polling}
     * </pre></blockquote><p>
     * <p>
     * {@link  WeatherEnvironment} accept parameters:
     * <p>
     * {@code String apiKey} - Your API key. This parameter is required for both modes.
     * <p>
     * {@code int cacheLimit} - The cache size, which defaults to 10. This parameter is optional for both modes.
     * <p>
     * {@code long cacheUpdatePeriodSeconds} - The cache update period in seconds. Defaults to 9*60 (9 minutes)
     * ensures data relevance in the cache and provides a zero-latency response for requests.
     * This parameter is applicable only to the {@code Polling} mode and is optional.
     * <p>
     * ! The SDK interacts only with the Free Access API, so be mindful of request limits when setting parameters !
     * <p>
     * If an instance with the provided {@code apiKey} already exists, it will not be created again.
     *
     * @param environment
     */

    public static void createInstance(final WeatherEnvironment environment) {
        instances.computeIfAbsent(
                environment.getApiKey(),
                value -> {
                    WeatherTemplate template = environment.getWeatherUpdateMode() == POLLING
                            ? new WeatherTemplatePolling(new WeatherHttpClient(), environment)
                            : new WeatherTemplate(new WeatherHttpClient(), environment);

                    if (template instanceof Polling) {
                        ((Polling) template).startPolling();
                    }
                    return template;
                });
    }

    /**
     * <pre>
     * </pre>
     * Get an instance of {@link WeatherTemplate} by {@code apiKey}.
     * <p>
     * If this method returns {@code null}, it means that the instance with the current {@code apiKey}
     * has not been created or has been deleted.
     *
     * @param apiKey
     * @return {@link WeatherTemplate}
     */
    public static WeatherTemplate getInstance(final String apiKey) {
        return instances().get(apiKey);
    }


    /**
     * <pre>
     * </pre>
     * Delete the instance by {@code apiKey}.
     * <p>
     * When deleting an instance with {@code Polling} mode, it also stops the scheduler thread for data updates.
     *
     * @param apiKey
     */
    public static void deleteInstance(final String apiKey) {
        WeatherTemplate template = instances.remove(apiKey);
        if (template instanceof Polling) {
            ((Polling) template).stopPolling();
        }
    }

    /**
     * Returns Map with all instances
     *
     * @return {@link Map}
     */
    public static Map<String, WeatherTemplate> instances() {
        return instances;
    }
}
