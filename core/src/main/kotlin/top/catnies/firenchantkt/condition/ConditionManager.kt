package top.catnies.firenchantkt.condition

import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import top.catnies.firenchantkt.api.FirEnchantAPI

class ConditionManager {
    companion object {
        @JvmStatic
        val instance by lazy { ConditionManager() }
    }

    fun check(user: CommandSender, type: String, args: MutableMap<String, Any>): Boolean {
        args["user"] = user
        var finalType = type

        var not = false
        if (type.startsWith('!')) {
            not = true
            finalType = type.substring(1)
        }

        val conditionClass = FirEnchantAPI.conditionRegistry().getCondition<AbstractCondition>(finalType)
        val condition = conditionClass.getDeclaredConstructor(args.javaClass).newInstance(args)

        return not != condition.check()
    }

    fun check(user: CommandSender, config: ConfigurationSection): Boolean {
        val type = config.getString("type")!!
        val args = mutableMapOf<String, Any>()
        config.getKeys(false).forEach {
            args[it] = config.get(it)!!
        }

        return check(user, type, args)
    }

    fun check(user: CommandSender, configList: List<ConfigurationSection>): Boolean {
        configList.forEach {
            val result = check(user, it)
            if (!result) {
                return false
            }
        }

        return true
    }
}