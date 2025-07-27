package com.example.lucidityAssignment.util;

import com.example.lucidityAssignment.model.Location;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeoUtilTest {

    @Test
    void testHaversine_sameLocation_returnsZero() {
        Location loc = new Location("Same", 12.9716, 77.5946, false, 0);
        double distance = GeoUtil.haversine(loc, loc);
        assertEquals(0.0, distance, 1e-6);
    }

    @Test
    void testHaversine_differentLocations_returnsPositive() {
        Location loc1 = new Location("A", 12.9716, 77.5946, false, 0); // Bangalore
        Location loc2 = new Location("B", 13.0827, 80.2707, false, 0); // Chennai

        double distance = GeoUtil.haversine(loc1, loc2);
        assertTrue(distance > 0);
        assertEquals(290, distance, 10); // Roughly 290 km
    }

    @Test
    void testTravelTime_calculatesCorrectly() {
        Location loc1 = new Location("A", 12.9716, 77.5946, false, 0);
        Location loc2 = new Location("B", 13.0827, 80.2707, false, 0);

        double time = GeoUtil.travelTime(loc1, loc2);
        assertTrue(time > 0);
        assertEquals(870.0, time, 60); // 290 km at 20 km/h = ~870 min
    }
}
