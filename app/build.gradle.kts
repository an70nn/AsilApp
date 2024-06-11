plugins {
    alias(libs.plugins.androidApplication)

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.asilapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.asilapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.gridlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Dipendenze per l'accesso al Database Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore:21.0.0") {
        exclude(group = "com.google.firebase", module = "firebase-common")
    }
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage:21.0.0")

    //Dipendenze per la gestione dei Fragment
    implementation("androidx.fragment:fragment:1.6.2")

    //Dipendenza per il servizio di geolocalizzazione di Open Street Maps
    implementation("org.osmdroid:osmdroid-android:6.1.10")

    //Dipendenza per la gestione di versioni API inferiore alla 26
    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")
}