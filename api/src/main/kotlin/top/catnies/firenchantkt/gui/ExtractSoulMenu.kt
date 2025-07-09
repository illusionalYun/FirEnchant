package top.catnies.firenchantkt.gui

import org.bukkit.entity.Player

interface ExtractSoulMenu {

    var title: String

    var structure: Array<String>

    fun openMenu(player: Player, data: Map<String, Any>)

}