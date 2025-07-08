package top.catnies.firenchantkt.engine.condition.string

import top.catnies.firenchantkt.engine.args.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

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