package com.example.lucidityAssignment.model;

public class Order {
    public Location restaurant;
    public Location consumer;

    public Order() {}
    public Order(Location restaurant, Location consumer) {
        this.restaurant = restaurant;
        this.consumer = consumer;
    }
}
