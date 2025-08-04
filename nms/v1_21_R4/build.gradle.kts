plugins {
    alias(libs.plugins.paperweight) // PAPER Weight
}

dependencies {
    implementation(project(":api"))
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT") // PaperDev
}