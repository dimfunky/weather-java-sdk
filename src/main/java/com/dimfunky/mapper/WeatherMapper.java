package com.dimfunky.mapper;

import com.dimfunky.exception.WeatherServiceException;
import com.dimfunky.model.CityCoords;
import com.dimfunky.model.WeatherData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRIM_CITY_LIST_STRING_REGEX = "(^\\[)|(\\])$";

    private WeatherMapper() {
    }

    public static CityCoords toCityCoords(final String jsonString) {
        String trimmedString = jsonString.replaceAll(TRIM_CITY_LIST_STRING_REGEX, "");
        try {
            return objectMapper.readValue(trimmedString, CityCoords.class);
        } catch (JsonProcessingException e) {
            throw new WeatherServiceException(e.getMessage(), e);
        }
    }

    public static WeatherData toWeatherData(final String jsonString) {
        try {
            return objectMapper.readValue(jsonString, WeatherData.class);
        } catch (JsonProcessingException e) {
            throw new WeatherServiceException(e.getMessage(), e);
        }
    }

    public static String weatherDataToJson(final WeatherData weatherData) {
        try {
            return objectMapper.writeValueAsString(weatherData);
        } catch (JsonProcessingException e) {
            throw new WeatherServiceException(e.getMessage(), e);
        }
    }

    public static CityCoords infoToCoords(final WeatherData weatherData) {
        return new CityCoords()
                .name(weatherData.name())
                .lat(weatherData.getCoord().lat())
                .lon(weatherData.getCoord().lon());
    }
}
