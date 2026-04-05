package com.example.sgsitsnavigation;

public class CampusEdge {
    private final String fromNodeId;
    private final String toNodeId;
    private final double weight;

    public CampusEdge(String fromNodeId, String toNodeId, double weight) {
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.weight = weight;
    }

    public String getFromNodeId() {
        return fromNodeId;
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public double getWeight() {
        return weight;
    }
}