package top.catnies.firenchantkt.lazyinit

import net.momirealms.craftengine.bukkit.api.event.CraftEngineReloadEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import top.catnies.firenchantkt.FirEnchant

class CraftEngineLoadListener(
    val plugin: FirEnchant
): Listener {

    @EventHandler
    fun onCraftEngineLoad(event: CraftEngineReloadEvent){
        plugin.initRegistry()
    }

}