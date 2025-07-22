dependencies {
    implementation(project(":api"))
    implementation(project(":compatibility"))
    compileOnly(rootProject.libs.bundles.invui) {
        exclude("org.jetbrains.kotlin", "*")
        exclude("org.jetbrains.kotlinx", "*")
    }
}

// 确保 plugin.yml 被正确处理
tasks.processResources {
    expand("version" to project.version)
}