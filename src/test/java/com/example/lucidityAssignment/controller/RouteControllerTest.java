package com.example.lucidityAssignment.controller;



import com.example.lucidityAssignment.exception.InvalidRequestException;
import com.example.lucidityAssignment.model.Location;
import com.example.lucidityAssignment.model.Order;
import com.example.lucidityAssignment.model.RouteRequest;
import com.example.lucidityAssignment.model.RouteResponse;
import com.example.lucidityAssignment.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteControllerTest {

    @Mock
    private RouteService routeService;

    @InjectMocks
    private RouteController routeController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidRouteRequestReturnsResponse() {
        Location deliveryExecutive = new Location("Aman", 12.935, 77.614, false, 0);
        Location r1 = new Location("R1", 12.937, 77.620, true, 5);
        Location c1 = new Location("C1", 12.945, 77.625, false, 0);
        List<Order> orders = List.of(new Order(r1, c1));

        RouteRequest request = new RouteRequest();
        request.deliveryExecutive = deliveryExecutive;
        request.orders = orders;

        RouteResponse expectedResponse = new RouteResponse(List.of("R1", "C1"), 12.5);
        when(routeService.findOptimalRoute(deliveryExecutive, orders)).thenReturn(expectedResponse);

        RouteResponse actualResponse = routeController.getOptimalRoute(request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.route, actualResponse.route);
        assertEquals(expectedResponse.totalTime, actualResponse.totalTime);
    }

    @Test
    void testInvalidRequestThrowsException() {
        RouteRequest badRequest = new RouteRequest(); // missing aman and orders

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> routeController.getOptimalRoute(badRequest));

        assertEquals("Invalid route request: missing required fields", ex.getMessage());
    }
}
