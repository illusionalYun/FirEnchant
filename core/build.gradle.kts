dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))
    implementation("xyz.xenondevs.invui:invui:${rootProject.properties["lib.invui.version"]}") // InvUI
    implementation("xyz.xenondevs.invui:invui-kotlin:${rootProject.properties["lib.invui.version"]}") // InvUI

    compileOnly("io.lettuce:lettuce-core:${rootProject.properties["lib.lettuce.version"]}") {
        exclude("io.netty", "*")
    } // Lettuce
    compileOnly("com.j256.ormlite:ormlite-core:${rootProject.properties["lib.ormlite.version"]}") // ORMLite
    compileOnly("com.j256.ormlite:ormlite-jdbc:${rootProject.properties["lib.ormlite.version"]}") // ORMLite
    compileOnly("com.zaxxer:HikariCP:${rootProject.properties["lib.hikaricp.version"]}") {
        exclude("org.slf4j", "*")
    } // HikariCP

    compileOnly("me.clip:placeholderapi:${rootProject.properties["lib.placeholderapi.version"]}") // PlaceholderAPI
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}