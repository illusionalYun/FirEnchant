package top.catnies.firenchantkt.api.event.anvilapplicable

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.view.AnvilView
import top.catnies.firenchantkt.enchantment.EnchantmentSetting

class EnchantedBookUseEvent(
    player: Player,
    val event: InventoryClickEvent,
    val anvilView: AnvilView,
    val firstItem: ItemStack,
    val secondSetting: EnchantmentSetting,
    var resultItem: ItemStack,
    var isSuccess: Boolean
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
        return AnvilApplicableItemRegisterEvent.Companion.HANDLER_LIST
    }

    override fun isCancelled(): Boolean {
        return isCancelled
    }

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }
}