# Weather Java SDK

## Introduction



Weather Java SDK is used to easily access a "Free Access weather API" <a href="https://openweathermap.org">OpenWeatherMap</a> and retrieve simplified weather data for a given location using Java language.
The package provides methods for retrieving data in JSON format or as object, using a location name as a string input.
SDK calls to 
This page describes basic configuration and usage example. For more detailed information on using the SDK, refer to the built-in JavaDoc.

## Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage Example](#usage-example)

## Installation
From source code with Maven:
- download source code
- run command in SDK project folder - `mvn clean install`
- add dependency to your `.pom` file:
```xml
<dependency>
    <groupId>com.dimfunky</groupId>
    <artifactId>weather-java-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

1. Create an instance, using factory

```java
public class WeatherService {
    // Add apiKey by creating an instance
    // In the factory method, we pass a simple environment constructor
    // with the selected update mode, providing the API key as a parameter.
    //
    // Also, available constructors that accept parameters for controlling
    // the cache limit and update period.
    public void addKey(final String apiKey) {
        WeatherTemplateFactory.createInstance(
                new WeatherEnvironment.Polling(apiKey)
        );
    }
}
```

## Usage Example

```java
public class WeatherService {
    private static final String API_KEY = "1234567890abcdefabcdef1234567890";
    
    // Example of getting instance of an existing template by apiKey
    // Getting JSON string weather data
    public String getWeatherString(final String cityName) {
        WeatherTemplate template = WeatherTemplateFactory.getInstance(API_KEY);
        if (template != null) {
            return template.getWeatherInfoJsonString(cityName);
        }
        throw new RuntimeException("apiKey not found");
    }

    // Delete key by deleting an instance
    public void delKey(final String apiKey) {
        WeatherTemplateFactory.deleteInstance(apiKey);
    }

    // Get all stored keys
    public Set<String> getKeys() {
        return WeatherTemplateFactory.instances().keySet();
    }

    // Get info about configured environment
    public void showInfo(final WeatherTemplate template) {
        var env = template.getEnvironment();
        System.out.println("Cache limit: " + env.getCacheLimit());
        System.out.println("ApiKey: " + env.getApiKey());
        System.out.println("Weather update mode: " + env.getWeatherUpdateMode());
        System.out.println("Cached cities: " + env.getCache().getAll().keySet().stream().toList());
    }
}
```
