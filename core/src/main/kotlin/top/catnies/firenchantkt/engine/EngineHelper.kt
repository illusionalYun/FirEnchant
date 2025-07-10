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

        // 如果条件不存在，会发出警告，然后忽略它。
        val conditionClass = conditionRegistry.getCondition(finalType) ?: return true
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

        // 如果动作不存在，会发出警告，然后忽略它。
        val actionClass = actionRegistry.getAction(type) ?: return
        val action = actionClass.getDeclaredConstructor(args.javaClass).newInstance(args)

        action.execute()
    }

    // 传入节点执行动作, 检查条件. TODO
    fun CommandSender.executeActionWithConditions(config: ConfigurationSection) {
        val type = config.getString("type")!!
        val conditions = config.getConfigurationSection("conditions") ?: return executeAction(config.name, mutableMapOf())
        // 获取 configList: List<ConfigurationSection> ????
    }

}