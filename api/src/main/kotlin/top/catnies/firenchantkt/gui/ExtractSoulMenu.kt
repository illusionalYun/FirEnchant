package top.catnies.firenchantkt.gui

import org.bukkit.entity.Player

interface ExtractSoulMenu {

    // 打开菜单
    fun openMenu(data: Map<String, Any>, async: Boolean = false)

}