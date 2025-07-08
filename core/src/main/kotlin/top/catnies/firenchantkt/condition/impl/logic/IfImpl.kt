package top.catnies.firenchantkt.condition.impl.logic

import top.catnies.firenchantkt.args.ArgumentKey
import top.catnies.firenchantkt.condition.AbstractCondition

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