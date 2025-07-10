package top.catnies.firenchantkt.engine.condition.logic

import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition
import top.catnies.firenchantkt.engine.EngineHelper.checkCondition

class OrImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["user"])
    private lateinit var user: CommandSender

    @ArgumentKey(["conditions", "condition"])
    private lateinit var conditions: List<ConfigurationSection>

    override fun getType() = "||"
    override fun require() = true
    override fun check() = conditions.any { user.checkCondition(it) }
}