package com.example.sgsitsnavigation;

import android.location.Location;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class NavigationHelper {

    public static ArrayList<GeoPoint> sampleRoutePoints(ArrayList<GeoPoint> route, float minSpacingMeters) {
        ArrayList<GeoPoint> sampled = new ArrayList<>();
        if (route == null || route.isEmpty()) return sampled;

        sampled.add(route.get(0));
        GeoPoint lastAdded = route.get(0);

        for (int i = 1; i < route.size(); i++) {
            GeoPoint current = route.get(i);
            float[] results = new float[1];
            Location.distanceBetween(
                    lastAdded.getLatitude(), lastAdded.getLongitude(),
                    current.getLatitude(), current.getLongitude(),
                    results
            );

            if (results[0] >= minSpacingMeters) {
                sampled.add(current);
                lastAdded = current;
            }
        }

        GeoPoint last = route.get(route.size() - 1);
        if (!samePoint(sampled.get(sampled.size() - 1), last)) {
            sampled.add(last);
        }

        return sampled;
    }

    private static boolean samePoint(GeoPoint a, GeoPoint b) {
        return Math.abs(a.getLatitude() - b.getLatitude()) < 0.000001 &&
                Math.abs(a.getLongitude() - b.getLongitude()) < 0.000001;
    }

    public static int advanceWaypointIfNeeded(Location userLocation, ArrayList<GeoPoint> waypoints, int currentIndex, float reachThresholdMeters) {
        if (userLocation == null || waypoints == null || waypoints.isEmpty()) return currentIndex;
        if (currentIndex >= waypoints.size()) return currentIndex;

        GeoPoint target = waypoints.get(currentIndex);
        float[] results = new float[1];
        Location.distanceBetween(
                userLocation.getLatitude(), userLocation.getLongitude(),
                target.getLatitude(), target.getLongitude(),
                results
        );

        if (results[0] <= reachThresholdMeters) {
            return currentIndex + 1;
        }

        return currentIndex;
    }

    public static float distanceToPoint(Location userLocation, GeoPoint point) {
        if (userLocation == null || point == null) return Float.MAX_VALUE;

        float[] results = new float[1];
        Location.distanceBetween(
                userLocation.getLatitude(), userLocation.getLongitude(),
                point.getLatitude(), point.getLongitude(),
                results
        );
        return results[0];
    }

    public static float bearingToPoint(Location userLocation, GeoPoint point) {
        float[] results = new float[2];
        Location.distanceBetween(
                userLocation.getLatitude(), userLocation.getLongitude(),
                point.getLatitude(), point.getLongitude(),
                results
        );
        return results[1];
    }

    public static float normalizeAngle(float angle) {
        while (angle > 180f) angle -= 360f;
        while (angle < -180f) angle += 360f;
        return angle;
    }
}