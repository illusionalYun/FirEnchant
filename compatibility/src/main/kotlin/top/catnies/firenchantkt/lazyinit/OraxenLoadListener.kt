package top.catnies.firenchantkt.lazyinit

import io.th0rgal.oraxen.api.events.OraxenItemsLoadedEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import top.catnies.firenchantkt.FirEnchant

class OraxenLoadListener(
    val plugin: FirEnchant
): Listener {

    @EventHandler
    fun onOraxenItemsLoad(event: OraxenItemsLoadedEvent){
        if (plugin.isInitializedRegistry) return
        plugin.initRegistry()
    }

}