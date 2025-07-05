package top.catnies.firenchantkt.context

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class AnvilContext(
    val firstItem: ItemStack,
    val secondItem: ItemStack,
    val result: ItemStack?,
    val viewer: Player,
): Context