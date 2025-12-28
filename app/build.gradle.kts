plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("jacoco")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.cr_d.passwordmanagerapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.cr_d.passwordmanagerapp"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest") // o testReleaseUnitTest según corresponda

    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
    }

    val debugJavaClasses = fileTree("${buildDir}/intermediates/javac/debug/classes") {
        include("**/passwordmanagerapp/domain/**", "**/passwordmanagerapp/application/**")
        exclude(
            "**/R.class",
            "**/R\$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*"
        )
    }

    val debugKotlinClasses = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
        include("**/passwordmanagerapp/domain/**","**/passwordmanagerapp/application/**")
        exclude(
            "**/R.class",
            "**/R\$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*"
        )
    }

    classDirectories.setFrom(debugJavaClasses + debugKotlinClasses)
    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(fileTree(layout.buildDirectory.asFile).include("**/jacoco/testDebugUnitTest.exec"))
}

dependencies {
    val roomVersion = "2.8.4"

    //Core
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    //UI_UX
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.animation.core.lint)
    implementation("androidx.compose.material:material-icons-extended")

    //Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //Room
    implementation("androidx.room:room-runtime:${roomVersion}")
    implementation("androidx.room:room-ktx:${roomVersion}")
    ksp("androidx.room:room-compiler:$roomVersion")
}