package com.dimfunky.factory;

import com.dimfunky.WeatherTemplateFactory;
import com.dimfunky.environment.WeatherEnvironment;
import com.dimfunky.template.Polling;
import com.dimfunky.template.WeatherTemplate;
import com.dimfunky.template.WeatherTemplatePolling;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.dimfunky.support.DataProvider.fakeApiKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class WeatherTemplateFactoryTest {

    @AfterEach
    void afterEach() {
        WeatherTemplateFactory.deleteInstance(fakeApiKey);
    }

    @Test
    void shouldCreateOnlyOneInstanceWithSameApiKey() {
        var environment1 = new WeatherEnvironment.OnDemand(fakeApiKey);
        var environment2 = new WeatherEnvironment.OnDemand(fakeApiKey);

        WeatherTemplateFactory.createInstance(environment1);
        WeatherTemplateFactory.createInstance(environment2);

        assertTrue(WeatherTemplateFactory.instances().containsKey(fakeApiKey));
        assertEquals(1, WeatherTemplateFactory.instances().size());
    }

    @Test
    void shouldCreateAndStartPollingWhenModeIsPolling() {
        var template = preparePollingTemplate();

        assertTrue(template instanceof Polling);
        assertTrue(template instanceof WeatherTemplatePolling);

        var pollingTemplate = (WeatherTemplatePolling) template;

        assertNotNull(pollingTemplate.getScheduler());
        assertFalse(pollingTemplate.getScheduler().isShutdown());
    }


    @Test
    void shouldDeleteAndStopPolling() {
        var template = preparePollingTemplate();

        WeatherTemplateFactory.deleteInstance(fakeApiKey);

        assertNull(WeatherTemplateFactory.instances().get(fakeApiKey));

        assertTrue(((WeatherTemplatePolling) template).getScheduler().isShutdown());
    }

    private WeatherTemplate preparePollingTemplate() {
        WeatherTemplateFactory.createInstance(
                new WeatherEnvironment.Polling(fakeApiKey));
        return WeatherTemplateFactory.getInstance(fakeApiKey);
    }
}
