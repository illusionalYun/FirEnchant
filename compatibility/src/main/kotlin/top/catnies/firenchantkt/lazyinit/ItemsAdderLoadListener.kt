package top.catnies.firenchantkt.lazyinit

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import top.catnies.firenchantkt.FirEnchant

class ItemsAdderLoadListener(
    val plugin: FirEnchant
): Listener {

    @EventHandler
    fun onItemsAdderLoad(event: ItemsAdderLoadDataEvent){
        if (plugin.isInitializedRegistry) return
        plugin.initRegistry()
    }

}