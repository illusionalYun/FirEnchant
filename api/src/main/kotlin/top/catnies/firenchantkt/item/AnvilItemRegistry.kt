package top.catnies.firenchantkt.item

import org.bukkit.inventory.ItemStack

/**
 * 可在铁砧中使用的物品注册表.
 */
interface AnvilItemRegistry {

    // 注册一个铁砧物品
    fun registerItem(item: AnvilApplicable): Boolean

    // 注销一个铁砧物品
    fun unregisterItem(item: AnvilApplicable): Boolean

    // 获取所有的铁砧物品
    fun getItems(): List<AnvilApplicable>

    // 获取物品对应的铁砧物品类
    fun findApplicableItem(itemStack: ItemStack): AnvilApplicable?

}