package top.catnies.firenchantkt.api.event.repairtable

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import top.catnies.firenchantkt.database.entity.ItemRepairTable

/**
 * 当玩家点击正在修复的装备, 尝试取消修复时.
 */
class RepairingItemCancelEvent(
    player: Player,
    val itemRepairTable: ItemRepairTable
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