package top.catnies.firenchantkt.api.event.enchantingtable

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.enchantment.EnchantmentSetting

/**
 * 当玩家在附魔台里, 对一件可附魔物品进行附魔时触发
 */
class EnchantItemEvent(
    player: Player,
    val inputItem: ItemStack,
    var setting: EnchantmentSetting,
    val lineIndex: Int,
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