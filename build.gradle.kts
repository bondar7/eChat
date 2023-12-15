// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.21")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}