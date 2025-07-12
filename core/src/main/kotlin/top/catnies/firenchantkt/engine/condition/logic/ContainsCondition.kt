package top.catnies.firenchantkt.engine.condition.logic

import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition


// TODO Papi解析?
class ContainsCondition(
    args: Map<String, Any>,
) : AbstractCondition(args) {

    @ArgumentKey(["s", "string"])
    private lateinit var string: String

    @ArgumentKey(["v", "value"])
    private lateinit var value: String

    override fun check() = string.contains(value)
}