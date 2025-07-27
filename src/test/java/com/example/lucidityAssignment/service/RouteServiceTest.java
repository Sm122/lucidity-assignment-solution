package com.example.lucidityAssignment.service;

import com.example.lucidityAssignment.model.Location;
import com.example.lucidityAssignment.model.Order;
import com.example.lucidityAssignment.model.RouteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteServiceTest {

    private RouteService service;

    @BeforeEach
    public void setUp() {
        service = new RouteService();
    }

    @Test
    public void testSingleOrder() {
        Location aman = new Location("Aman", 12.935, 77.614, false, 0);
        Location r1 = new Location("R1", 12.937, 77.620, true, 5);
        Location c1 = new Location("C1", 12.945, 77.625, false, 0);

        List<Order> orders = List.of(new Order(r1, c1));
        RouteResponse response = service.findOptimalRoute(aman, orders);

        assertNotNull(response);
        assertEquals(2, response.route.size());
        assertTrue(response.totalTime > 0);
        assertEquals("R1", response.route.get(0));
        assertEquals("C1", response.route.get(1));
    }

    @Test
    public void testTwoOrders() {
        Location aman = new Location("Aman", 12.935, 77.614, false, 0);
        Location r1 = new Location("R1", 12.937, 77.620, true, 10);
        Location c1 = new Location("C1", 12.945, 77.625, false, 0);
        Location r2 = new Location("R2", 12.940, 77.618, true, 8);
        Location c2 = new Location("C2", 12.942, 77.630, false, 0);

        List<Order> orders = List.of(new Order(r1, c1), new Order(r2, c2));
        RouteResponse response = service.findOptimalRoute(aman, orders);

        assertNotNull(response);
        assertEquals(4, response.route.size());
        assertTrue(response.totalTime > 0);
        assertTrue(response.route.contains("R1"));
        assertTrue(response.route.contains("C1"));
        assertTrue(response.route.contains("R2"));
        assertTrue(response.route.contains("C2"));
    }

    @Test
    public void testInvalidOrderThrowsException() {
        Location aman = new Location("Aman", 12.935, 77.614, false, 0);
        List<Order> orders = List.of(new Order(null, null));

        Exception ex = assertThrows(RuntimeException.class, () ->
                service.findOptimalRoute(aman, orders)
        );

        assertTrue(ex.getMessage().contains("Each order must include"));
    }
}
