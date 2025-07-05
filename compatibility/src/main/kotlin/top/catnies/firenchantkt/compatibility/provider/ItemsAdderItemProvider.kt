package top.catnies.firenchantkt.compatibility.provider

import dev.lone.itemsadder.api.CustomStack
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.integration.ItemProvider

class ItemsAdderItemProvider private constructor(
    override val enabled: Boolean
): ItemProvider {
    constructor(): this(Bukkit.getPluginManager().isPluginEnabled("ItemsAdder"))

    override fun getItemById(id: String): ItemStack? {
        return CustomStack.getInstance(id)?.itemStack
    }

    override fun getIdByItem(item: ItemStack): String? {
        return CustomStack.byItemStack(item)?.id
    }

    override fun toString(): String {
        return "ItemsAdder"
    }
}