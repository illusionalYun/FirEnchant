package top.catnies.firenchantkt.api.event

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.view.AnvilView
import top.catnies.firenchantkt.enchantment.EnchantmentSetting

/**
 * 当两本附魔书放入铁砧, 点击结果合并时触发.
 * 这个事件如果取消了, 那么 `InventoryClickEvent` 也会取消.
 * 铁砧中取消此事件会导致客户端不同步, 例如经验显示减少了但是实际上没有减少, 铁砧界面不显示经验消耗, 均为正常现象.
 * 故在取消此事件后, 建议额外操作 `event.view.setItem(2, ItemStack.of(Material.AIR))`
 */
class EnchantedBookMergeEvent(
    player: Player,
    val event: InventoryClickEvent,
    val anvilView: AnvilView,
    val firstSetting: EnchantmentSetting,
    val secondSetting: EnchantmentSetting,
    val resultItem: ItemStack?
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