package top.catnies.firenchantkt.item.enchantingtable

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.api.FirEnchantAPI
import top.catnies.firenchantkt.api.event.enchantingtable.RenewalBookUseEvent
import top.catnies.firenchantkt.config.EnchantingTableConfig
import top.catnies.firenchantkt.context.EnchantingTableContext
import top.catnies.firenchantkt.engine.ConfigActionTemplate
import top.catnies.firenchantkt.integration.ItemProvider


class FirRenewalBook: RenewalBook {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = plugin.logger
        val config = EnchantingTableConfig.instance
    }

    var isEnabled: Boolean = false
    var itemProvider: ItemProvider? = null
    var itemID: String? = null
    var actions: List<ConfigActionTemplate> = emptyList()

    init {
        load()
    }

    // 检查配置合法性
    override fun load() {
        isEnabled = config.RENEWAL_BOOK_ENABLE
        if (isEnabled) {
            actions = config.RENEWAL_BOOK_ACTIONS
            itemProvider = FirEnchantAPI.itemProviderRegistry().getItemProvider(config.RENEWAL_BOOK_ITEM_PROVIDER!!)
            itemID = config.RENEWAL_BOOK_ITEM_ID
        }
    }

    override fun reload() = load()

    override fun matches(itemStack: ItemStack): Boolean {
        if (!isEnabled) return false
        return itemProvider!!.getIdByItem(itemStack) == itemID
    }

    override fun onPostInput(itemStack: ItemStack, context: EnchantingTableContext) {
        val player = context.player
        val newSeed = (1..Int.MAX_VALUE).random()

        // 广播事件
        val useEvent = RenewalBookUseEvent(player, newSeed)
        Bukkit.getPluginManager().callEvent(useEvent)
        if (useEvent.isCancelled) return

        // 执行
        context.menu.clearInputInventory()
        player.enchantmentSeed = useEvent.newSeed

        // 额外动作
        actions.forEach { action ->
            action.executeIfAllowed(mapOf("player" to player))
        }
    }
}