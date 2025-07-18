package top.catnies.firenchantkt.gui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import top.catnies.firenchantkt.engine.ConfigActionTemplate
import top.catnies.firenchantkt.engine.RunSource
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.impl.AbstractItem

class MenuCustomItem(
    private val menuItemProvider: ItemProvider,
    val actionTemplates: List<ConfigActionTemplate>
): AbstractItem() {

    constructor(itemProvider: ItemProvider): this(itemProvider, emptyList())
    constructor(itemProvider: ItemProvider, actionTemplate: ConfigActionTemplate): this(itemProvider, listOf(actionTemplate))

    override fun getItemProvider(): ItemProvider = menuItemProvider

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val args = mutableMapOf<String, Any?>()
        args["checkSource"] = RunSource.MENUCLICK
        args["player"] = player
        args["clickType"] = clickType.name
        args["event"] = event
        actionTemplates.forEach {
            it.executeIfAllowed(args)
        }
    }

}