plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17" // PAPER Weight
}

dependencies {
    implementation(project(":api"))
    paperweight.paperDevBundle("1.21.7-R0.1-SNAPSHOT") // PaperDev
}