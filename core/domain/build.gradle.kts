plugins {
    alias(libs.plugins.finvivirexam.android.library)
    alias(libs.plugins.finvivirexam.android.hilt)
}

android {
    namespace = "com.xsoftcdmx.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)
    api(projects.core.common)
}