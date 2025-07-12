package top.catnies.firenchantkt.engine.condition.logic

import top.catnies.firenchantkt.engine.AbstractCondition
import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.ConfigConditionTemplate

class OrCondition(
    args: Map<String, Any>,
) : AbstractCondition(args) {

    @ArgumentKey(["conditions"])
    private lateinit var conditions: List<ConfigConditionTemplate>

    override fun check() = conditions.any { it.check(args) }

}