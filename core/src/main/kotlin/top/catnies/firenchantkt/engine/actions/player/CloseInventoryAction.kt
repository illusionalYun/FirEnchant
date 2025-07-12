package top.catnies.firenchantkt.engine.actions.player

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import top.catnies.firenchantkt.engine.AbstractAction
import top.catnies.firenchantkt.engine.ArgumentKey

class CloseInventoryAction(
    var args: Map<String, Any?>
): AbstractAction(args) {

    @ArgumentKey(["user"])
    private lateinit var user: CommandSender

    override fun getType() = "close_inventory"

    override fun execute() {
        (user as? Player)?.closeInventory()
    }
}