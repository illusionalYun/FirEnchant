plugins {
    id("io.papermc.paperweight.userdev") // PAPER Weight
}

repositories {
    mavenCentral()
    maven("https://repo.xenondevs.xyz/releases") // InvUI
}

dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))
    paperweight.paperDevBundle("1.21.7-R0.1-SNAPSHOT")

    implementation("xyz.xenondevs.invui:invui:1.46") // InvUI
}

tasks.shadowJar {
    relocate("com.saicone.rtag", "${project.group}.libs.rtag")
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}