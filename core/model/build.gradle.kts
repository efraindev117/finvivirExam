plugins {
    alias(libs.plugins.finvivirexam.android.library)
    alias(libs.plugins.finvivirexam.android.hilt)
}

android {
    namespace = "com.xsoftcdmx.finvivirexam.core.model"
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
}