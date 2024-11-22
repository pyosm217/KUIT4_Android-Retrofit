import java.io.FileInputStream
import java.util.Properties
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

val properties = Properties().apply {
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    } else {
        println("local.properties 파일을 찾을 수 없습니다. 기본 값을 사용합니다.")
    }
}

android {
    namespace = "com.example.kuit4_android_retrofit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kuit4_android_retrofit"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val baseUrl = properties.getProperty("BASE_URL", "http://default-url.com")
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.github.bumptech.glide:glide:4.16.0")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    val retrofit_version = "2.6.1"
    // Retrofit 라이브러리
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    // Gson Converter 라이브러리
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    // Scalars Converter 라이브러리
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit_version")
}
