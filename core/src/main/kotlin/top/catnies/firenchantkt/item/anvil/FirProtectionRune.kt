package top.catnies.firenchantkt.item.anvil

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.context.AnvilContext

class FirProtectionRune: ProtectionRune {

    override fun hasProtectionRune(item: ItemStack): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeProtectionRune(item: ItemStack) {
        TODO("Not yet implemented")
    }

    override fun addProtectionRune(item: ItemStack) {
        TODO("Not yet implemented")
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

    override fun onCost(
        event: InventoryClickEvent,
        context: AnvilContext
    ) {
        super.onCost(event, context)
    }
}