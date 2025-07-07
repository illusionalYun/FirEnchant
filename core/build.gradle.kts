dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))

    implementation("xyz.xenondevs.invui:invui:${rootProject.properties["lib.invui.version"]}") // InvUI
    implementation("io.lettuce:lettuce-core:${rootProject.properties["lib.lettuce.version"]}") // Lettuce

    implementation("com.j256.ormlite:ormlite-core:${rootProject.properties["lib.ormlite.version"]}")
    implementation("com.j256.ormlite:ormlite-jdbc:${rootProject.properties["lib.ormlite.version"]}") // ORMLite
    implementation("com.zaxxer:HikariCP:${rootProject.properties["lib.hikaricp.version"]}") // HikariCP

    implementation("com.h2database:h2:${rootProject.properties["database.driver.h2.version"]}")
    implementation("com.mysql:mysql-connector-j:${rootProject.properties["database.driver.mysql.version"]}")

    compileOnly("me.clip:placeholderapi:${rootProject.properties["lib.placeholderapi.version"]}") // PlaceholderAPI
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}