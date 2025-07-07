package top.catnies.firenchantkt.api.event.anvilapplicable

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import top.catnies.firenchantkt.item.AnvilItemRegistry

/**
 * 当插件启动时, 物品注册时触发, 你可以监听这个事件注册新的物品.
 */
class AnvilApplicableItemRegisterEvent(
    val register: AnvilItemRegistry
): Event() {

    companion object {
        val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLER_LIST
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

}