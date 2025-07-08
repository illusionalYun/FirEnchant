package top.catnies.firenchantkt.engine

import top.catnies.firenchantkt.engine.condition.Condition

/**
 *
 */
interface ConditionRegistry {
    //
    fun <T: Condition> registerCondition(name: String, condition: Class<T>)
    fun unregisterCondition(name: String): Boolean

    fun <T: Condition> getCondition(name: String): Class<T>
}