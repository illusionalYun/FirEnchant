dependencies {
    // 项目依赖
    implementation(project(":api"))
    compileOnly("me.clip:placeholderapi:2.11.6") // PlaceholderAPI
    compileOnly(files("libs/EnchantmentSlots-4.4.6.jar")) // EnchantmentSlots

    // 物品库
    compileOnly("com.nexomc:nexo:1.1.0") // Nexo
    compileOnly("io.th0rgal:oraxen:1.181.0") // Oraxen
    compileOnly("io.lumine:Mythic-Dist:5.6.1") // MythicMobs
    compileOnly("dev.lone:api-itemsadder:4.0.10") // ItemsAdder
    compileOnly("net.momirealms:craft-engine-core:0.0.57") // CraftEngine
    compileOnly("net.momirealms:craft-engine-bukkit:0.0.57") // CraftEngine

    // 拓展功能
    compileOnly("dev.aurelium:auraskills-api-bukkit:2.3.5") // AuraSkills
    compileOnly("net.momirealms:custom-crops:3.6.29") // CustomCrops
    compileOnly("net.momirealms:custom-fishing:2.3.7") // CustomFishing
}