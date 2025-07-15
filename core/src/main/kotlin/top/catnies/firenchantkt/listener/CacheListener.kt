package top.catnies.firenchantkt.listener

import kotlinx.coroutines.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.database.FirConnectionManager

class CacheListener: Listener {

    val plugin = FirEnchantPlugin.instance
    val enchantLogData = FirConnectionManager.getInstance().enchantLogData

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        plugin.launch {
            val logCache = enchantLogData.getByPlayerRecent(event.player.uniqueId, 20)
        }
    }
}