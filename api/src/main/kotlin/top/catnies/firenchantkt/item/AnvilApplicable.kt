package top.catnies.firenchantkt.item

import com.destroystokyo.paper.event.inventory.PrepareResultEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.context.AnvilContext


interface AnvilApplicable {

    // 检查给定的 ItemStack 是否匹配这个处理器
    fun matches(itemStack: ItemStack): Boolean

    // 当物品放入铁砧时触发.
    fun onPrepare(event: PrepareAnvilEvent, context: AnvilContext)

    // 当物品在铁砧中被使用时触发.
    fun onComplete(event: PrepareResultEvent, context: AnvilContext)

}