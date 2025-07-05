package top.catnies.firenchantkt.listener

import com.destroystokyo.paper.event.inventory.PrepareResultEvent
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.AnvilInventory
import top.catnies.firenchantkt.config.AnvilConfig
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir

// 监听原版的附魔书应用事件, 然后根据配置取消.
class EnchantedBookApplyListener: Listener {

    companion object {
        val config = AnvilConfig.instance
    }

    @EventHandler
    fun onEnchantedBookApply(event: PrepareResultEvent) {
        if (!config.isDenyVanillaEnchantedBook) return
        if (event.inventory !is AnvilInventory) return
        if (event.viewers.first()?.gameMode == GameMode.CREATIVE) return

        val inv = event.inventory as AnvilInventory
        if (inv.firstItem.nullOrAir() || inv.secondItem.nullOrAir() || inv.secondItem!!.type != Material.ENCHANTED_BOOK) {
            return
        }

        event.result = null
    }

}