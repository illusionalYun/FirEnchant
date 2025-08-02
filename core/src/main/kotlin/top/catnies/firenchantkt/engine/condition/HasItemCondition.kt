package top.catnies.firenchantkt.engine.condition

import org.bukkit.entity.Player
import top.catnies.firenchantkt.engine.AbstractCondition
import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.integration.FirItemProviderRegistry

class HasItemCondition(
    args: Map<String, Any?>
) : AbstractCondition(args) {

    @ArgumentKey(["player"], autoInject = true)
    private var player: Player? = null

    @ArgumentKey(["hooked-plugin"])
    private lateinit var hookPlugin: String

    @ArgumentKey(["hooked-id", "hooked-item"])
    private lateinit var hookedID: String

    @ArgumentKey(["count"])
    private var count: Int = 0

    override fun check(): Boolean {
        val itemProvider = FirItemProviderRegistry.instance.getItemProvider(hookPlugin) ?: return false
        player?.inventory?.fold(0) { acc, itemStack ->
            val i = if (itemProvider.getIdByItem(itemStack) == hookedID) acc + itemStack.amount else acc
            if (i >= count) return true
            return@fold i
        }
        return false
    }

}