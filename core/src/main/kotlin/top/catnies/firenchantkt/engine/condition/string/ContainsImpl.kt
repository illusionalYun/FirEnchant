package top.catnies.firenchantkt.engine.condition.string

import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

class ContainsImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["s", "string"])
    private lateinit var string: String

    @ArgumentKey(["v", "value"])
    private lateinit var value: String

    override fun getType() = "contains"
    override fun require() = true
    override fun check() = string.contains(value)
}