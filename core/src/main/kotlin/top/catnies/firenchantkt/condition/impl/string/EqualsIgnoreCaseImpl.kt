package top.catnies.firenchantkt.condition.impl.string

import top.catnies.firenchantkt.args.ArgumentKey
import top.catnies.firenchantkt.condition.AbstractCondition

class EqualsIgnoreCaseImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["s", "string"])
    private lateinit var string: String

    @ArgumentKey(["v", "value"])
    private lateinit var value: String

    override fun getType(): String {
        return "equals_ignore_case"
    }

    override fun require(): Boolean {
        return true
    }

    override fun check(): Boolean {
        return string.equals(value, true)
    }

}