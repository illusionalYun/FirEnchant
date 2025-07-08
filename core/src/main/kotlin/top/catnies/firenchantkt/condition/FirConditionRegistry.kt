package top.catnies.firenchantkt.condition

import top.catnies.firenchantkt.api.ServiceContainer
import top.catnies.firenchantkt.condition.impl.PermissionImpl
import top.catnies.firenchantkt.condition.impl.list.ListContainsImpl
import top.catnies.firenchantkt.condition.impl.logic.AndImpl
import top.catnies.firenchantkt.condition.impl.logic.IfImpl
import top.catnies.firenchantkt.condition.impl.logic.OrImpl
import top.catnies.firenchantkt.condition.impl.math.GreaterThanImpl
import top.catnies.firenchantkt.condition.impl.math.GreaterThanOrEqualImpl
import top.catnies.firenchantkt.condition.impl.math.LessThenImpl
import top.catnies.firenchantkt.condition.impl.math.LessThenOrEqualImpl
import top.catnies.firenchantkt.condition.impl.string.ContainsImpl
import top.catnies.firenchantkt.condition.impl.string.EqualsIgnoreCaseImpl
import top.catnies.firenchantkt.condition.impl.string.EqualsImpl

class FirConditionRegistry private constructor() : ConditionRegistry {
    companion object {
        val instance by lazy {
            FirConditionRegistry().also {
                it.load()
            }
        }
    }

    private val registry = mutableMapOf<String, Class<out Condition>>()

    fun load() {
        registerCondition("list_contains", ListContainsImpl::class.java)

        registerCondition("<", LessThenImpl::class.java)
        registerCondition("<=", LessThenOrEqualImpl::class.java)
        registerCondition("==", EqualsImpl::class.java)
        registerCondition(">=", GreaterThanOrEqualImpl::class.java)
        registerCondition(">", GreaterThanImpl::class.java)

        registerCondition("contains", ContainsImpl::class.java)
        registerCondition("equals_ignore_case", EqualsIgnoreCaseImpl::class.java)
        registerCondition("equals", EqualsImpl::class.java)

        registerCondition("&&", AndImpl::class.java)
        registerCondition("||", OrImpl::class.java)
        registerCondition("if", IfImpl::class.java)

        registerCondition("permission", PermissionImpl::class.java)

        ServiceContainer.register(ConditionRegistry::class.java, this)
    }

    override fun <T : Condition> registerCondition(name: String, condition: Class<T>) {
        registry.putIfAbsent(name, condition)
    }

    override fun unregisterCondition(name: String): Boolean {
        return registry.remove(name) != null
    }

    override fun <T : Condition> getCondition(name: String): Class<T> {
        return registry[name] as Class<T>
    }
}
