package top.catnies.firenchantkt.item.enchantingtable

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.api.FirEnchantAPI
import top.catnies.firenchantkt.api.event.enchantingtable.ReversalBookUseEvent
import top.catnies.firenchantkt.config.EnchantingTableConfig
import top.catnies.firenchantkt.context.EnchantingTableContext
import top.catnies.firenchantkt.database.FirCacheManager
import top.catnies.firenchantkt.engine.ConfigActionTemplate
import top.catnies.firenchantkt.integration.ItemProvider
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir
import top.catnies.firenchantkt.util.YamlUtils

class FirReversalBook: ReversalBook {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = plugin.logger
        val config = EnchantingTableConfig.instance
    }

    var isEnabled: Boolean = false
    var itemProvider: ItemProvider? = null
    var actions: List<ConfigActionTemplate> = emptyList()

    init {
        load()
    }

    // 检查配置合法性
    override fun load() {
        isEnabled = config.REVERSAL_BOOK_ENABLE
        if (isEnabled) {
            actions = config.REVERSAL_BOOK_ACTIONS
            val buildItem = YamlUtils.tryBuildItem(
                config.REVERSAL_BOOK_ITEM_PROVIDER,
                config.REVERSAL_BOOK_ITEM_ID,
                config.fileName,
                "reverse-book"
            )
            if (buildItem.nullOrAir()) {
                isEnabled = false
                return
            }
        }
    }

    override fun reload() = load()

    override fun matches(itemStack: ItemStack): Boolean {
        if (!isEnabled) return false
        val itemProvider = FirEnchantAPI.itemProviderRegistry().getItemProvider(FirRenewalBook.Companion.config.REVERSAL_BOOK_ITEM_PROVIDER!!)
        return itemProvider!!.getIdByItem(itemStack) == FirRenewalBook.Companion.config.REVERSAL_BOOK_ITEM_ID
    }

    override fun onPostInput(itemStack: ItemStack, context: EnchantingTableContext) {
        val player = context.player

        // 检查是否有上次附魔的种子
        val lastEnchantingSeed = FirCacheManager.getInstance().getLastEnchantingTableSeed(player.uniqueId)
        if (lastEnchantingSeed == -1) return

        // 触发事件
        val useEvent = ReversalBookUseEvent(player, lastEnchantingSeed)
        Bukkit.getPluginManager().callEvent(useEvent)
        if (useEvent.isCancelled) return

        // 回滚上次附魔的种子
        player.enchantmentSeed = useEvent.backSeed
        context.menu.clearInputInventory()

        actions.forEach { action ->
            action.executeIfAllowed(mapOf("player" to player))
        }
    }
}