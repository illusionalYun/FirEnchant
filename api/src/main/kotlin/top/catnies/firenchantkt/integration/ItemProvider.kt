package top.catnies.firenchantkt.integration

import org.bukkit.inventory.ItemStack

interface ItemProvider {
    val enabled: Boolean
    fun getItemById(id: String): ItemStack?
    fun getIdByItem(item: ItemStack): String?
    override fun toString(): String
}