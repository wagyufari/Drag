plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.mayburger.drag"
        minSdk = 23
        targetSdk = 30
        versionCode = 43
        versionName = "2.1.1"

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions("default")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        dataBinding = true
        viewBinding = true
    }
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")

    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")


    implementation("com.google.dagger:dagger:2.38.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt("com.google.dagger:dagger-compiler:2.38.1")
    //(Dagger Android
    api("com.google.dagger:dagger-android:2.37")
    api("com.google.dagger:dagger-android-support:2.37")
    kapt("com.google.dagger:dagger-android-processor:2.37")
    //(Dagger - Hilt)
    implementation("com.google.dagger:hilt-android:2.38.1")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

    implementation(kotlinDependencies.kotlin)
    implementation(kotlinDependencies.coroutinesCore)
    implementation(kotlinDependencies.coroutinesAndroid)

    implementation(jetpackDependencies.coreKtx)
    implementation(jetpackDependencies.appCompat)
    implementation(jetpackDependencies.constraintLayout)
    implementation(jetpackDependencies.legacySupport)
    implementation(jetpackDependencies.animatedVectorDrawable)
    implementation(jetpackDependencies.cardView)
    implementation(jetpackDependencies.multiDex)
    implementation(jetpackDependencies.lifecycleExtensions)
    implementation(jetpackDependencies.lifecycleCompiler)
    implementation(jetpackDependencies.lifecycleViewModel)
    implementation(jetpackDependencies.lifecycleLivedata)
    implementation(jetpackDependencies.lifecycleCoroutines)
    implementation(jetpackDependencies.cameraXCore)
    implementation(jetpackDependencies.cameraX2)

    implementation(retrofitDependencies.gson)
    implementation(retrofitDependencies.gsonConverter)
    implementation(retrofitDependencies.retrofit)

//    implementation(googleDependencies.material)
    implementation("com.google.android.material:material:1.3.0")
    implementation(googleDependencies.flexbox)
    implementation(googleDependencies.maps)

    kapt("org.xerial:sqlite-jdbc:3.34.0")

    debugImplementation(chuckDependencies.chuckDebug)
    releaseImplementation(chuckDependencies.chuckRelease)

    implementation(roomDependencies.room)
    implementation(roomDependencies.ktx)
    kapt(roomDependencies.compiler)

    implementation(pagingDependencies.paging)



    // Activity KTX for viewModels()
    implementation("androidx.activity:activity-ktx:1.2.3")
    implementation("android.arch.lifecycle:extensions:1.1.1")
    implementation("com.google.android.gms:play-services-location:18.0.0")
    implementation("com.google.android.gms:play-services-maps:17.0.1")

    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    implementation("com.github.msarhan:ummalqura-calendar:1.1.9")

    implementation(platform("com.google.firebase:firebase-bom:27.1.0"))
    implementation("com.google.firebase:firebase-crashlytics")

    implementation("androidx.work:work-runtime-ktx:2.7.0-alpha05")
    implementation("androidx.work:work-rxjava2:2.7.0-alpha05")

    implementation("com.google.android.play:core:1.10.2")

    implementation("com.pixplicity.easyprefs:EasyPrefs:1.10.0")

    implementation(glideDependencies.glide)
    kapt(glideDependencies.glideCompiler)

    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

}

