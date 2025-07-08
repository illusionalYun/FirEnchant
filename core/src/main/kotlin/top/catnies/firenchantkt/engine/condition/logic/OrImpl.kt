package top.catnies.firenchantkt.engine.condition.logic

import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import top.catnies.firenchantkt.engine.args.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition
import top.catnies.firenchantkt.engine.ConditionManager

class OrImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["user"])
    private lateinit var user: CommandSender

    @ArgumentKey(["conditions", "condition"])
    private lateinit var conditions: List<ConfigurationSection>

    override fun getType(): String {
        return "||"
    }

    override fun require(): Boolean {
        return true
    }

    override fun check(): Boolean {
        conditions.forEach {
            val result = ConditionManager.instance.check(user, it)
            if (result) {
                return true
            }
        }

        return false
    }

}