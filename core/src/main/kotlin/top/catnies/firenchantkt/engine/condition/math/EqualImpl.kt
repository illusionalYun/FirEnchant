package top.catnies.firenchantkt.engine.condition.math

import top.catnies.firenchantkt.engine.args.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

class EqualImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["v1", "value1"])
    private var value1: Double = 0.0

    @ArgumentKey(["v2", "value2"])
    private var value2: Double = 0.0

    override fun getType() = "=="
    override fun require() = true
    override fun check() = value1 == value2

}