import com.xsoftcdmx.finvivirexam.build_logic.convention.implementation
import com.xsoftcdmx.finvivirexam.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("finvivirexam.android.library")
                apply("finvivirexam.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("finvivirexam.android.library.compose")
            }

            dependencies {
                // Define common dependencies for feature modules
                implementation(libs.findLibrary("androidx-navigation-compose").get())
                implementation(libs.findLibrary("kotlinx-serialization-json").get())
            }
        }
    }
}
