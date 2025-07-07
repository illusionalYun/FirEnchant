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
    implementation(project(path = ":nms:v1_21_R1", configuration = "reobf"))
    implementation(project(path = ":nms:v1_21_R2", configuration = "reobf"))
    implementation(project(path = ":nms:v1_21_R3", configuration = "reobf"))
    implementation(project(path = ":nms:v1_21_R4", configuration = "reobf"))
    implementation(project(path = ":nms:v1_21_R5", configuration = "reobf"))
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
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // Kotlin STD
        compileOnly("org.projectlombok:lombok:${rootProject.properties["lib.lombok.version"]}") // Lombok
        annotationProcessor("org.projectlombok:lombok:${rootProject.properties["lib.lombok.version"]}") // Lombok

        // 依赖库
        implementation("cn.chengzhiya:MHDF-Scheduler:${rootProject.properties["lib.mhdf.scheduler.version"]}") // Scheduler
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

        relocate("cn.chengzhiya", "${project.group}.firenchantkt.libs.cn.chengzhiya")
        relocate("com.saicone", "${project.group}.firenchantkt.libs.com.saicone")
        relocate("xyz.xenondevs", "${project.group}.firenchantkt.libs.xyz.xenondevs")
        relocate("org.intellij", "${project.group}.firenchantkt.libs.org.intellij")
        relocate("org.jetbrains", "${project.group}.firenchantkt.libs.org.jetbrains")

        archiveFileName.set("${project.name}-${project.version}.jar")
        destinationDirectory.set(file("$rootDir/target"))
    }

    runServer {
        dependsOn(shadowJar)
        dependsOn(jar)
        minecraftVersion("1.21.7")

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