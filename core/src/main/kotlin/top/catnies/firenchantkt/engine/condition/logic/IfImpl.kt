package top.catnies.firenchantkt.engine.condition.logic

import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

class IfImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["v", "value"])
    private var value: Boolean = false

    override fun getType() = "if"
    override fun require() = true
    override fun check() = value
}