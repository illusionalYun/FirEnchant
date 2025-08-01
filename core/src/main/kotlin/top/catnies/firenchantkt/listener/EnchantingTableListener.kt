package top.catnies.firenchantkt.listener

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import top.catnies.firenchantkt.config.EnchantingTableConfig
import top.catnies.firenchantkt.gui.FirEnchantingTableMenu
import top.catnies.firenchantkt.integration.NMSHandlerHolder

class EnchantingTableListener : Listener {

    val config = EnchantingTableConfig.instance

    // 右键附魔台替换成打开插件附魔台;
    // 创造模式+shift右键打开原版附魔台;
    @EventHandler(ignoreCancelled = true)
    fun onEnchantingTableClick(event: PlayerInteractEvent) {
        if (!config.REPLACE_VANILLA_ENCHANTMENT_TABLE) return   // 检查功能是否打开
        if (event.action == Action.RIGHT_CLICK_BLOCK            // 右键点击方块
            && !event.player.isSneaking                         // 潜行不算
            && event.clickedBlock?.type == Material.ENCHANTING_TABLE)   // 点击的是附魔台方块
        {
            event.isCancelled = true
            val nmsHandler = NMSHandlerHolder.getNMSHandler()
            val bookShelves = nmsHandler.getEnchantmentTableBookShelf(event.clickedBlock!!.location)
            FirEnchantingTableMenu(event.player, bookShelves).openMenu(emptyMap(), true)
        }
    }

}