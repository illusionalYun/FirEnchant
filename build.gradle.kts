// 插件
plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.21" // Kotlin
    id("com.gradleup.shadow") version "9.0.0-beta6" // Shadow
    id("xyz.jpenilla.run-paper") version "2.3.1" // Run Paper
}

// 插件仓库
repositories {
    gradlePluginPortal()
}

// 依赖所有子模块
dependencies {
    implementation(project(":core"))
    implementation(project(":api"))
    implementation(project(":compatibility"))
}


// 需要将一些部分统一进行配置，可以直接在根项目创建一个build.gradle.kts来统一编写内容：
allprojects {
    // 应用插件到子项目
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.gradleup.shadow")

    group = "top.catnies"
    version = "3.0.0"
    kotlin.jvmToolchain(21)

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") // PaperDev

        maven("https://maven.devs.beer/") // ItemsAdder
        maven("https://repo.nexomc.com/releases") // Nexo
        maven("https://repo.oraxen.com/releases") // Oraxen
        maven("https://mvn.lumine.io/repository/maven-public/") // MythicMobs
        maven("https://repo.momirealms.net/releases/") // CustomCrops, CustomFishing, CraftEngine
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // PlaceholderAPI

        maven("https://jitpack.io") // RTag
    }

    dependencies {
        // 基础库
        compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT") // PAPER
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // Kotlin STD

        // 依赖库
        implementation("com.saicone.rtag:rtag:1.5.11") // RTag
        implementation("com.saicone.rtag:rtag-item:1.5.11") // RTag
    }

    tasks.shadowJar {
        relocate("com.saicone.rtag", "${project.group}.libs.rtag")
    }
}

// 任务配置
tasks.build { dependsOn(tasks.shadowJar) }
tasks.runServer { minecraftVersion("1.21.4") }