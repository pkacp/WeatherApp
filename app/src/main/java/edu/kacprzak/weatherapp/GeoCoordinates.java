package edu.kacprzak.weatherapp;

public class GeoCoordinates {

    double lon;
    double lat;

    public GeoCoordinates(double lat, double lon) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
