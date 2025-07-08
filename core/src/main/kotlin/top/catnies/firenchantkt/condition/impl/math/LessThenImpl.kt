package top.catnies.firenchantkt.condition.impl.math

import top.catnies.firenchantkt.args.ArgumentKey
import top.catnies.firenchantkt.condition.AbstractCondition

class LessThenImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["v1", "value1"])
    private var value1: Double = 0.0

    @ArgumentKey(["v2", "value2"])
    private var value2: Double = 0.0

    override fun getType(): String {
        return "<"
    }

    override fun require(): Boolean {
        return true
    }

    override fun check(): Boolean {
        return value1 < value2
    }

}