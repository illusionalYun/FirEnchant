dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))

    implementation("xyz.xenondevs.invui:invui:1.46") // InvUI

    compileOnly("me.clip:placeholderapi:2.11.6") // PlaceholderAPI
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}