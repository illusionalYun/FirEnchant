package top.catnies.firenchantkt.engine

import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import top.catnies.firenchantkt.api.FirEnchantAPI

object ConditionHelper {

    fun CommandSender.checkCondition(type: String, args: MutableMap<String, Any>): Boolean {
        args["user"] = this
        var finalType = type

        var not = false
        if (type.startsWith('!')) {
            not = true
            finalType = type.substring(1)
        }

        val conditionClass = FirEnchantAPI.conditionRegistry().getCondition(finalType) ?: return false
        val condition = conditionClass.getDeclaredConstructor(args.javaClass).newInstance(args)

        return not != condition.check()
    }

    fun CommandSender.checkCondition(config: ConfigurationSection): Boolean {
        val type = config.getString("type")!!
        val args = config.getKeys(false).associateWith { config.get(it)!! }.toMutableMap()
        return checkCondition(type, args)
    }


    fun CommandSender.checkCondition(configList: List<ConfigurationSection>) = configList.all { checkCondition( it) }

}