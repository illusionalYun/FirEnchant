rootProject.name = "FirEnchantKt"
include(":api")
include(":compatibility")
include(":core")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/") // PAPER Weight
    }
}