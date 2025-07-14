dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))

    // InvUI
    compileOnly("xyz.xenondevs.invui:invui-core:${rootProject.properties["lib.invui.version"]}")
    compileOnly("xyz.xenondevs.invui:inventory-access-r20:${rootProject.properties["lib.invui.version"]}")
    compileOnly("xyz.xenondevs.invui:inventory-access-r21:${rootProject.properties["lib.invui.version"]}")
    compileOnly("xyz.xenondevs.invui:inventory-access-r22:${rootProject.properties["lib.invui.version"]}")
    compileOnly("xyz.xenondevs.invui:inventory-access-r23:${rootProject.properties["lib.invui.version"]}")
    compileOnly("xyz.xenondevs.invui:inventory-access-r24:${rootProject.properties["lib.invui.version"]}")
//    compileOnly("xyz.xenondevs.invui:invui-kotlin:${rootProject.properties["lib.invui.version"]}") {
//        exclude("org.jetbrains.kotlin", "*")
//        exclude("org.jetbrains.kotlinx", "*")
//    }

    compileOnly("me.clip:placeholderapi:${rootProject.properties["lib.placeholderapi.version"]}") // PlaceholderAPI
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}