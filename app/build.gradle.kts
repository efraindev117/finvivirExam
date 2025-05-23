import com.xsoftcdmx.finvivirexam.build_logic.convention.implementation

plugins {
    alias(libs.plugins.finvivirexam.android.application)
    alias(libs.plugins.finvivirexam.android.application.compose)
    alias(libs.plugins.finvivirexam.android.hilt)
}

android {
    namespace = "com.xsoftcdmx.finvivirexam"

    defaultConfig {
        applicationId = "com.xsoftcdmx.finvivirexam"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

}

dependencies {
    implementation(projects.feature.gmaps)
    implementation(libs.places)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.navigation.compose)
}
