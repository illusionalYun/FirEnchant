package top.catnies.firenchantkt.item.enchantingtable

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.api.FirEnchantAPI
import top.catnies.firenchantkt.config.EnchantingTableConfig
import top.catnies.firenchantkt.context.EnchantingTableContext
import top.catnies.firenchantkt.engine.ConfigActionTemplate
import top.catnies.firenchantkt.engine.RunSource
import top.catnies.firenchantkt.integration.ItemProvider
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir
import top.catnies.firenchantkt.util.YamlUtils


class FirRenewalBook: RenewalBook {

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
        isEnabled = config.RENEWAL_BOOK_ENABLE
        if (isEnabled) {
            actions = config.RENEWAL_BOOK_ACTIONS
            val buildItem = YamlUtils.tryBuildItem(
                config.RENEWAL_BOOK_ITEM_PROVIDER,
                config.RENEWAL_BOOK_ITEM_ID,
                config.fileName,
                "renewal-book"
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
        val itemProvider = FirEnchantAPI.itemProviderRegistry().getItemProvider(config.RENEWAL_BOOK_ITEM_PROVIDER!!)
        return itemProvider!!.getIdByItem(itemStack) == config.RENEWAL_BOOK_ITEM_ID
    }

    override fun onPostInput(itemStack: ItemStack, context: EnchantingTableContext) {
        val player = context.player
        context.menu.clearInputInventory()
        player.enchantmentSeed = (1..Int.MAX_VALUE).random()

        val args = mutableMapOf<String, Any?>()
        args["checkSource"] = RunSource.MENU_CLICK
        args["player"] = player
        actions.forEach { action ->
            action.executeIfAllowed(args)
        }
    }
}