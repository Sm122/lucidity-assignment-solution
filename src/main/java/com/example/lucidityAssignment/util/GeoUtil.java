package com.example.lucidityAssignment.util;

import com.example.lucidityAssignment.model.Location;

public class GeoUtil {
    private static final double EARTH_RADIUS = 6371.0;
    private static final double SPEED_KMPH = 20.0;

    public static double haversine(Location a, Location b) {
        double dLat = Math.toRadians(b.lat - a.lat);
        double dLon = Math.toRadians(b.lon - a.lon);
        double lat1 = Math.toRadians(a.lat);
        double lat2 = Math.toRadians(b.lat);

        double aVal = Math.pow(Math.sin(dLat / 2), 2)
                + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));

        return EARTH_RADIUS * c;
    }

    public static double travelTime(Location a, Location b) {
        return (haversine(a, b) / SPEED_KMPH) * 60; // minutes
    }
}
