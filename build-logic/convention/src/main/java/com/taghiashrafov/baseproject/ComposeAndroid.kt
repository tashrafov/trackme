import com.android.build.api.dsl.CommonExtension
import com.taghiashrafov.baseproject.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) = commonExtension.apply {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.findVersion("composeBom").get().toString()
    }

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        val compose = libs.findLibrary("androidx-activity-compose").get()
        val androidXUI = libs.findLibrary("androidx-ui").get()
        val androidXUIGraphics = libs.findLibrary("androidx-ui-graphics").get()
        val androidXUIToolingPreview = libs.findLibrary("androidx-ui-tooling-preview").get()
        val androidXMaterial3 = libs.findLibrary("androidx-material3").get()
        add("implementation", compose)
        add("implementation", platform(bom))
        add("implementation", androidXUI)
        add("implementation", androidXUIGraphics)
        add("implementation", androidXUIToolingPreview)
        add("implementation", androidXMaterial3)
        add("androidTestImplementation", platform(bom))
        add("coreLibraryDesugaring", libs.findLibrary("android-desugar").get())
    }
}