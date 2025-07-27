package com.example.lucidityAssignment.service;

import com.example.lucidityAssignment.exception.InvalidRequestException;
import com.example.lucidityAssignment.model.Location;
import com.example.lucidityAssignment.model.Order;
import com.example.lucidityAssignment.model.RouteResponse;
import com.example.lucidityAssignment.util.GeoUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteService {
    private List<Location> allPoints;
    private Map<String, Integer> locationIndex;
    private double[][] travelTimes;

    public RouteResponse findOptimalRoute(Location deliveryExecutive, List<Order> orders) {
        try {
            if (deliveryExecutive == null || deliveryExecutive.name == null || deliveryExecutive.name.isBlank()) {
                throw new InvalidRequestException("Invalid Aman location: Name and coordinates are required");
            }
            if (orders == null || orders.isEmpty()) {
                throw new InvalidRequestException("Orders list cannot be null or empty");
            }

            int n = orders.size();
            allPoints = new ArrayList<>();
            locationIndex = new HashMap<>();

            allPoints.add(deliveryExecutive);
            locationIndex.put(deliveryExecutive.name, 0);
            int idx = 1;
            for (Order o : orders) {
                if (o.restaurant == null || o.consumer == null) {
                    throw new InvalidRequestException("Each order must include a restaurant and consumer location");
                }
                if (o.restaurant.name == null || o.consumer.name == null ||
                        o.restaurant.name.isBlank() || o.consumer.name.isBlank()) {
                    throw new InvalidRequestException("Restaurant and consumer names cannot be blank");
                }
                allPoints.add(o.restaurant);
                locationIndex.put(o.restaurant.name, idx++);
                allPoints.add(o.consumer);
                locationIndex.put(o.consumer.name, idx++);
            }

            int m = allPoints.size();
            travelTimes = new double[m][m];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    travelTimes[i][j] = GeoUtil.travelTime(allPoints.get(i), allPoints.get(j));
                }
            }

            Set<Integer> visited = new HashSet<>();
            List<String> path = new ArrayList<>();
            Result result = dp(0, visited, orders, path, 0);
            return new RouteResponse(result.path, result.time);
        } catch (InvalidRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to compute optimal route", e);
        }
    }

    static class Result {
        double time;
        List<String> path;
        Result(double time, List<String> path) {
            this.time = time;
            this.path = path;
        }
    }

    private Result dp(int currIdx, Set<Integer> visited, List<Order> orders, List<String> pathSoFar, double currTime) {
        if (visited.size() == orders.size() * 2) {
            return new Result(currTime, new ArrayList<>(pathSoFar));
        }

        Result best = new Result(Double.MAX_VALUE, new ArrayList<>());

        for (int i = 1; i < allPoints.size(); i++) {
            if (visited.contains(i)) continue;
            Location next = allPoints.get(i);

            if (!next.isRestaurant) {
                Order corresponding = orders.get((i - 1) / 2);
                int restIdx = locationIndex.get(corresponding.restaurant.name);
                if (!visited.contains(restIdx)) continue;
            }

            double travel = travelTimes[currIdx][i];
            double waitTime = next.isRestaurant ? Math.max(0, next.prepTime - currTime - travel) : 0;

            visited.add(i);
            pathSoFar.add(next.name);
            Result res = dp(i, visited, orders, pathSoFar, currTime + travel + waitTime);
            if (res.time < best.time) best = res;
            visited.remove(i);
            pathSoFar.remove(pathSoFar.size() - 1);
        }

        return best;
    }
}
