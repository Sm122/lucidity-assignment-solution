package com.example.lucidityAssignment.model;

public class Location {
    public String name;
    public double lat, lon;
    public boolean isRestaurant;
    public int prepTime;

    public Location() {}
    public Location(String name, double lat, double lon, boolean isRestaurant, int prepTime) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.isRestaurant = isRestaurant;
        this.prepTime = prepTime;
    }
}
