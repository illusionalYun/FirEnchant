package top.catnies.firenchantkt.item.anvil

import com.destroystokyo.paper.event.inventory.PrepareResultEvent
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.config.AnvilConfig
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.item.AnvilApplicable


// 监听原版的附魔书应用事件, 然后根据配置取消.
class VanillaEnchantedBook: AnvilApplicable {

    companion object {
        val config = AnvilConfig.instance
    }

    override fun matches(itemStack: ItemStack): Boolean {
        // 未开启功能
        if (!config.isDenyVanillaEnchantedBook) return false
        // 无效物品
        if (itemStack.type != Material.ENCHANTED_BOOK) return false
        return true
    }

    override fun onPrepare(
        event: PrepareAnvilEvent,
        context: AnvilContext
    ) {}

    override fun onComplete(
        event: PrepareResultEvent,
        context: AnvilContext
    ) {
        // 忽略创造模式
        if (context.viewer.gameMode == GameMode.CREATIVE) return
        // 取消结果
        event.result = null
    }
}