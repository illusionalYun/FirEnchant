package top.catnies.firenchantkt.engine

import top.catnies.firenchantkt.engine.condition.Condition

/**
 * 条件系统注册表
 */
interface ConditionRegistry {

    // 注册新的条件
    fun <T: Condition> registerCondition(name: String, condition: Class<T>)

    // 注销条件
    fun unregisterCondition(name: String): Boolean

    // 获取条件类
    fun <T: Condition> getCondition(name: String): Class<T>

}