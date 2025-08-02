package top.catnies.firenchantkt.item.enchantingtable

import org.bukkit.enchantments.Enchantment

/**
 * 记录可在附魔台使用的物品数据类
 */
data class OriginalBookData(
    val hookedPlugin: String,
    val hookedID: String,
    val enchantable: Int,
    val enchantmentList: Set<Enchantment>
)