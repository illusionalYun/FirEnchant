package top.catnies.firenchantkt.api.event.repairtable

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.database.entity.ItemRepairTable
import top.catnies.firenchantkt.item.repairtable.ReductionType

/**
 * 当玩家尝试使用时间减少卡时触发
 */
class ReductionCardUseEvent(
    player: Player,
    val event: InventoryClickEvent,
    val cursorCard: ItemStack,
    val itemRepairTable: ItemRepairTable,
    val reductionType: ReductionType,
    var value: Double
): PlayerEvent(player), Cancellable {

    companion object {
        val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLER_LIST
        }
    }

    private var isCancelled = false

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    override fun isCancelled(): Boolean {
        return isCancelled
    }

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }
}