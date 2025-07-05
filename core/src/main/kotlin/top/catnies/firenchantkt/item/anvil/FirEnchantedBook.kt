package top.catnies.firenchantkt.item.anvil

import com.destroystokyo.paper.event.inventory.PrepareResultEvent
import com.saicone.rtag.RtagItem
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.enchantment.FirEnchantmentManager
import top.catnies.firenchantkt.item.AnvilApplicable

class FirEnchantedBook: AnvilApplicable {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = plugin.logger
    }

    // 抓取符合条件的物品
    override fun matches(itemStack: ItemStack): Boolean {
        val tag = RtagItem.of(itemStack)
        val enchantment: String = tag.get("FirEnchant", "Enchantment") ?: return false
        val level: Int = tag.get("FirEnchant", "Level") ?: return false
        val failure: Int = tag.get("FirEnchant", "Failure") ?: return false
        return FirEnchantmentManager.instance.hasEnchantment(enchantment)
    }

    override fun onPrepare(event: PrepareAnvilEvent, context: AnvilContext) {
        logger.warning { "FirEnchantBook onPrepare" }
        TODO("Not yet implemented")
    }


    override fun onComplete(event: PrepareResultEvent, context: AnvilContext) {
        TODO("Not yet implemented")
    }

}