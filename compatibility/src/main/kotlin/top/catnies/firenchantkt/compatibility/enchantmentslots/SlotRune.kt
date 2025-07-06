package top.catnies.firenchantkt.compatibility.enchantmentslots

import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.item.AnvilApplicable

// 拓展符文
class SlotRune: AnvilApplicable {

    companion object {
        var itemProvider = null
        var itemID = null
    }

    override fun matches(itemStack: ItemStack): Boolean {
        return super.matches(itemStack)
    }

    override fun onPrepare(
        event: PrepareAnvilEvent,
        context: AnvilContext
    ) {
        super.onPrepare(event, context)
    }

}