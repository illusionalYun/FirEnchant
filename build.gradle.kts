// 插件
plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.21" // Kotlin
    id("com.gradleup.shadow") version "9.0.0-beta6" // Shadow
    id("xyz.jpenilla.run-paper") version "2.3.1" // Run Paper
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17" apply false // PAPER Weight
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
    implementation(project(":nms:v1_21_R1"))
    implementation(project(":nms:v1_21_R2"))
    implementation(project(":nms:v1_21_R3"))
    implementation(project(":nms:v1_21_R4"))
    implementation(project(":nms:v1_21_R5"))
}


// 需要将一些部分统一进行配置，可以直接在根项目创建一个build.gradle.kts来统一编写内容：
allprojects {
    // 应用插件到子项目
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.gradleup.shadow")

    group = "top.catnies"
    version = "${rootProject.properties["project.version"]}"
    kotlin.jvmToolchain(21)
    java.sourceCompatibility = JavaVersion.VERSION_21
    java.targetCompatibility = JavaVersion.VERSION_21

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") // Paper
        maven("https://maven.devs.beer/") // ItemsAdder
        maven("https://repo.nexomc.com/releases") // Nexo
        maven("https://repo.oraxen.com/releases") // Oraxen
        maven("https://mvn.lumine.io/repository/maven-public/") // MythicMobs
        maven("https://repo.momirealms.net/releases/") // CustomCrops, CustomFishing, CraftEngine
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // PlaceholderAPI
        maven("https://maven.chengzhimeow.cn/releases") // 橙汁喵

        maven("https://jitpack.io") // RTag
        maven("https://repo.xenondevs.xyz/releases") // InvUI
    }

    dependencies {
        // 基础库
        compileOnly("io.papermc.paper:paper-api:${rootProject.properties["server.paper.version"]}") // PAPER
        implementation("cn.chengzhiya:MHDF-Scheduler:1.0.1") // 調度器
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // Kotlin STD

        compileOnly("org.projectlombok:lombok:${rootProject.properties["lib.lombok.version"]}")
        annotationProcessor("org.projectlombok:lombok:${rootProject.properties["lib.lombok.version"]}") // Lombok

        // 依赖库
        implementation("com.saicone.rtag:rtag:${rootProject.properties["lib.rtag.version"]}") // RTag
        implementation("com.saicone.rtag:rtag-item:${rootProject.properties["lib.rtag.version"]}") // RTag
    }
}


// 任务配置
tasks {
    clean {
        delete("$rootDir/target")
    }
    shadowJar {
        dependsOn(":nms:v1_21_R1:reobfJar")
        dependsOn(":nms:v1_21_R2:reobfJar")
        dependsOn(":nms:v1_21_R3:reobfJar")
        dependsOn(":nms:v1_21_R4:reobfJar")
        dependsOn(":nms:v1_21_R5:reobfJar")

        archiveFileName = "${rootProject.name}-$version.jar"
        destinationDirectory.set(file("$rootDir/target"))

        relocate("com.saicone.rtag", "${project.group}.libs.rtag")
    }
    assemble {
        dependsOn(shadowJar)
    }
}


// 测试服务器
tasks.runServer {
    minecraftVersion("1.21.4")
}