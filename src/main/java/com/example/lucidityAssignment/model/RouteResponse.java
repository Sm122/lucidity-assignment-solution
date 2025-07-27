package com.example.lucidityAssignment.model;

import java.util.List;

public class RouteResponse {
    public List<String> route;
    public double totalTime;

    public RouteResponse(List<String> route, double totalTime) {
        this.route = route;
        this.totalTime = totalTime;
    }
}

