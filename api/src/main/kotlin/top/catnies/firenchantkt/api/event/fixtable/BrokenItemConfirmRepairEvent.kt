package top.catnies.firenchantkt.api.event.fixtable

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import top.catnies.firenchantkt.database.entity.ItemRepairTable

/**
 * 当玩家点击确认按钮, 确认修复装备时.
 */
class BrokenItemConfirmRepairEvent(
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