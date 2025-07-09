package top.catnies.firenchantkt

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import top.catnies.firenchantkt.command.CommandManager
import top.catnies.firenchantkt.config.ConfigManager
import top.catnies.firenchantkt.database.impl.FirEnchantDatabaseManager
import top.catnies.firenchantkt.database.impl.FirPlayerEnchantLogDataManager
import top.catnies.firenchantkt.enchantment.FirEnchantmentManager
import top.catnies.firenchantkt.integration.FirItemProviderRegistry
import top.catnies.firenchantkt.integration.IntegrationManager
import top.catnies.firenchantkt.integration.NMSHandlerHolder
import top.catnies.firenchantkt.item.FirAnvilItemRegistry
import top.catnies.firenchantkt.item.FirEnchantingTableRegistry
import top.catnies.firenchantkt.language.MessageConstants.PLUGIN_COMPATIBILITY_HOOK_SUCCESS
import top.catnies.firenchantkt.language.TranslationManager
import top.catnies.firenchantkt.lazyinit.CraftEngineLoadListener
import top.catnies.firenchantkt.lazyinit.ItemsAdderLoadListener
import top.catnies.firenchantkt.lazyinit.NexoLoadListener
import top.catnies.firenchantkt.lazyinit.OraxenLoadListener
import top.catnies.firenchantkt.listener.ListenerManger
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent
import kotlin.properties.Delegates

class FirEnchantPlugin: JavaPlugin(), FirEnchant {

    companion object {
        @JvmStatic
        var instance by Delegates.notNull<FirEnchantPlugin>()
    }

    override var isInitializedRegistry = false

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        NMSHandlerHolder.instance // NMS持有者

        ConfigManager.instance // 配置文件管理器
        TranslationManager.instance // 语言管理器
        CommandManager.instance // 命令管理器
        ListenerManger.instance // 事件监听管理器
        FirEnchantDatabaseManager.getInstance() // 数据库控制器
        FirPlayerEnchantLogDataManager.getInstance() // 玩家附魔日志数据控制器
        registerLateInitListener()

        logger.info("FirEnchant Plugin Enabled!")
    }

    // 插件重载
    fun reload() {
        ConfigManager.instance.reload() // 配置文件管理器
        TranslationManager.instance.reload() // 语言管理器
        FirEnchantmentManager.instance.reload() // 系统魔咒管理器

        IntegrationManager.instance.reload() // 关联插件集成管理器

        FirAnvilItemRegistry.instance.reload() // 铁砧物品注册表
        FirEnchantingTableRegistry.instance.reload() // 附魔台物品注册表
    }

    // 延迟初始化注册表实现
    private fun registerLateInitListener() {
        when {
            // CraftEngine
            Bukkit.getPluginManager().getPlugin("CraftEngine") != null -> {
                Bukkit.getPluginManager().registerEvents(CraftEngineLoadListener(this), this)
                Bukkit.getConsoleSender().sendTranslatableComponent(PLUGIN_COMPATIBILITY_HOOK_SUCCESS, "CraftEngine")
            }
            // Nexo
            Bukkit.getPluginManager().getPlugin("Nexo") != null -> {
                Bukkit.getPluginManager().registerEvents(NexoLoadListener(this), this)
                Bukkit.getConsoleSender().sendTranslatableComponent(PLUGIN_COMPATIBILITY_HOOK_SUCCESS, "Nexo")
            }
            // Oraxen
            Bukkit.getPluginManager().getPlugin("Oraxen") != null -> {
                Bukkit.getPluginManager().registerEvents(OraxenLoadListener(this), this)
                Bukkit.getConsoleSender().sendTranslatableComponent(PLUGIN_COMPATIBILITY_HOOK_SUCCESS, "Oraxen")
            }
            // ItemsAdder
            Bukkit.getPluginManager().getPlugin("ItemsAdder") != null -> {
                Bukkit.getPluginManager().registerEvents(ItemsAdderLoadListener(this), this)
                Bukkit.getConsoleSender().sendTranslatableComponent(PLUGIN_COMPATIBILITY_HOOK_SUCCESS, "ItemsAdder")
            }
            else -> {
                initRegistry()
            }
        }
    }

    // 初始化注册表
    override fun initRegistry() {
        if (isInitializedRegistry) {
            logger.warning("警告, 插件试图重复初始化注册表, 不应发生这样的问题, 请联系开发者反馈.")
            return
        }
        isInitializedRegistry = true
        IntegrationManager.instance // 关联插件集成管理器

        FirItemProviderRegistry.instance // 物品集成注册表
        FirEnchantmentManager.instance // 系统魔咒管理器
        FirAnvilItemRegistry.instance // 铁砧物品注册表
        FirEnchantingTableRegistry.instance // 附魔台物品注册表
    }
}