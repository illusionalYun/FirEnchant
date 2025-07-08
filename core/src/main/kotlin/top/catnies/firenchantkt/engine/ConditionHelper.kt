package top.catnies.firenchantkt.engine

import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import top.catnies.firenchantkt.api.FirEnchantAPI

object ConditionHelper {

    fun CommandSender.checkCondition(type: String, args: MutableMap<String, Any>): Boolean {
        args["user"] = this

        // 如果type开头是! , 就返回 substring + true, 否则 原字符串 + false
        val (finalType, isNot) = if (type.startsWith('!')) (type.substring(1) to true) else (type to false)

        val conditionClass = FirEnchantAPI.conditionRegistry().getCondition(finalType) ?: return false
        val condition = conditionClass.getDeclaredConstructor(args.javaClass).newInstance(args)

        return isNot != condition.check()
    }

    fun CommandSender.checkCondition(config: ConfigurationSection): Boolean {
        val type = config.getString("type")!!
        val args = config.getKeys(false).associateWith { config.get(it)!! }.toMutableMap()
        return checkCondition(type, args)
    }

    fun CommandSender.checkCondition(configList: List<ConfigurationSection>) = configList.all { checkCondition( it) }

}