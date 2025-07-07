package top.catnies.firenchantkt.api.event.anvilapplicable

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.event.player.PlayerEvent
import top.catnies.firenchantkt.enchantment.EnchantmentSetting

/**
 * 当两本附魔书放入铁砧, 试图合并时触发.
 * 事件如果取消了, 插件将不会处理它, 将会进行原版逻辑.
 */
class PreEnchantedBookMergeEvent(
    player: Player,
    val event: PrepareAnvilEvent,
    var costExp: Int,
    val firstSetting: EnchantmentSetting,
    val secondSetting: EnchantmentSetting,
    var resultSetting: EnchantmentSetting
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