package top.catnies.firenchantkt.engine

import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import top.catnies.firenchantkt.api.FirEnchantAPI

object EngineHelper {

    val conditionRegistry = FirEnchantAPI.conditionRegistry()
    val actionRegistry = FirEnchantAPI.actionRegistry()

    // 检查条件
    fun CommandSender.checkCondition(type: String, args: MutableMap<String, Any>): Boolean {
        args["user"] = this

        // 如果type开头是! , 就返回 substring + true, 否则 原字符串 + false
        val (finalType, isNot) = if (type.startsWith('!')) (type.substring(1) to true) else (type to false)

        val conditionClass = conditionRegistry.getCondition(finalType) ?: throw IllegalArgumentException("试图检查不存在的条件: $type ,请检查配置文件后再试.")
        val condition = conditionClass.getDeclaredConstructor(args.javaClass).newInstance(args)

        return isNot != condition.check()
    }

    fun CommandSender.checkCondition(config: ConfigurationSection): Boolean {
        val type = config.getString("type")!!
        val args = config.getKeys(false).associateWith { config.get(it)!! }.toMutableMap()
        return checkCondition(type, args)
    }

    fun CommandSender.checkCondition(configList: List<ConfigurationSection>) = configList.all { checkCondition( it) }


    // 执行动作
    fun CommandSender.executeAction(type: String, args: MutableMap<String, Any>) {
        args["user"] = this

        val actionClass = actionRegistry.getAction(type) ?: throw IllegalArgumentException("试图执行不存在的动作: $type ,请检查配置文件后再试.")
        val action = actionClass.getDeclaredConstructor(args.javaClass).newInstance(args)

        action.execute()
    }

    // 传入节点执行动作, 检查条件. TODO
    fun CommandSender.executeActionWithConditions(config: ConfigurationSection) {

    }

}