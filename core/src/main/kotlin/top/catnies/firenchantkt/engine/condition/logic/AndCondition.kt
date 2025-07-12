package top.catnies.firenchantkt.engine.condition.logic

import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition
import top.catnies.firenchantkt.engine.EngineHelper.checkCondition

// TODO 递归解析?
class AndCondition(
    args: Map<String, Any>,
) : AbstractCondition(args) {

    @ArgumentKey(["user"], autoInject = true)
    private lateinit var user: CommandSender

    @ArgumentKey(["conditions", "condition"])
    private lateinit var conditions: List<ConfigurationSection>

    override fun check() = user.checkCondition(conditions)
}