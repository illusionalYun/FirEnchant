package top.catnies.firenchantkt.integration

import org.bukkit.Bukkit
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.compatibility.enchantmentslots.RegistryListener
import top.catnies.firenchantkt.language.MessageConstants.PLUGIN_COMPATIBILITY_HOOK_SUCCESS
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent

// 插件集成管理器
class IntegrationManager private constructor() {

    var plugin = FirEnchantPlugin.instance
    var logger = FirEnchantPlugin.instance.logger

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
            // 监听物品注册事件
            Bukkit.getPluginManager().registerEvents(RegistryListener(), plugin)
            sendPluginHookedMessage("EnchantmentSlots")
        }
        // PlaceholderAPI
        if (isPlaceholderAPIEnabled) {
            sendPluginHookedMessage("PlaceholderAPI")
        }
    }

    fun reload() {

    }

    // 发送插件关联启动消息
    private fun sendPluginHookedMessage(name: String) {
        Bukkit.getConsoleSender().sendTranslatableComponent(
            PLUGIN_COMPATIBILITY_HOOK_SUCCESS,
            name
        )
    }
}