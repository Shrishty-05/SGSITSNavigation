package com.example.sgsitsnavigation;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NavigationController {

    public interface RouteCallback {
        void onRouteReady(ArrayList<GeoPoint> routePoints);
        void onError(String error);
    }

    public void fetchRoute(GeoPoint origin, GeoPoint destination, RouteCallback callback) {

        new Thread(() -> {
            try {
                String urlStr = "https://router.project-osrm.org/route/v1/foot/"
                        + origin.getLongitude() + "," + origin.getLatitude()
                        + ";"
                        + destination.getLongitude() + "," + destination.getLatitude()
                        + "?overview=full&geometries=geojson";

                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder json = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                reader.close();

                JSONObject response = new JSONObject(json.toString());
                JSONArray routes = response.getJSONArray("routes");

                if (routes.length() == 0) {
                    callback.onError("No route found");
                    return;
                }

                JSONObject route = routes.getJSONObject(0);
                JSONObject geometry = route.getJSONObject("geometry");
                JSONArray coordinates = geometry.getJSONArray("coordinates");

                ArrayList<GeoPoint> points = new ArrayList<>();

                for (int i = 0; i < coordinates.length(); i++) {
                    JSONArray coord = coordinates.getJSONArray(i);
                    double lon = coord.getDouble(0);
                    double lat = coord.getDouble(1);

                    points.add(new GeoPoint(lat, lon));
                }

                callback.onRouteReady(points);

            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }
}
