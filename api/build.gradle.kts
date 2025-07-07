plugins {
    id("maven-publish") // Maven Publish
}

dependencies {
    compileOnly("com.j256.ormlite:ormlite-core:${rootProject.properties["lib.ormlite.version"]}")
}

// 发布到 Maven 仓库
publishing {
    repositories {
        maven {
            name = "Catnies"
            url = uri("https://repo.catnies.top/releases")
            credentials(PasswordCredentials::class)
            authentication { create<BasicAuthentication>("basic") }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "top.catnies"
            artifactId = "firenchantkt"
            version = "${rootProject.properties["project.version"]}"
            from(components["java"])
        }
    }
}