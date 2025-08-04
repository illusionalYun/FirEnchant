package top.catnies.firenchantkt.lazyinit

import com.nexomc.nexo.api.events.NexoItemsLoadedEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import top.catnies.firenchantkt.FirEnchant

class NexoLoadListener(
    val plugin: FirEnchant
): Listener {

    @EventHandler
    fun onNexoItemsLoad(event: NexoItemsLoadedEvent){
        if (plugin.isInitializedRegistry) return
        plugin.initRegistry()
    }

}