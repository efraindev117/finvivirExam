plugins {
    alias(libs.plugins.finvivirexam.android.library)
    alias(libs.plugins.finvivirexam.android.hilt)
    alias(libs.plugins.finvivirexam.android.room)
}

android {
    namespace = "com.xsoftcdmx.database"
}

dependencies {
    api(projects.core.model)
}