package top.catnies.firenchantkt.listener

import com.destroystokyo.paper.event.inventory.PrepareResultEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareAnvilEvent
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.item.FirAnvilItemRegistry
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir

class RecipeListener: Listener {

    @EventHandler(ignoreCancelled = true)
    fun onPrepareAnvilEvent(event: PrepareAnvilEvent){
        val player = event.inventory.viewers.first() as? Player ?: return
        val firstItem = event.inventory.getItem(0) ?: return
        val secondItem = event.inventory.getItem(1) ?: return
        if (firstItem.nullOrAir() || secondItem.nullOrAir()) return

        // 找到物品对应的处理器
        val anvilApplicable = FirAnvilItemRegistry.instance.findApplicableItem(secondItem) ?: return
        // 构建上下文
        val context = AnvilContext(firstItem, secondItem, event.result, player)
        // 处理物品
        anvilApplicable.onPrepare(event, context)
    }


    @EventHandler(ignoreCancelled = true)
    fun onPrepareResultEvent(event: PrepareResultEvent) {
        val player = event.inventory.viewers.first() as? Player ?: return
        val firstItem = event.inventory.getItem(0) ?: return
        val secondItem = event.inventory.getItem(1) ?: return
        if (firstItem.nullOrAir() || secondItem.nullOrAir()) return

        // 找到物品对应的处理器
        val anvilApplicable = FirAnvilItemRegistry.instance.findApplicableItem(secondItem) ?: return
        // 构建上下文
        val context = AnvilContext(firstItem, secondItem, event.result, player)
        // 处理物品
        anvilApplicable.onPrepareResult(event, context)
    }

}