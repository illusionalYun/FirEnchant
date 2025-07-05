package top.catnies.firenchantkt.util

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object ItemUtils {

    // 判断物品是否是 null 或 空气
    fun ItemStack?.nullOrAir(): Boolean {
        this ?: return true
        return this.type.isAir
    }

    // 检查物品是否能够应用某个魔咒兼容.
    fun ItemStack.isCompatibleWithEnchantment(other: Enchantment): Boolean {
        // 如果物品类型不支持附魔
        if (!other.canEnchantItem(this)) {
            return false
        }
        // 检查物品上的魔咒是否有冲突
        this.enchantments.forEach { (enchantment, level) ->
            if (!enchantment.conflictsWith(other)) return false
        }
        return true
    }

}