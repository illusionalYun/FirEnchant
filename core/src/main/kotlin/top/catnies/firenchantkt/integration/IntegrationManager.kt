package top.catnies.firenchantkt.integration

import org.bukkit.Bukkit

// 插件集成管理器
class IntegrationManager private constructor() {

    companion object {
        val instance by lazy { IntegrationManager().also {
            it.load()
        }}
    }

    val isAuraSkillsEnabled: Boolean = Bukkit.getPluginManager().isPluginEnabled("AuraSkills")
    val isEnchantmentSlotsEnabled: Boolean = Bukkit.getPluginManager().isPluginEnabled("EnchantmentSlots")
    val isPlaceholderAPIEnabled: Boolean = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")

    private fun load() {
        // AuraSkills
        if (isEnchantmentSlotsEnabled) {

        }
        // EnchantmentSlots
        if (isEnchantmentSlotsEnabled) {

        }

    }

    fun reload() {

    }

}