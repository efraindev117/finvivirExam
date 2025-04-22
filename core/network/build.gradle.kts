plugins {
    alias(libs.plugins.finvivirexam.android.library)
    alias(libs.plugins.finvivirexam.android.hilt)
}

android {
    namespace = "com.xsoftcdmx.network"
}

dependencies {
    // network
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //flows
    implementation(libs.coroutines.core)
}