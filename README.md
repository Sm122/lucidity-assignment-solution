# Delivery Route Optimizer (DP-based)

A Spring Boot REST API that calculates the **minimum total delivery time** for a delivery person (Aman) picking up and delivering orders from multiple restaurants to consumers. The solution uses **Dynamic Programming (DP)** to determine the optimal route under preparation and delivery constraints.

---

## Features

- Calculates optimal delivery route and total time
- Accounts for restaurant food preparation times
- Ensures pickup before delivery for each order
- Scalable to support `n` restaurant-consumer order pairs
- Exposes a REST API to submit orders and retrieve route

---

## Assumptions
- Aman starts at his base location (e.g., home).
- All food must be picked up before it is delivered.
- Each restaurant has a prepTime (in minutes) — Aman may wait if he arrives early.
- Travel time is based on the Haversine distance assuming a speed of 20 km/h.
- Each order has one restaurant and one consumer.
- The system supports up to n orders where n is reasonable for recursive DP (e.g., n ≤ 10).

## Approach
The solution uses **Dynamic programming (recursive backtracking with memoization)** to compute:

1. All possible valid paths where:
   - Each restaurant is visited before its corresponding consumer  
   - Aman waits at restaurants if prep time isn't complete

2. Tracks the total time at each step, considering:
   - Distance traveled between locations  
   - Wait time at restaurants

3. Returns the **path with the minimum total delivery time**
