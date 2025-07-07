dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))

    compileOnly("io.lettuce:lettuce-core:6.5.3.RELEASE") // Lettuce

    compileOnly("me.clip:placeholderapi:2.11.6") // PlaceholderAPI
    implementation("xyz.xenondevs.invui:invui:1.46") // InvUI
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}