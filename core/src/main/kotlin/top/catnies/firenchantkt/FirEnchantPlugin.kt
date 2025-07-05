package top.catnies.firenchantkt

import org.bukkit.plugin.java.JavaPlugin
import top.catnies.firenchantkt.command.CommandManager
import top.catnies.firenchantkt.config.ConfigManager
import top.catnies.firenchantkt.enchantment.FirEnchantmentManager
import top.catnies.firenchantkt.integration.IntegrationManager
import top.catnies.firenchantkt.language.TranslationManager
import top.catnies.firenchantkt.listener.ListenerManger
import kotlin.properties.Delegates

class FirEnchantPlugin: JavaPlugin() {

    companion object {
        @JvmStatic
        var instance by Delegates.notNull<FirEnchantPlugin>()
    }

    override fun onEnable() {
        instance = this

        ConfigManager.instance // 配置文件管理器
        TranslationManager.instance // 语言管理器
        FirEnchantmentManager.instance // 系统魔咒管理器
        IntegrationManager.instance // 集成管理器
        CommandManager.instance // 命令管理器
        ListenerManger.instance // 事件监听管理器

        logger.info("FirEnchant enabled!")
    }

    fun reload() {
        ConfigManager.instance.reload() // 配置文件管理器
        TranslationManager.instance.reload() // 语言管理器
        FirEnchantmentManager.instance.reload() // 系统魔咒管理器
    }
}