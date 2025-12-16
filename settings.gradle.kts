pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}




dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // 녹음 모듈 사용하려고
        maven { url=uri("https://jitpack.io") }
    }
}

rootProject.name = "android_java"
include(":app")
 