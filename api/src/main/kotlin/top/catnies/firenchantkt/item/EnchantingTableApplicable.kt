package top.catnies.firenchantkt.item

import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.context.EnchantingTableContext

interface EnchantingTableApplicable {

    // 检查给定的 ItemStack 是否匹配这个处理器
    fun matches(itemStack: ItemStack): Boolean {
        return false
    }

    // 当物品准备放入附魔台时
    fun onPreInput(itemStack: ItemStack, context: EnchantingTableContext) {
        return
    }

    // 当物品放入附魔台后
    fun onPostInput(itemStack: ItemStack, context: EnchantingTableContext) {
        return
    }

}