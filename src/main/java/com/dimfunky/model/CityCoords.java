package com.dimfunky.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CityCoords {

    @JsonProperty("name")
    private String name;

    public String name() {
        return name;
    }

    public CityCoords name(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("lat")
    private Double lat;

    public Double lat() {
        return lat;
    }

    public CityCoords lat(Double lat) {
        this.lat = lat;
        return this;
    }

    @JsonProperty("lon")
    private Double lon;

    public Double lon() {
        return lon;
    }

    public CityCoords lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public CityCoords() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityCoords)) return false;
        CityCoords that = (CityCoords) o;
        return Objects.equals(name, that.name) && Objects.equals(lat, that.lat) && Objects.equals(lon, that.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lat, lon);
    }

    @Override
    public String toString() {
        return "CityCoords{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}

