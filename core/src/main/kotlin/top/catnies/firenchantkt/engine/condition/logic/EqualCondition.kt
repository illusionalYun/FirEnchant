package top.catnies.firenchantkt.engine.condition.logic

import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

// TODO Papi解析?
class EqualCondition(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["v1", "value1"])
    private var value1: Double = 0.0

    @ArgumentKey(["v2", "value2"])
    private var value2: Double = 0.0

    override fun check() = value1 == value2

}