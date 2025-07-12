package top.catnies.firenchantkt.engine.condition.logic

import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

class IfCondition(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["v", "value"])
    private var value: Boolean = false

    override fun check() = value
}