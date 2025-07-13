dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))

    // InvUI
    implementation("xyz.xenondevs.invui:invui:${rootProject.properties["lib.invui.version"]}") {
        (1 .. 19).forEach { exclude("xyz.xenondevs.invui", "inventory-access-r$it") }
    }
    implementation("xyz.xenondevs.invui:invui-kotlin:${rootProject.properties["lib.invui.version"]}") {
        exclude("org.jetbrains.kotlin", "*")
        exclude("org.jetbrains.kotlinx", "*")
    }

    compileOnly("me.clip:placeholderapi:${rootProject.properties["lib.placeholderapi.version"]}") // PlaceholderAPI
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}