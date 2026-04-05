plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.sgsitsnavigation"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sgsitsnavigation"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.cardview:cardview:1.0.0")

    // CameraX
    implementation("androidx.camera:camera-core:1.4.2")
    implementation("androidx.camera:camera-camera2:1.4.2")
    implementation("androidx.camera:camera-lifecycle:1.4.2")
    implementation("androidx.camera:camera-view:1.4.2")

    // Location
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // osmdroid
    implementation("org.osmdroid:osmdroid-android:6.1.20")}

//plugins {
//    id("com.android.application")
//}
//
//android {
//    namespace = "com.example.sgsitsnavigation"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "com.example.sgsitsoutdoornavigation"
//        minSdk = 26
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"
//
//        val mapsApiKey = project.findProperty("MAPS_API_KEY") as String? ?: ""
//        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//
//    buildFeatures {
//        viewBinding = true
//    }
//}
//
//dependencies {
//    implementation("androidx.appcompat:appcompat:1.7.0")
//    implementation("com.google.android.material:material:1.12.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
//    implementation("androidx.cardview:cardview:1.0.0")
//
//    // CameraX
//    implementation("androidx.camera:camera-core:1.4.2")
//    implementation("androidx.camera:camera-camera2:1.4.2")
//    implementation("androidx.camera:camera-lifecycle:1.4.2")
//    implementation("androidx.camera:camera-view:1.4.2")
//
//    // Maps + location
//    implementation("com.google.android.gms:play-services-maps:19.2.0")
//    implementation("com.google.android.gms:play-services-location:21.3.0")
//}