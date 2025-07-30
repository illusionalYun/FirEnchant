package top.catnies.firenchantkt.item.brokengear

import org.bukkit.inventory.ItemStack

interface BrokenGear {

    /**
     * 检查物品是否是一件破损的装备物品.
     */
    fun isBrokenGear(item: ItemStack?): Boolean


    /**
     * 将一件装备变成破损的形式.
     * 如果不支持变化, 最后会返回 NULL;
     */
    fun toBrokenGear(item: ItemStack?): ItemStack?


    /**
     * 修复一件破损的装备.
     * 如果不支持变化, 最后会返回 NULL;
     */
    fun repairBrokenGear(item: ItemStack?): ItemStack?

}