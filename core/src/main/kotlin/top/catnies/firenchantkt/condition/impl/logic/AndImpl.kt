package top.catnies.firenchantkt.condition.impl.logic

import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import top.catnies.firenchantkt.args.ArgumentKey
import top.catnies.firenchantkt.condition.AbstractCondition
import top.catnies.firenchantkt.condition.ConditionManager

class AndImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["user"])
    private lateinit var user: CommandSender

    @ArgumentKey(["conditions", "condition"])
    private lateinit var conditions: List<ConfigurationSection>

    override fun getType(): String {
        return "&&"
    }

    override fun require(): Boolean {
        return true
    }

    override fun check(): Boolean {
        return ConditionManager.instance.check(user, conditions)
    }

}