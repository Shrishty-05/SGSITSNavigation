package com.example.sgsitsnavigation;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class CampusGraph {

    private final Map<String, CampusNode> nodes = new HashMap<>();
    private final Map<String, ArrayList<CampusEdge>> adjacencyList = new HashMap<>();

    public void addNode(CampusNode node) {
        nodes.put(node.getId(), node);
        adjacencyList.putIfAbsent(node.getId(), new ArrayList<>());
    }

    public CampusNode getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public void addBidirectionalEdge(String nodeA, String nodeB, double weight) {
        adjacencyList.get(nodeA).add(new CampusEdge(nodeA, nodeB, weight));
        adjacencyList.get(nodeB).add(new CampusEdge(nodeB, nodeA, weight));
    }

    public ArrayList<GeoPoint> findShortestPath(String startNodeId, String endNodeId) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        HashSet<String> visited = new HashSet<>();

        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(
                (a, b) -> Double.compare(a.distance, b.distance)
        );

        for (String nodeId : nodes.keySet()) {
            distances.put(nodeId, Double.MAX_VALUE);
        }

        distances.put(startNodeId, 0.0);
        pq.add(new NodeDistance(startNodeId, 0.0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();

            if (visited.contains(current.nodeId)) continue;
            visited.add(current.nodeId);

            if (current.nodeId.equals(endNodeId)) break;

            ArrayList<CampusEdge> neighbors = adjacencyList.get(current.nodeId);
            if (neighbors == null) continue;

            for (CampusEdge edge : neighbors) {
                String nextNodeId = edge.getToNodeId();
                double newDistance = distances.get(current.nodeId) + edge.getWeight();

                if (newDistance < distances.get(nextNodeId)) {
                    distances.put(nextNodeId, newDistance);
                    previous.put(nextNodeId, current.nodeId);
                    pq.add(new NodeDistance(nextNodeId, newDistance));
                }
            }
        }

        return buildGeoPath(previous, startNodeId, endNodeId);
    }

    private ArrayList<GeoPoint> buildGeoPath(Map<String, String> previous, String startNodeId, String endNodeId) {
        ArrayList<String> nodePath = new ArrayList<>();
        String current = endNodeId;

        while (current != null) {
            nodePath.add(0, current);
            if (current.equals(startNodeId)) break;
            current = previous.get(current);
        }

        ArrayList<GeoPoint> geoPath = new ArrayList<>();

        if (nodePath.isEmpty() || !nodePath.get(0).equals(startNodeId)) {
            return geoPath;
        }

        for (String nodeId : nodePath) {
            CampusNode node = nodes.get(nodeId);
            if (node != null) {
                geoPath.add(new GeoPoint(node.getLatitude(), node.getLongitude()));
            }
        }

        return geoPath;
    }

    public String findNearestNode(double userLat, double userLon) {
        String nearestNodeId = null;
        double minDistance = Double.MAX_VALUE;

        for (CampusNode node : nodes.values()) {
            double distance = distanceBetween(userLat, userLon, node.getLatitude(), node.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestNodeId = node.getId();
            }
        }

        return nearestNodeId;
    }

    private double distanceBetween(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371000.0;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    private static class NodeDistance {
        String nodeId;
        double distance;

        NodeDistance(String nodeId, double distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }
    }
}