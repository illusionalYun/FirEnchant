// 插件
plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.21" // Kotlin
    id("com.gradleup.shadow") version "9.0.0-beta6" // Shadow
    id("xyz.jpenilla.run-paper") version "2.3.1" // Run Paper
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17" apply false // Paper Weight
}

// 依赖所有子模块
dependencies {
    implementation(project(":core"))
    implementation(project(":api"))
    implementation(project(":compatibility"))
    implementation(project(path = ":nms:v1_21_R1"))
    implementation(project(path = ":nms:v1_21_R2"))
    implementation(project(path = ":nms:v1_21_R3"))
    implementation(project(path = ":nms:v1_21_R4"))
    implementation(project(path = ":nms:v1_21_R5"))
}


// 需要将一些部分统一进行配置，可以直接在根项目使用`subprojects`或`allprojects`编写内容
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
        maven("https://jitpack.io") // RTag
        maven("https://repo.xenondevs.xyz/releases") // InvUI
        maven("https://repo.papermc.io/repository/maven-public/") // Paper
        maven("https://maven.devs.beer/") // ItemsAdder
        maven("https://repo.nexomc.com/releases") // Nexo
        maven("https://repo.oraxen.com/releases") // Oraxen
        maven("https://mvn.lumine.io/repository/maven-public/") // MythicMobs
        maven("https://repo.momirealms.net/releases/") // CustomCrops, CustomFishing, CraftEngine
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // PlaceholderAPI
        maven("https://maven.chengzhimeow.cn/releases") // ChengZhiMeow
        maven("https://repo.nightexpressdev.com/releases") // CoinsEngine
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:${rootProject.properties["server.paper.version"]}") // PAPER
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // Kotlin STD
        compileOnly("org.jetbrains.kotlin:kotlin-reflect:2.1.21") // Kotlin Reflect
        compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1") // Kotlin Coroutines
        compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-jdk9:1.8.1") // Kotlin Coroutines
//        compileOnly("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.22.0")
//        compileOnly("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.22.0")
        compileOnly("org.projectlombok:lombok:${rootProject.properties["lib.lombok.version"]}") // Lombok
        annotationProcessor("org.projectlombok:lombok:${rootProject.properties["lib.lombok.version"]}") // Lombok

        // 数据库
        compileOnly("com.j256.ormlite:ormlite-core:${rootProject.properties["lib.ormlite.version"]}") // ORMLite
        compileOnly("com.j256.ormlite:ormlite-jdbc:${rootProject.properties["lib.ormlite.version"]}") // ORMLite
        compileOnly("com.zaxxer:HikariCP:${rootProject.properties["lib.hikaricp.version"]}") { exclude("org.slf4j", "*") } // HikariCP
        compileOnly("io.lettuce:lettuce-core:${rootProject.properties["lib.lettuce.version"]}") { exclude("io.netty", "*") } // Lettuce

        // 依赖库
        implementation("cn.chengzhiya:MHDF-Scheduler:${rootProject.properties["lib.mhdf.scheduler.version"]}") // Scheduler
        compileOnly("com.saicone.rtag:rtag:${rootProject.properties["lib.rtag.version"]}") // RTag
        compileOnly("com.saicone.rtag:rtag-item:${rootProject.properties["lib.rtag.version"]}") // RTag
    }
}


// 任务配置
tasks {
    clean {
        delete("$rootDir/target")
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        dependsOn(":nms:v1_21_R1:reobfJar")
        dependsOn(":nms:v1_21_R2:reobfJar")
        dependsOn(":nms:v1_21_R3:reobfJar")
        dependsOn(":nms:v1_21_R4:reobfJar")
        dependsOn(":nms:v1_21_R5:reobfJar")
        mergeServiceFiles()

        manifest.attributes("paperweight-mappings-namespace" to "mojang")

        archiveFileName.set("${project.name}-${project.version}.jar")
        destinationDirectory.set(file("$rootDir/target"))
    }

    runServer {
        dependsOn(shadowJar)
        dependsOn(jar)
        minecraftVersion("1.21.7")

        // 在运行服务器之前执行部分插件依赖复制
//        doFirst {
//            copy {
//                from("$rootDir/compatibility/libs")
//                into("$rootDir/run/plugins")
//                duplicatesStrategy = DuplicatesStrategy.INCLUDE
//            }
//        }

        downloadPlugins {
            hangar("PlaceholderAPI", "2.11.6")
        }
    }

}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-Ddisable.watchdog=true")
    jvmArgs("-Xlog:redefine+class*=info")
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}