dependencies {
    implementation(project(":api"))

    // 物品库
    compileOnly("com.nexomc:nexo:${rootProject.properties["lib.nexo.version"]}") // Nexo
    compileOnly("io.th0rgal:oraxen:${rootProject.properties["lib.oraxen.version"]}") // Oraxen
    compileOnly("io.lumine:Mythic-Dist:${rootProject.properties["lib.mythicmobs.version"]}") // MythicMobs
    compileOnly("dev.lone:api-itemsadder:${rootProject.properties["lib.itemsadder.version"]}") // ItemsAdder
    compileOnly("net.momirealms:craft-engine-core:${rootProject.properties["lib.craftengine.version"]}") // CraftEngine
    compileOnly("net.momirealms:craft-engine-bukkit:${rootProject.properties["lib.craftengine.version"]}") // CraftEngine

    // 拓展功能
    compileOnly("me.clip:placeholderapi:${rootProject.properties["lib.placeholderapi.version"]}") // PlaceholderAPI
    compileOnly(files("libs/EnchantmentSlots-4.4.6.jar")) // EnchantmentSlots
    compileOnly("dev.aurelium:auraskills-api-bukkit:${rootProject.properties["lib.auraskills.version"]}") // AuraSkills
    compileOnly("net.momirealms:custom-crops:${rootProject.properties["lib.customcrops.version"]}") // CustomCrops
    compileOnly("net.momirealms:custom-fishing:${rootProject.properties["lib.customfishing.version"]}") // CustomFishing
}