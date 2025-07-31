package top.catnies.firenchantkt.api.event.enchantingtable

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import top.catnies.firenchantkt.api.event.anvil.AnvilApplicableItemRegisterEvent

class RenewalBookUseEvent(
    player: Player,
    var newSeed: Int // 新的附魔种子
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
        return AnvilApplicableItemRegisterEvent.Companion.HANDLER_LIST
    }

    override fun isCancelled(): Boolean {
        return isCancelled
    }

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

}