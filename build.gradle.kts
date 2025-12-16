// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")

    }
}
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("androidx.navigation.safeargs") version "2.7.7" apply false // 여기가 빠졌었음
}
