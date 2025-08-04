package top.catnies.firenchantkt.item

import org.bukkit.inventory.ItemStack

/**
 * 可在修复桌中使用的物品注册表.
 */
interface RepairTableItemRegistry {

    // 注册一个物品
    fun registerItem(item: RepairTableApplicable): Boolean

    // 注销一个物品
    fun unregisterItem(item: RepairTableApplicable): Boolean

    // 获取一个物品
    fun <T : RepairTableApplicable> getItem(applicableClass: Class<T>): T?

    // 获取所有的物品
    fun getItems(): List<RepairTableApplicable>

    // 获取物品对应的物品类
    fun findApplicableItem(itemStack: ItemStack): RepairTableApplicable?

}