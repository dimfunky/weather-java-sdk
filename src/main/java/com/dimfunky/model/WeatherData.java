package com.dimfunky.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"weather", "temperature", "visibility", "wind", "datetime", "sys"})
public class WeatherData {

    /**
     * Timestamp of cache data update in UNIX epochSeconds.
     */
    private Long fetchedAt;

    public Long fetchedAt() {
        return fetchedAt;
    }

    public WeatherData fetchedAt(Long fetchedAt) {
        this.fetchedAt = fetchedAt;
        return this;
    }

    @JsonIgnore
    public Long getFetchedAt() {
        return fetchedAt;
    }

    @JsonIgnore
    public void setFetchedAt(Long fetchedAt) {
        this.fetchedAt = fetchedAt;
    }

    /**
     * <pre>
     * </pre>
     * Validation of data relevance in the cache.
     * Data is considered relevant for 10 minutes after the update.
     * <p>
     * Returns {@code true} if data was updated more than 10 minutes ago.
     */
    @JsonIgnore
    public boolean isExpired() {
        return (Instant.now().getEpochSecond() - fetchedAt) >= 10 * 60L;
    }

    private Coord coord;

    @JsonIgnore
    public Coord getCoord() {
        return coord;
    }

    @JsonProperty
    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Coord coord() {
        return coord;
    }

    public WeatherData coord(Coord coord) {
        this.coord = coord;
        return this;
    }

    @JsonProperty("weather")
    private Weather weather;


    @JsonSetter("weather")
    private void setWeather(List<Weather> weather) {
        if (weather != null && !weather.isEmpty()) {
            this.weather = weather.get(0);
        }
    }

    public Weather weather() {
        return weather;
    }

    public WeatherData weather(Weather weather) {
        this.weather = weather;
        return this;
    }

    @JsonAlias("main")
    @JsonProperty("temperature")
    private Temperature temperature;

    public Temperature temperature() {
        return temperature;
    }

    public WeatherData temperature(Temperature temperature) {
        this.temperature = temperature;
        return this;
    }

    @JsonProperty("visibility")
    private Integer visibility;

    public Integer visibility() {
        return visibility;
    }

    public WeatherData visibility(Integer visibility) {
        this.visibility = visibility;
        return this;
    }

    @JsonProperty("wind")
    private Wind wind;

    public Wind wind() {
        return wind;
    }

    public WeatherData wind(Wind wind) {
        this.wind = wind;
        return this;
    }

    @JsonAlias("dt")
    @JsonProperty("date_time")
    private Long dateTime;

    public Long dateTime() {
        return dateTime;
    }

    public WeatherData dateTime(Long dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @JsonProperty("sys")
    private Sys sys;

    public Sys sys() {
        return sys;
    }

    public WeatherData sys(Sys sys) {
        this.sys = sys;
        return this;
    }

    @JsonProperty("timezone")
    private Integer timeZone;

    public Integer timeZone() {
        return timeZone;
    }

    public WeatherData timeZone(Integer timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    @JsonProperty("name")
    private String name;

    public String name() {
        return name;
    }

    public WeatherData name(String name) {
        this.name = name;
        return this;
    }

    public WeatherData() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherData)) return false;
        WeatherData that = (WeatherData) o;
        return Objects.equals(coord, that.coord) && Objects.equals(weather, that.weather) && Objects.equals(temperature, that.temperature) && Objects.equals(visibility, that.visibility) && Objects.equals(wind, that.wind) && Objects.equals(dateTime, that.dateTime) && Objects.equals(sys, that.sys) && Objects.equals(timeZone, that.timeZone) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord, weather, temperature, visibility, wind, dateTime, sys, timeZone, name);
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "coord=" + coord +
                ", weather=" + weather +
                ", temperature=" + temperature +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", dateTime=" + dateTime +
                ", sys=" + sys +
                ", timeZone=" + timeZone +
                ", name='" + name + '\'' +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coord {

        @JsonProperty("lat")
        private Double lat;

        public Double lat() {
            return lat;
        }

        public Coord lat(Double lat) {
            this.lat = lat;
            return this;
        }

        @JsonProperty("lon")
        private Double lon;

        public Double lon() {
            return lon;
        }

        public Coord lon(Double lon) {
            this.lon = lon;
            return this;
        }

        public Coord() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return Objects.equals(lat, coord.lat) && Objects.equals(lon, coord.lon);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lat, lon);
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "lat=" + lat +
                    ", lon=" + lon +
                    '}';
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {

        @JsonProperty("main")
        private String main;

        public String main() {
            return main;
        }

        public Weather main(String main) {
            this.main = main;
            return this;
        }

        @JsonProperty("description")
        private String description;

        public String description() {
            return description;
        }

        public Weather description(String description) {
            this.description = description;
            return this;
        }

        public Weather() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Weather weather = (Weather) o;
            return Objects.equals(main, weather.main) && Objects.equals(description, weather.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(main, description);
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "main='" + main + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Temperature {

        @JsonProperty("temp")
        private Double temp;

        public Double temp() {
            return temp;
        }

        public Temperature temp(Double temp) {
            this.temp = temp;
            return this;
        }

        @JsonProperty("feels_like")
        private Double feelsLike;

        public Double feelsLike() {
            return feelsLike;
        }

        public Temperature feelsLike(Double feelsLike) {
            this.feelsLike = feelsLike;
            return this;
        }

        public Temperature() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Temperature that = (Temperature) o;
            return Double.compare(temp, that.temp) == 0 && Double.compare(feelsLike, that.feelsLike) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(temp, feelsLike);
        }

        @Override
        public String toString() {
            return "Temperature{" +
                    "temp=" + temp +
                    ", feelsLike=" + feelsLike +
                    '}';
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {

        @JsonProperty("speed")
        private Double speed;

        public Double speed() {
            return speed;
        }

        public Wind speed(Double speed) {
            this.speed = speed;
            return this;
        }

        public Wind() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wind wind = (Wind) o;
            return Double.compare(speed, wind.speed) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(speed);
        }

        @Override
        public String toString() {
            return "Wind{" +
                    "speed=" + speed +
                    '}';
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {

        @JsonProperty("sunrise")
        private Long sunrise;

        public Long sunrise() {
            return sunrise;
        }

        public Sys sunrise(Long sunrise) {
            this.sunrise = sunrise;
            return this;
        }

        @JsonProperty("sunset")
        private Long sunset;

        public Long sunset() {
            return sunset;
        }

        public Sys sunset(Long sunset) {
            this.sunset = sunset;
            return this;
        }

        public Sys() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Sys)) return false;
            Sys sys = (Sys) o;
            return Objects.equals(sunrise, sys.sunrise) && Objects.equals(sunset, sys.sunset);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sunrise, sunset);
        }

        @Override
        public String toString() {
            return "Sys{" +
                    "sunrise=" + sunrise +
                    ", sunset=" + sunset +
                    '}';
        }
    }
}

