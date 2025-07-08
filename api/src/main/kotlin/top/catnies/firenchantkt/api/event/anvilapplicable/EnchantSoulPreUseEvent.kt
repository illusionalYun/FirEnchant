package top.catnies.firenchantkt.api.event.anvilapplicable

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.enchantment.EnchantmentSetting

class EnchantSoulPreUseEvent(
    player: Player,
    val event: PrepareAnvilEvent,
    var useAmount: Int,
    var costExp: Int,
    var resultSetting: EnchantmentSetting,
    val firstItem: ItemStack
) : PlayerEvent(player), Cancellable {

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