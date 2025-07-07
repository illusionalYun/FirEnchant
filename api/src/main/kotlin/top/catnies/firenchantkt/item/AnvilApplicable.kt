package top.catnies.firenchantkt.item

import com.destroystokyo.paper.event.inventory.PrepareResultEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.context.AnvilContext


interface AnvilApplicable {

    // 创建处理器时执行的操作, 例如绑定检查物品是否存在.
    fun load() {
        return
    }

    // 重载插件时处理器执行的操作, 例如清除更新缓存等.
    fun reload() {
        return
    }

    // 检查给定的 ItemStack 是否匹配这个处理器.
    fun matches(itemStack: ItemStack): Boolean {
        return false
    }

    // 当物品放入铁砧时触发.
    fun onPrepare(event: PrepareAnvilEvent, context: AnvilContext) {
        return
    }

    // 当物品在铁砧中展示结果时触发
    fun onPrepareResult(event: PrepareResultEvent, context: AnvilContext) {
        return
    }

    // 当物品在的使用槽, 并且点击铁砧的结果槽试图取出物品时触发
    fun onCost(event: InventoryClickEvent, context: AnvilContext) {
        return
    }

}