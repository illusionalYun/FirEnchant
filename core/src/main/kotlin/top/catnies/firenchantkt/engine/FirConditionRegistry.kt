package top.catnies.firenchantkt.engine

import org.bukkit.Bukkit
import top.catnies.firenchantkt.api.ServiceContainer
import top.catnies.firenchantkt.api.event.ConditionRegisterEvent
import top.catnies.firenchantkt.engine.condition.Condition
import top.catnies.firenchantkt.engine.condition.PermissionImpl
import top.catnies.firenchantkt.engine.condition.list.ListContainsImpl
import top.catnies.firenchantkt.engine.condition.logic.AndImpl
import top.catnies.firenchantkt.engine.condition.logic.IfImpl
import top.catnies.firenchantkt.engine.condition.logic.OrImpl
import top.catnies.firenchantkt.engine.condition.math.GreaterThanImpl
import top.catnies.firenchantkt.engine.condition.math.GreaterThanOrEqualImpl
import top.catnies.firenchantkt.engine.condition.math.LessThenImpl
import top.catnies.firenchantkt.engine.condition.math.LessThenOrEqualImpl
import top.catnies.firenchantkt.engine.condition.string.ContainsImpl
import top.catnies.firenchantkt.engine.condition.string.EqualsIgnoreCaseImpl
import top.catnies.firenchantkt.engine.condition.string.EqualsImpl

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
        Bukkit.getPluginManager().callEvent(ConditionRegisterEvent(this))
    }

    override fun registerCondition(name: String, condition: Class<out Condition>) {
        registry.putIfAbsent(name, condition)
    }

    override fun unregisterCondition(name: String): Boolean {
        return registry.remove(name) != null
    }

    override fun getCondition(name: String): Class<out Condition>? {
        return registry[name]
    }
}
