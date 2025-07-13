package top.catnies.firenchantkt.api.event.extractsoul

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.api.event.anvil.AnvilApplicableItemRegisterEvent

/**
 * 当玩家在使用魔咒之魂提取时, 点击确认兑换触发
 */
class ExtractSoulEvent(
    player: Player,
    var removedEnchantedBooks: List<ItemStack?>,
    var resultItems: List<ItemStack>
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