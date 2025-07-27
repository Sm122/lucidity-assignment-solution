package com.example.lucidityAssignment.controller;


import com.example.lucidityAssignment.exception.InvalidRequestException;
import com.example.lucidityAssignment.model.RouteRequest;
import com.example.lucidityAssignment.model.RouteResponse;
import com.example.lucidityAssignment.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/optimal-route")
    public RouteResponse getOptimalRoute(@RequestBody RouteRequest request) {
        if (request == null || request.deliveryExecutive == null || request.orders == null || request.orders.isEmpty()) {
            throw new InvalidRequestException("Invalid route request: missing required fields");
        }
        return routeService.findOptimalRoute(request.deliveryExecutive, request.orders);
    }
}
