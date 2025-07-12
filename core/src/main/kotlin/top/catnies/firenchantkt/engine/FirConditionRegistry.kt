package top.catnies.firenchantkt.engine

import org.bukkit.Bukkit
import top.catnies.firenchantkt.api.ServiceContainer
import top.catnies.firenchantkt.api.event.ConditionRegisterEvent
import top.catnies.firenchantkt.engine.condition.player.PermissionCondition
import top.catnies.firenchantkt.engine.condition.logic.AndCondition
import top.catnies.firenchantkt.engine.condition.logic.EqualCondition
import top.catnies.firenchantkt.engine.condition.logic.GreaterThanCondition
import top.catnies.firenchantkt.engine.condition.logic.GreaterThanOrEqualCondition
import top.catnies.firenchantkt.engine.condition.logic.LessThenCondition
import top.catnies.firenchantkt.engine.condition.logic.LessThenOrEqualCondition
import top.catnies.firenchantkt.engine.condition.logic.OrCondition
import top.catnies.firenchantkt.engine.condition.logic.ContainsCondition
import top.catnies.firenchantkt.engine.condition.logic.EqualsIgnoreCaseCondition
import top.catnies.firenchantkt.engine.condition.logic.EqualsCondition

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
        registerCondition("<", LessThenCondition::class.java)
        registerCondition("<=", LessThenOrEqualCondition::class.java)
        registerCondition("==", EqualCondition::class.java)
        registerCondition(">=", GreaterThanOrEqualCondition::class.java)
        registerCondition(">", GreaterThanCondition::class.java)

        registerCondition("contains", ContainsCondition::class.java)
        registerCondition("equals_ignore_case", EqualsIgnoreCaseCondition::class.java)
        registerCondition("equals", EqualsCondition::class.java)

        registerCondition("&&", AndCondition::class.java)
        registerCondition("||", OrCondition::class.java)

        registerCondition("permission", PermissionCondition::class.java)

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
