plugins {
    alias(libs.plugins.finvivirexam.android.library)
    alias(libs.plugins.finvivirexam.android.hilt)
}

android {
    namespace = "com.xsoftcdmx.finvivirexam.core.common"
}

dependencies {
    implementation(libs.retrofit)
}