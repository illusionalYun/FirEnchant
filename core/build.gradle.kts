plugins {
    id("java") // Java
}

dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))

    compileOnly("io.lettuce:lettuce-core:${rootProject.properties["lib.lettuce.version"]}") // Lettuce

    compileOnly("me.clip:placeholderapi:${rootProject.properties["lib.placeholderapi.version"]}") // PlaceholderAPI
    implementation("xyz.xenondevs.invui:invui:${rootProject.properties["lib.invui.version"]}") // InvUI
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}