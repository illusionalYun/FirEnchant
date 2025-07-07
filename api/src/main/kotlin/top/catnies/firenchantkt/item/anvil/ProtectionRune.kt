package top.catnies.firenchantkt.item.anvil

import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull
import top.catnies.firenchantkt.item.AnvilApplicable

interface ProtectionRune: AnvilApplicable {

    /**
     * 检查物品上是否存在保护符文.
     * @param item 物品
     */
    @NotNull
    fun hasProtectionRune(item: ItemStack): Boolean


    /**
     * 移除物品上的保护符文.
     * @param item 物品
     */
    fun removeProtectionRune(item: ItemStack)


    /**
     * 为物品添加保护符文.
     */
    fun addProtectionRune(item: ItemStack)

}