package top.catnies.firenchantkt.engine.condition.list

import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

class ListContainsImpl(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["list"])
    private lateinit var list: List<String>

    @ArgumentKey(["v", "value"])
    private lateinit var value: String

    override fun getType() = "list_contains"
    override fun require() = true
    override fun check() = list.contains(value)
}