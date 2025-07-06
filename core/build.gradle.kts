plugins {
    id("io.papermc.paperweight.userdev") // PAPER Weight
}

dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))
    paperweight.paperDevBundle("1.21.7-R0.1-SNAPSHOT")

    implementation("xyz.xenondevs.invui:invui:1.46") // InvUI

    compileOnly("me.clip:placeholderapi:2.11.6") // PlaceholderAPI
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}