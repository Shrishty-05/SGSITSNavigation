package com.example.sgsitsnavigation;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

public class RouteUtils {

    public static Polyline drawRoute(MapView mapView, ArrayList<GeoPoint> points) {
        Polyline polyline = new Polyline();

        polyline.setPoints(points);
        polyline.setWidth(8f);
        polyline.setColor(0xFF2196F3); // blue

        mapView.getOverlayManager().add(polyline);
        mapView.invalidate();

        return polyline;
    }
}
