plugins {
    alias(libs.plugins.finvivirexam.android.feature)
    alias(libs.plugins.finvivirexam.android.library.compose)
}

android {
    namespace = "com.xsoftcdmx.gmaps"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.places)
    implementation(libs.play.services.maps)
    implementation(libs.maps.compose)
    implementation(libs.timber)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
}