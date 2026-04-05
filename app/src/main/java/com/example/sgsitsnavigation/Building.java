package com.example.sgsitsnavigation;

public class Building {
    private final String name;
    private final String destinationNodeId;

    public Building(String name, String destinationNodeId) {
        this.name = name;
        this.destinationNodeId = destinationNodeId;
    }

    public String getName() {
        return name;
    }

    public String getDestinationNodeId() {
        return destinationNodeId;
    }

    @Override
    public String toString() {
        return name;
    }
}