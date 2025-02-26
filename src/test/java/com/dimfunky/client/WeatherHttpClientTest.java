package com.dimfunky.client;

import com.dimfunky.exception.WeatherServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WeatherHttpClientTest {
    private WeatherHttpClient weatherHttpClient;
    private HttpClient mockHttpClient;

    @BeforeEach
    void beforeEach() {
        mockHttpClient = mock(HttpClient.class);
        weatherHttpClient = new WeatherHttpClient(mockHttpClient);

    }

    @Test
    void shouldThrowExceptionOnApiError() throws IOException, InterruptedException {
        prepareMocks(401, "Invalid API key");

        var ex = assertThrows(
                WeatherServiceException.class,
                () -> weatherHttpClient.getCoordsByCityName(anyString(), anyString())
        );

        assertTrue(ex.getMessage().contains("openweathermap.org returned an error: Invalid API key"));
    }

    @Test
    void getCoordsByCityNameShouldThrowExceptionWhenEmptyResponse() throws IOException, InterruptedException {
        prepareMocks(200, "[]");

        var ex = assertThrows(
                WeatherServiceException.class,
                () -> weatherHttpClient.getCoordsByCityName(anyString(), anyString())
        );

        assertTrue(ex.getMessage().contains("The city was not found. Try to write name in another format"));
    }

    private void prepareMocks(final int status, final String body) throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(status);
        when(mockResponse.body()).thenReturn(body);
        when(mockHttpClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);
    }
}
