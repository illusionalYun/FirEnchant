package top.catnies.firenchantkt.item

import org.bukkit.inventory.ItemStack

interface FixTableItemRegistry {

    // 注册一个修复桌物品
    fun registerItem(item: FixTableApplicable): Boolean

    // 注销一个修复桌物品
    fun unregisterItem(item: FixTableApplicable): Boolean

    // 获取一个修复桌物品
    fun <T :FixTableApplicable> getItem(applicableClass: Class<T>): T?

    // 获取所有的修复桌物品
    fun getItems(): List<FixTableApplicable>

    // 获取物品对应的修复桌物品类
    fun findApplicableItem(itemStack: ItemStack): FixTableApplicable?

}