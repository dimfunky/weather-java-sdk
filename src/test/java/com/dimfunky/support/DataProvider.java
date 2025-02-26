package com.dimfunky.support;

import com.dimfunky.model.CityCoords;
import com.dimfunky.model.WeatherData;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class DataProvider {
    public static String fakeApiKey = "1234567890abcdefabcdef1234567890";

    public static String readJsonFile(final String fileName) {
        ClassLoader classLoader = DataProvider.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    public static WeatherData prepareParisWeatherData() {
        return new WeatherData()
                .fetchedAt(Instant.now().getEpochSecond())
                .coord(new WeatherData.Coord()
                        .lat(48.8589)
                        .lon(2.32))
                .weather(new WeatherData.Weather()
                        .main("Clouds")
                        .description("scattered clouds"))
                .temperature(new WeatherData.Temperature()
                        .temp(285.96)
                        .feelsLike(285.23))
                .visibility(10000)
                .wind(new WeatherData.Wind()
                        .speed(5.14))
                .sys(new WeatherData.Sys()
                        .sunrise(1740293069L)
                        .sunset(1740331441L))
                .dateTime(1740311935L)
                .timeZone(3600)
                .name("Paris");
    }

    public static CityCoords prepareParisCityCoords() {
        return new CityCoords()
                .name("Paris")
                .lat(48.8589)
                .lon(2.32);
    }

    public static WeatherData prepareLondonWeatherData() {
        return new WeatherData()
                .fetchedAt(Instant.now().getEpochSecond())
                .coord(new WeatherData.Coord()
                        .lat(51.5085)
                        .lon(-0.1257))
                .weather(new WeatherData.Weather()
                        .main("Clouds")
                        .description("scattered clouds"))
                .temperature(new WeatherData.Temperature()
                        .temp(285.96)
                        .feelsLike(285.23))
                .visibility(10000)
                .wind(new WeatherData.Wind()
                        .speed(5.14))
                .sys(new WeatherData.Sys()
                        .sunrise(1740293069L)
                        .sunset(1740331441L))
                .dateTime(Instant.now().getEpochSecond())
                .timeZone(3600)
                .name("London");
    }

    public static CityCoords prepareLondonCityCoords() {
        return new CityCoords()
                .name("London")
                .lat(51.5085)
                .lon(-0.1257);
    }

    public static WeatherData prepareNewYorkWeatherData() {
        return new WeatherData()
                .fetchedAt(Instant.now().getEpochSecond())
                .coord(new WeatherData.Coord()
                        .lat(40.7127)
                        .lon(-74.006))
                .weather(new WeatherData.Weather()
                        .main("Clouds")
                        .description("scattered clouds"))
                .temperature(new WeatherData.Temperature()
                        .temp(285.96)
                        .feelsLike(285.23))
                .visibility(10000)
                .wind(new WeatherData.Wind()
                        .speed(5.14))
                .sys(new WeatherData.Sys()
                        .sunrise(1740293069L)
                        .sunset(1740331441L))
                .dateTime(Instant.now().getEpochSecond())
                .timeZone(3600)
                .name("New York");
    }

    public static CityCoords prepareNewYorkCityCoords() {
        return new CityCoords()
                .name("New York")
                .lat(40.7127)
                .lon(-74.006);
    }

    public static WeatherData prepareTokioWeatherData() {
        return new WeatherData()
                .fetchedAt(Instant.now().getEpochSecond())
                .coord(new WeatherData.Coord()
                        .lat(35.6769)
                        .lon(139.7639))
                .weather(new WeatherData.Weather()
                        .main("Clouds")
                        .description("scattered clouds"))
                .temperature(new WeatherData.Temperature()
                        .temp(285.96)
                        .feelsLike(285.23))
                .visibility(10000)
                .wind(new WeatherData.Wind()
                        .speed(5.14))
                .sys(new WeatherData.Sys()
                        .sunrise(1740293069L)
                        .sunset(1740331441L))
                .dateTime(Instant.now().getEpochSecond())
                .timeZone(3600)
                .name("Tokio");
    }

    public static CityCoords prepareTokioCityCoords() {
        return new CityCoords()
                .name("Tokio")
                .lat(35.6769)
                .lon(139.7639);
    }
}
