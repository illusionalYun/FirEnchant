rootProject.name = "FirEnchantKt"

include(":api")
include(":compatibility")
include(":core")
include("nms:v1_21_R1")
include("nms:v1_21_R2")
include("nms:v1_21_R3")
include("nms:v1_21_R4")
include("nms:v1_21_R5")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/") // PAPER Weight
    }
}
