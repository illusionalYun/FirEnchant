package top.catnies.firenchantkt.item

import org.bukkit.inventory.ItemStack

interface EnchantingTableItemRegistry {
    
    // 注册一个附魔台物品
    fun registerItem(item: EnchantingTableApplicable): Boolean

    // 注销一个附魔台物品
    fun unregisterItem(item: EnchantingTableApplicable): Boolean

    // 获取一个附魔台物品
    fun <T :EnchantingTableApplicable> getItem(applicableClass: Class<T>): T?

    // 获取所有的附魔台物品
    fun getItems(): List<EnchantingTableApplicable>

    // 获取物品对应的附魔台物品类
    fun findApplicableItem(itemStack: ItemStack): EnchantingTableApplicable?

}