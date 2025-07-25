package top.catnies.firenchantkt.item.repairtable

import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.database.entity.ItemRepairTable

/**
 * 时间缩减卡, 可以光标持物品点击正在修复中的物品, 减少修复花费的时间;
 */
interface ReductionCard {

    fun matches(item: ItemStack): Boolean {
        return false
    }

    fun onUse(itemRepairTable: ItemRepairTable) {
        return
    }
}