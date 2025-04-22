plugins {
    alias(libs.plugins.finvivirexam.android.library)
    alias(libs.plugins.finvivirexam.android.hilt)
}

android {
    namespace = "com.xsoftcdmx.finvivirexam.core.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    implementation(libs.retrofit)
    implementation(projects.core.network)

}