package top.catnies.firenchantkt.api.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import top.catnies.firenchantkt.engine.ActionRegistry

class ActionRegisterEvent (
    val register: ActionRegistry
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