package top.catnies.firenchantkt.item.fixtable

import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.item.FixTableApplicable

interface BrokenGear: FixTableApplicable {

    /**
     * 检查物品是否是一件破损的装备物品.
     */
    fun isBrokenGear(item: ItemStack): Boolean


    /**
     * 将一件装备变成破损的形式.
     */
    fun toBrokenGear(item: ItemStack): ItemStack


    /**
     * 修复一件破损的装备.
     */
    fun repairBrokenGear(item: ItemStack): ItemStack

}