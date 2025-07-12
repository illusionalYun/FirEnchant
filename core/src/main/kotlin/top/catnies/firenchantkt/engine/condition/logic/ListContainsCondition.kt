package top.catnies.firenchantkt.engine.condition.logic

import top.catnies.firenchantkt.engine.AbstractCondition
import top.catnies.firenchantkt.engine.ArgumentKey

class ListContainsCondition(
    args: Map<String, Any>,
) : AbstractCondition(args) {
    @ArgumentKey(["list"])
    private lateinit var list: List<String>

    @ArgumentKey(["v", "value"])
    private lateinit var value: String

    override fun check() = list.contains(value)
}