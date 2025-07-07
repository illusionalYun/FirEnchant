plugins {
    id("maven-publish") // Maven Publish
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
            version = "3.0.0-beta-1"
            from(components["java"])
        }
    }
}