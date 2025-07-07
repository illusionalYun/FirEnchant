package top.catnies.firenchantkt

import org.bukkit.plugin.java.JavaPlugin
import top.catnies.firenchantkt.command.CommandManager
import top.catnies.firenchantkt.config.ConfigManager
import top.catnies.firenchantkt.database.DatabaseManager
import top.catnies.firenchantkt.database.impl.FirEnchantDatabaseManager
import top.catnies.firenchantkt.database.impl.FirPlayerEnchantLogDataManager
import top.catnies.firenchantkt.enchantment.FirEnchantmentManager
import top.catnies.firenchantkt.integration.FirItemProviderRegistry
import top.catnies.firenchantkt.integration.IntegrationManager
import top.catnies.firenchantkt.item.FirAnvilItemRegistry
import top.catnies.firenchantkt.item.FirEnchantingTableRegistry
import top.catnies.firenchantkt.language.TranslationManager
import top.catnies.firenchantkt.listener.ListenerManger
import kotlin.properties.Delegates

class FirEnchantPlugin: JavaPlugin() {

    companion object {
        @JvmStatic
        var instance by Delegates.notNull<FirEnchantPlugin>()
    }

    lateinit var databaseManager: DatabaseManager


    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        ConfigManager.instance // 配置文件管理器
        TranslationManager.instance // 语言管理器
        FirEnchantmentManager.instance // 系统魔咒管理器
        CommandManager.instance // 命令管理器
        ListenerManger.instance // 事件监听管理器

        IntegrationManager.instance // 关联插件集成管理器

        FirItemProviderRegistry.instance // 物品集成注册表
        FirAnvilItemRegistry.instance // 铁砧物品注册表
        FirEnchantingTableRegistry.instance // 附魔台物品注册表

        // 数据库控制器
        databaseManager = FirEnchantDatabaseManager()
        databaseManager.connect()
        databaseManager.initTable()

        // 玩家附魔日志数据控制器
        FirPlayerEnchantLogDataManager.getInstance()

        logger.info("FirEnchant enabled!")
    }


    fun reload() {
        ConfigManager.instance.reload() // 配置文件管理器
        TranslationManager.instance.reload() // 语言管理器
        FirEnchantmentManager.instance.reload() // 系统魔咒管理器

        IntegrationManager.instance.reload() // 关联插件集成管理器

        FirAnvilItemRegistry.instance.reload() // 铁砧物品注册表
        FirEnchantingTableRegistry.instance.reload() // 附魔台物品注册表
    }
}