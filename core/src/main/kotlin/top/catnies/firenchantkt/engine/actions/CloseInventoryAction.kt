package top.catnies.firenchantkt.engine.actions

import org.bukkit.entity.Player
import top.catnies.firenchantkt.engine.AbstractAction
import top.catnies.firenchantkt.engine.ArgumentKey

class CloseInventoryAction(
    args: Map<String, Any?>
) : AbstractAction(args) {

    @ArgumentKey(["player"], autoInject = true)
    private var player: Player? = null

    override fun execute() {
        player?.closeInventory()
    }
}