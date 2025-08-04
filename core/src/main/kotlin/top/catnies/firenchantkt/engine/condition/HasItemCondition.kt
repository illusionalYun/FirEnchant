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
        var acc = 0
        player?.inventory?.forEach { itemStack ->
            if (itemStack == null) return@forEach
            if (itemProvider.getIdByItem(itemStack).equals(hookedID, true)) {
                acc += itemStack.amount
                if (acc >= count) return true
            }
        }
        return false
    }

}