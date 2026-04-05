package com.example.sgsitsnavigation;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {

    public static final int REQUEST_CODE_PERMISSIONS = 1001;

    public static final String[] REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static boolean hasAllPermissions(Activity activity) {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestAllPermissions(Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
        );
    }
}

//package com.example.sgsitsnavigation;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.pm.PackageManager;
//
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//public class PermissionHelper {
//
//    public static final int REQUEST_CODE_PERMISSIONS = 1001;
//
//    public static final String[] REQUIRED_PERMISSIONS = new String[]{
//            Manifest.permission.CAMERA,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//    };
//
//    public static boolean hasAllPermissions(Activity activity) {
//        for (String permission : REQUIRED_PERMISSIONS) {
//            if (ContextCompat.checkSelfPermission(activity, permission)
//                    != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public static void requestAllPermissions(Activity activity) {
//        ActivityCompat.requestPermissions(
//                activity,
//                REQUIRED_PERMISSIONS,
//                REQUEST_CODE_PERMISSIONS
//        );
//    }
//}