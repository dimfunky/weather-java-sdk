package com.dimfunky.template;

import com.dimfunky.client.WeatherHttpClient;
import com.dimfunky.environment.WeatherEnvironment;
import com.dimfunky.model.WeatherData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A subclass of the {@link WeatherTemplate} that additionally implements the {@link Polling} interface,
 * which contains methods for starting and stopping the data update scheduler.
 */
public class WeatherTemplatePolling extends WeatherTemplate implements Polling {
    private ScheduledExecutorService scheduler;


    public WeatherTemplatePolling(WeatherHttpClient httpClient, WeatherEnvironment weatherEnvironment) {
        super(httpClient, weatherEnvironment);
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    /**
     * Methods for starting {@link com.dimfunky.template.WeatherTemplatePolling#scheduler}.
     * <p>
     * Initialize the {@link com.dimfunky.template.WeatherTemplatePolling#scheduler}.
     * Creates a thread to start updates data in cache at a specified interval.
     * For each entry in the cache, it makes a request to the API of
     * <a href="https://openweathermap.org">OpenWeatherMap</a>.
     * Updates the data for each entry in the cache.
     * Starts scheduler without delays. The update interval is taken from the
     * {@link WeatherEnvironment#getCacheUpdatePeriodSeconds()}.
     */
    @Override
    public void startPolling() {
        scheduler = Executors.newScheduledThreadPool(1);
        var cache = environment.getCache();
        scheduler.scheduleAtFixedRate(() -> {
            for (WeatherData data : cache.getAll().values()) {
                var fetchedData = fetchData(data.name(), data);
                updateCache(fetchedData, cache);
            }
        }, 0, environment.getCacheUpdatePeriodSeconds(), TimeUnit.SECONDS);
    }


    /**
     * Stops the {@link com.dimfunky.template.WeatherTemplatePolling#scheduler} if it has been initialized
     */
    @Override
    public void stopPolling() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}
