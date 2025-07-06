plugins {
    id("maven-publish") // Maven Publish
    id("io.papermc.paperweight.userdev") // PAPER Weight
}


repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.7-R0.1-SNAPSHOT") // PAPER Weight
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