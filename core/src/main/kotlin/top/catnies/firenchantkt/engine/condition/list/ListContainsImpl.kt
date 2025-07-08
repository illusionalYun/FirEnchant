package top.catnies.firenchantkt.engine.condition.list

import top.catnies.firenchantkt.engine.args.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

class ListContainsImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["list"])
    private lateinit var list: List<String>

    @ArgumentKey(["v", "value"])
    private lateinit var value: String

    override fun getType(): String {
        return "list_contains"
    }

    override fun require(): Boolean {
        return true
    }

    override fun check(): Boolean {
        return list.contains(value)
    }

}