package top.catnies.firenchantkt.condition.impl.list

import top.catnies.firenchantkt.args.ArgumentKey
import top.catnies.firenchantkt.condition.AbstractCondition

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