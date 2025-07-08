package top.catnies.firenchantkt.api.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import top.catnies.firenchantkt.engine.ConditionRegistry

/**
 * 当插件启动时, 物品条件时触发, 你可以监听这个事件注册新的条件.
 */
class ConditionRegisterEvent(
    val register: ConditionRegistry
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
