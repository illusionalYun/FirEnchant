package top.catnies.firenchantkt.item.enchantingtable

import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.config.AnvilConfig
import top.catnies.firenchantkt.integration.ItemProvider


class FirRenewalBook: RenewalBook {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = plugin.logger
        val config = AnvilConfig.instance
    }

    var isEnabled: Boolean = false
    var itemProvider: ItemProvider? = null
    var itemID: String? = null

    init {
        load()
    }

    // 检查配置合法性
    override fun load() {

    }

    override fun reload() = load()

    override fun matches(itemStack: ItemStack): Boolean {
        if (!isEnabled) return false
        return itemProvider!!.getIdByItem(itemStack) == itemID
    }
}