package com.example.sgsitsnavigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

//import com.example.sgsitsnavigation.R;
//import org.osmdroid.views.overlay.Polyline;

import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.sgsitsnavigation.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.LocationServices;
import com.google.common.util.concurrent.ListenableFuture;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


//    fake golden gate location
    private boolean USE_FAKE_LOCATION = true;

//    22.7261965/75.8739759
    private static final double FAKE_LAT = 22.726276;
    private static final double FAKE_LON = 75.874141;

    private Spinner spinnerStart;
    private boolean USE_MANUAL_START = true;
    private String manualStartNodeId = "GATE";

    private PreviewView previewView;
    private Spinner spinnerDestinations;
    private MapView mapView;

    private CampusGraph campusGraph;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private Marker userMarker;
    private boolean firstMapCenterDone = false;

    private NavigationController navigationController;
    private ArrayList<GeoPoint> currentRoute = new ArrayList<>();
    private org.osmdroid.views.overlay.Polyline currentPolyline;

    // phase 2 B
    private ArrowOverlayView arrowOverlayView;

    private ArrayList<GeoPoint> sampledWaypoints = new ArrayList<>();
    private int currentWaypointIndex = 0;
    private GeoPoint currentDestinationPoint;

    private Location lastKnownLocation;

    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private float currentAzimuthDegrees = 0f;

    private boolean destinationReached = false;

    private Marker destinationMarker;
    private long lastMapAnimateTime = 0L;

    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    result -> {
                        boolean cameraGranted = Boolean.TRUE.equals(result.get(Manifest.permission.CAMERA));
                        boolean fineGranted = Boolean.TRUE.equals(result.get(Manifest.permission.ACCESS_FINE_LOCATION));
                        boolean coarseGranted = Boolean.TRUE.equals(result.get(Manifest.permission.ACCESS_COARSE_LOCATION));

                        if (cameraGranted && (fineGranted || coarseGranted)) {
                            initializeApp();
                        } else {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Camera and location permissions are required.",
                                    Toast.LENGTH_LONG
                            ).show();
                            finish();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(
                getApplicationContext(),
                getSharedPreferences("osmdroid_prefs", MODE_PRIVATE)
        );
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_main);

        arrowOverlayView = findViewById(R.id.arrowOverlayView);
        previewView = findViewById(R.id.previewView);
        spinnerStart = findViewById(R.id.SpinnerStart);
        spinnerDestinations = findViewById(R.id.spinnerDestinations);
        mapView = findViewById(R.id.mapView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        campusGraph = CampusGraphData.createCampusGraph();

        setupStartSpinner();
        setupDestinationSpinner();

        if (hasAllPermissions()) {
            initializeApp();
        } else {
            permissionLauncher.launch(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }
    private void initializeApp() {
        initMap();
        startCamera();
        startLocationUpdates();
    }

    private void setupStartSpinner() {

        List<Building> buildings = CampusData.getBuildings();

        ArrayAdapter<Building> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                buildings
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStart.setAdapter(adapter);

        spinnerStart.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {

                if (position == 0) return;

                Building selected = (Building) parent.getItemAtPosition(position);
                manualStartNodeId = selected.getDestinationNodeId();

                Toast.makeText(MainActivity.this,
                        "Start set to: " + selected.getName(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
    }

    private void setupDestinationSpinner() {
        List<Building> buildings = CampusData.getBuildings();

        ArrayAdapter<Building> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                buildings
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestinations.setAdapter(adapter);

        spinnerDestinations.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {

                if (position == 0) return;

                Building selected = (Building) parent.getItemAtPosition(position);

                if (lastKnownLocation == null) {
                    Toast.makeText(MainActivity.this, "Waiting for GPS...", Toast.LENGTH_SHORT).show();
                    return;
                }
//
//                String startNodeId = campusGraph.findNearestNode(
//                        lastKnownLocation.getLatitude(),
//                        lastKnownLocation.getLongitude()
//                );

                String startNodeId;

                if (USE_MANUAL_START) {
                    startNodeId = manualStartNodeId;
                } else {
                    startNodeId = campusGraph.findNearestNode(
                            lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()
                    );
                }

                String destinationNodeId = selected.getDestinationNodeId();

                if (startNodeId == null || destinationNodeId == null || destinationNodeId.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Invalid route nodes", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<GeoPoint> routePoints = campusGraph.findShortestPath(startNodeId, destinationNodeId);
                showGraphRoute(routePoints, selected.getName());
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
    }

    private boolean hasAllPermissions() {
        boolean cameraGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;

        boolean fineLocationGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        boolean coarseLocationGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        return cameraGranted && (fineLocationGranted || coarseLocationGranted);
    }

    private void initMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(false);
        mapView.setBuiltInZoomControls(false);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mapView.setTilesScaledToDpi(true);

        // Default center: SGSITS Golden Gate area
        GeoPoint defaultPoint = new GeoPoint(FAKE_LAT, FAKE_LON);
        mapView.getController().setZoom(18.0);
        mapView.getController().setCenter(defaultPoint);
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview
                );

            } catch (Exception e) {
                Toast.makeText(
                        MainActivity.this,
                        "Camera start failed: " + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationRequest locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                2000
        )
                .setMinUpdateIntervalMillis(1000)
                .build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    updateUserOnMap(location);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                getMainLooper()
        );
    }

    private void updateUserOnMap(Location location) {

//        boolean USE_FAKE_LOCATION = true;

        if (USE_FAKE_LOCATION) {
            Location fakeLocation = new Location(location);
            fakeLocation.setLatitude(22.72630);
            fakeLocation.setLongitude(75.87413);
            location = fakeLocation;
        }
        lastKnownLocation = location;

        GeoPoint userPoint = new GeoPoint(
                location.getLatitude(),
                location.getLongitude()
        );

        if (userMarker == null) {
            userMarker = new Marker(mapView);
            userMarker.setPosition(userPoint);
            userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            userMarker.setTitle("You are here");
            mapView.getOverlays().add(userMarker);
        } else {
            userMarker.setPosition(userPoint);
        }

        if (!firstMapCenterDone) {
            mapView.getController().setZoom(18.0);
            mapView.getController().setCenter(userPoint);
            firstMapCenterDone = true;
        } else {
            long now = System.currentTimeMillis();
            if (now - lastMapAnimateTime > 2000) {
                mapView.getController().animateTo(userPoint);
                lastMapAnimateTime = now;
            }
        }

        mapView.invalidate();
        updateArrowGuidance();
        checkDestinationReached();
    }

    private void showGraphRoute(ArrayList<GeoPoint> routePoints, String destinationName) {

        if (routePoints == null || routePoints.isEmpty()) {
            Toast.makeText(this, "No path found", Toast.LENGTH_LONG).show();
            return;
        }

        if (currentPolyline != null) {
            mapView.getOverlays().remove(currentPolyline);
            currentPolyline = null;
        }

        currentRoute = routePoints;
        sampledWaypoints = new ArrayList<>(routePoints);
        currentWaypointIndex = 0;
        currentDestinationPoint = routePoints.get(routePoints.size() - 1);
        destinationReached = false;

        arrowOverlayView.clearArrows();

        currentPolyline = RouteUtils.drawRoute(mapView, routePoints);

        if (destinationMarker != null) {
            mapView.getOverlays().remove(destinationMarker);
        }

        destinationMarker = new Marker(mapView);
        destinationMarker.setPosition(currentDestinationPoint);
        destinationMarker.setTitle(destinationName);
        mapView.getOverlays().add(destinationMarker);

        mapView.getController().animateTo(currentDestinationPoint);
        mapView.invalidate();
    }
//    private void fetchRoute(GeoPoint origin, GeoPoint destination) {
//
//        Toast.makeText(this, "Fetching route...", Toast.LENGTH_SHORT).show();
//
//        navigationController.fetchRoute(origin, destination, new NavigationController.RouteCallback() {
//            @Override
//            public void onRouteReady(ArrayList<GeoPoint> routePoints) {
//                runOnUiThread(() -> {
//
//                    // Clear old route
//                    if (currentPolyline != null) {
//                        mapView.getOverlays().remove(currentPolyline);
//                        currentPolyline = null;
//                    }
//
//                    // Reset navigation state
//                    currentRoute = routePoints;
//                    sampledWaypoints = NavigationHelper.sampleRoutePoints(routePoints, 18f);
//                    currentWaypointIndex = 0;
//                    currentDestinationPoint = destination;
//                    destinationReached = false;
//
//                    // Clear old arrows
//                    arrowOverlayView.clearArrows();
//
//                    // Draw new route
//                    currentPolyline = RouteUtils.drawRoute(mapView, routePoints);
//
//                    // Clear old destination marker
//                    if (destinationMarker != null) {
//                        mapView.getOverlays().remove(destinationMarker);
//                    }
//
//                    // Add new destination marker
//                    destinationMarker = new Marker(mapView);
//                    destinationMarker.setPosition(destination);
//                    destinationMarker.setTitle("Destination");
//                    mapView.getOverlays().add(destinationMarker);
//
//                    // Optional camera move
//                    mapView.getController().animateTo(destination);
//
//                    mapView.invalidate();
//                });
//            }
//
//            @Override
//            public void onError(String error) {
//                runOnUiThread(() ->
//                        Toast.makeText(MainActivity.this, "Route error: " + error, Toast.LENGTH_LONG).show()
//                );
//            }
//        });
//    }

    private void updateArrowGuidance() {
        if (lastKnownLocation == null || sampledWaypoints == null || sampledWaypoints.isEmpty()) {
            arrowOverlayView.clearArrows();
            return;
        }

        currentWaypointIndex = NavigationHelper.advanceWaypointIfNeeded(
                lastKnownLocation,
                sampledWaypoints,
                currentWaypointIndex,
                12f
        );

        if (currentWaypointIndex >= sampledWaypoints.size()) {
            arrowOverlayView.clearArrows();
            return;
        }

        ArrayList<ArrowOverlayView.ArrowData> arrows = new ArrayList<>();

        int maxArrows = 3;
        int added = 0;
        float lastAcceptedDistance = -1000f;

        for (int i = currentWaypointIndex; i < sampledWaypoints.size() && added < maxArrows; i++) {
            GeoPoint point = sampledWaypoints.get(i);

            float distanceMeters = NavigationHelper.distanceToPoint(lastKnownLocation, point);

            // skip points too close to previous shown arrow
            if (lastAcceptedDistance >= 0 && Math.abs(distanceMeters - lastAcceptedDistance) < 12f) {
                continue;
            }

            float targetBearing = NavigationHelper.bearingToPoint(lastKnownLocation, point);
            float relativeAngle = NavigationHelper.normalizeAngle(targetBearing - currentAzimuthDegrees);

            boolean shouldShowArrow = Math.abs(relativeAngle) <= 95f;
            if (!shouldShowArrow) {
                continue;
            }

            String label = (int) distanceMeters + " m";
            float verticalOffset = added * 230f;

            arrows.add(new ArrowOverlayView.ArrowData(
                    relativeAngle,
                    true,
                    label,
                    verticalOffset
            ));

            lastAcceptedDistance = distanceMeters;
            added++;
        }

        if (arrows.isEmpty()) {
            arrowOverlayView.clearArrows();
        } else {
            arrowOverlayView.updateArrows(arrows);
        }
    }
    private void checkDestinationReached() {
        if (destinationReached || lastKnownLocation == null || currentDestinationPoint == null) return;

        float distance = NavigationHelper.distanceToPoint(lastKnownLocation, currentDestinationPoint);

        if (distance <= 12f) {
            destinationReached = true;
//            arrowOverlayView.updateArrow(0f, false, "");
            arrowOverlayView.clearArrows();

            new AlertDialog.Builder(this)
                    .setTitle("Destination Reached")
                    .setMessage("You have reached your destination.")
                    .setCancelable(false)
                    .setPositiveButton("Exit", (dialog, which) -> finish())
                    .show();
        }
    }

    private void stopLocationUpdates() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }

        if (sensorManager != null && rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
        stopLocationUpdates();

        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }
//    private void checkDestinationReached() {
//        if (destinationReached || lastKnownLocation == null || currentDestinationPoint == null) return;
//
//        float distance = NavigationHelper.distanceToPoint(lastKnownLocation, currentDestinationPoint);
//
//        if (distance <= 12f) {
//            destinationReached = true;
//            arrowOverlayView.updateArrow(0f, false, "");
//
//            new AlertDialog.Builder(this)
//                    .setTitle("Destination Reached")
//                    .setMessage("You have reached your destination.")
//                    .setCancelable(false)
//                    .setPositiveButton("Exit", (dialog, which) -> finish())
//                    .show();
//        }
//    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotationMatrix = new float[9];
            float[] orientationAngles = new float[3];

            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            float azimuthRadians = orientationAngles[0];
            currentAzimuthDegrees = (float) Math.toDegrees(azimuthRadians);

            if (currentAzimuthDegrees < 0) {
                currentAzimuthDegrees += 360f;
            }

            updateArrowGuidance();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // no-op
    }
}