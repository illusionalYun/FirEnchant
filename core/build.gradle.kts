dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}