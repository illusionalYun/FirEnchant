package top.catnies.firenchantkt.engine.condition.logic

import top.catnies.firenchantkt.engine.args.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

class IfImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["v", "value"])
    private var value: Boolean = false

    override fun getType(): String {
        return "if"
    }

    override fun require(): Boolean {
        return true
    }

    override fun check(): Boolean {
        return value
    }

}