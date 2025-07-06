plugins {
    id("io.papermc.paperweight.userdev") // PAPER Weight
}

dependencies {
    implementation(project(":api"))
    paperweight.paperDevBundle("1.21.7-R0.1-SNAPSHOT")

    // 物品库
    compileOnly("com.nexomc:nexo:1.1.0") // Nexo
    compileOnly("io.th0rgal:oraxen:1.181.0") // Oraxen
    compileOnly("io.lumine:Mythic-Dist:5.6.1") // MythicMobs
    compileOnly("dev.lone:api-itemsadder:4.0.10") // ItemsAdder
    compileOnly("net.momirealms:craft-engine-core:0.0.57") // CraftEngine
    compileOnly("net.momirealms:craft-engine-bukkit:0.0.57") // CraftEngine

    // 拓展功能
    compileOnly("me.clip:placeholderapi:2.11.6") // PlaceholderAPI
    compileOnly(files("libs/EnchantmentSlots-4.4.6.jar")) // EnchantmentSlots
    compileOnly("dev.aurelium:auraskills-api-bukkit:2.2.4") // AuraSkills
    compileOnly("net.momirealms:custom-crops:3.6.29") // CustomCrops
    compileOnly("net.momirealms:custom-fishing:2.3.7") // CustomFishing
}