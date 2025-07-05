package top.catnies.firenchantkt.integration

import org.bukkit.Bukkit
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.api.event.ItemProviderRegisterEvent
import top.catnies.firenchantkt.compatibility.provider.CraftEngineItemProvider
import top.catnies.firenchantkt.compatibility.provider.ItemsAdderItemProvider
import top.catnies.firenchantkt.compatibility.provider.MythicMobsItemProvider
import top.catnies.firenchantkt.compatibility.provider.NexoItemProvider
import top.catnies.firenchantkt.compatibility.provider.VanillaItemProvider

class IntegrationManager private constructor(): ItemProviderRegistry {

    var plugin = FirEnchantPlugin.instance
    var logger = FirEnchantPlugin.instance.logger

    companion object {
        @JvmStatic
        val instance by lazy { IntegrationManager().apply {
            logger.info("Loading integration...")
            load()
            logger.info("Integration loaded!")
        } }

        @JvmStatic
        private val itemProviderMap = mutableMapOf<String, ItemProvider>()
    }

    fun load() {
        // Item Hook
        registerItemProvider("Vanilla", VanillaItemProvider())
        registerItemProvider("ItemsAdder", ItemsAdderItemProvider())
        registerItemProvider("CraftEngine", CraftEngineItemProvider())
        registerItemProvider("MythicMobs", MythicMobsItemProvider())
        registerItemProvider("Nexo", NexoItemProvider())
        Bukkit.getPluginManager().callEvent(ItemProviderRegisterEvent(this))
    }

    // ItemProviders
    override fun registerItemProvider(plugin: String, provider: ItemProvider) = itemProviderMap.put(plugin.lowercase(), provider)
    override fun unregisterItemProvider(plugin: String) = itemProviderMap.remove(plugin)
    override fun getItemProviders() = itemProviderMap.values.filter { it.enabled }
    override fun getItemProvider(plugin: String): ItemProvider? {
        itemProviderMap[plugin.lowercase()]?.let { if (it.enabled) return it }
        return null
    }
}