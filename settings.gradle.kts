pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

//nova maneira de adicionar maven
//https://stackoverflow.com/questions/69163511/build-was-configured-to-prefer-settings-repositories-over-project-repositories-b
//https://github.com/jmadaminov/glassmorphic-composables
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io")}
    }
}

rootProject.name = "Streaming Amazing"
include(":app")
