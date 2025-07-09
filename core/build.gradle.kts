dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))

    // ORMLite
    compileOnly("com.j256.ormlite:ormlite-core:${rootProject.properties["lib.ormlite.version"]}")
    compileOnly("com.j256.ormlite:ormlite-jdbc:${rootProject.properties["lib.ormlite.version"]}")

    // HikariCP
    compileOnly("com.zaxxer:HikariCP:${rootProject.properties["lib.hikaricp.version"]}") {
        exclude("org.slf4j", "*")
    }

    // Lettuce
    compileOnly("io.lettuce:lettuce-core:${rootProject.properties["lib.lettuce.version"]}") {
        exclude("io.netty", "*")
    }

    // InvUI
    implementation("xyz.xenondevs.invui:invui-kotlin:${rootProject.properties["lib.invui.version"]}") {
        exclude("org.jetbrains.kotlin", "*")
        exclude("org.jetbrains.kotlinx", "*")
    }
    implementation("xyz.xenondevs.invui:invui:${rootProject.properties["lib.invui.version"]}") {
        (1 .. 19).forEach { exclude("xyz.xenondevs.invui", "inventory-access-r$it") }
    }

    compileOnly("me.clip:placeholderapi:${rootProject.properties["lib.placeholderapi.version"]}") // PlaceholderAPI
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}