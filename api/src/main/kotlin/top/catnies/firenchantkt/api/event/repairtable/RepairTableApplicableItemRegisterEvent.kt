package top.catnies.firenchantkt.api.event.repairtable

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import top.catnies.firenchantkt.item.RepairTableItemRegistry

class RepairTableApplicableItemRegisterEvent(
    val register: RepairTableItemRegistry
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