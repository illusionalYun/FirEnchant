package top.catnies.firenchantkt.condition.impl.string

import top.catnies.firenchantkt.args.ArgumentKey
import top.catnies.firenchantkt.condition.AbstractCondition

class EqualsImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["s", "string"])
    private lateinit var string: String

    @ArgumentKey(["v", "value"])
    private lateinit var value: String

    override fun getType(): String {
        return "equals"
    }

    override fun require(): Boolean {
        return true
    }

    override fun check(): Boolean {
        return string == value
    }

}