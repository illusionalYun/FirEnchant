dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))

    compileOnly("me.clip:placeholderapi:2.11.6") // PlaceholderAPI
}

tasks.shadowJar {
    relocate("com.saicone.rtag", "${project.group}.libs.rtag")
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}