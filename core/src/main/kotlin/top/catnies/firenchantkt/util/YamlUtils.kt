package top.catnies.firenchantkt.util

import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.yaml.snakeyaml.Yaml
import top.catnies.firenchantkt.api.FirEnchantAPI
import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.ConfigActionTemplate
import top.catnies.firenchantkt.engine.ConfigConditionTemplate
import top.catnies.firenchantkt.integration.FirItemProviderRegistry
import top.catnies.firenchantkt.language.MessageConstants
import top.catnies.firenchantkt.util.MessageUtils.renderToComponent
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent
import java.io.StringReader

object YamlUtils {

    // 将 ListMap 转换成 List<YamlConfiguration>
    fun ConfigurationSection?.getConfigurationSectionList(key: String) : List<YamlConfiguration> {
        val data: List<*> = this?.getList(key) ?: return ArrayList()

        return data.map { Yaml().dump(it) }
            .map { StringReader(it) }
            .map { YamlConfiguration.loadConfiguration(it) }
    }


    // 从配置文件节点解析物品
    fun parseItemFromConfig(section: ConfigurationSection): ItemStack? {
        // 必需的参数:
        val hookedPlugin = section.getString("hooked-plugin") ?: return null
        val hookedId = section.getString("hooked-id") ?: return null
        val itemProvider = FirItemProviderRegistry.instance.getItemProvider(hookedPlugin) ?: return null
        val item = itemProvider.getItemById(hookedId) ?: return null

        val builder = ItemBuilder.builder().setItem(item)
        section.getString("item-name")
            ?.let { builder.setDisplayName(it.renderToComponent()) }

        section.getStringList("lore")
            .map { it.renderToComponent() }
            .let { builder.setLore(it) }

        section.getString("item-model")
            ?.let { builder.setItemModel(it) }

        section.getDouble("custom-model-data")
            .let { builder.setCustomModelData(it.toFloat()) }

        section.getInt("amount", 1)
            .let { builder.setAmount(it) }

        section.getConfigurationSection("dyed-color")
            ?.let {
                builder.setColor(it.getInt("alpine", 1), it.getInt("red"), it.getInt("green"), it.getInt("blue"))
            }

        section.getConfigurationSection("firework-color")
            ?.let {
                builder.setFireworkStarColor(it.getInt("alpine", 1), it.getInt("red"), it.getInt("green"), it.getInt("blue"))
            }

        section.getString("durability")
            ?.let { builder.setDurability(section.getInt("durability")) }

        return builder.build()
    }
    
    // 解析Action模板列表
    fun parseActionTemplates(configList: List<ConfigurationSection>): List<ConfigActionTemplate> {
        return configList.mapNotNull { config ->
            parseActionTemplate(config)
        }
    }
    
    // 解析单个Action模板
    fun parseActionTemplate(config: ConfigurationSection): ConfigActionTemplate? {
        val type = config.getString("type") ?: return null
        val actionClass = FirEnchantAPI.actionRegistry().getAction(type) ?: run {
            Bukkit.getConsoleSender().sendTranslatableComponent(MessageConstants.CONFIG_ACTION_TYPE_UNKNOWN, type)
            return null
        }
        
        // 提取静态参数（排除conditions）
        val staticArgs = config.getKeys(false)
            .filter { it != "conditions" }
            .associateWith { config.get(it)!! }
            .toMutableMap()
        
        // 验证必需参数
        val missingRequiredArgs = validateRequiredArguments(actionClass, staticArgs)
        
        // 解析条件
        val conditions = config.getConfigurationSectionList("conditions")
            .mapNotNull { parseConditionTemplate(it) }
        
        return ConfigActionTemplate(
            type = type,
            actionClass = actionClass,
            staticArgs = staticArgs,
            missingRequiredArgs = missingRequiredArgs,
            conditions = conditions
        )
    }
    
    // 解析Condition模板
    fun parseConditionTemplate(config: ConfigurationSection): ConfigConditionTemplate? {
        val type = config.getString("type") ?: return null
        val conditionClass = FirEnchantAPI.conditionRegistry().getCondition(type) ?: run {
            Bukkit.getConsoleSender().sendTranslatableComponent(MessageConstants.CONFIG_CONDITION_TYPE_UNKNOWN, type)
            return null
        }
        
        val staticArgs = config.getKeys(false)
            .associateWith { config.get(it)!! }
            .toMutableMap()
        
        val missingRequiredArgs = validateRequiredArguments(conditionClass, staticArgs)
        
        return ConfigConditionTemplate(
            type = type,
            conditionClass = conditionClass,
            staticArgs = staticArgs,
            missingRequiredArgs = missingRequiredArgs
        )
    }
    
    // 验证必需参数
    private fun validateRequiredArguments(clazz: Class<*>, args: Map<String, Any?>): List<String> {
        val missingArgs = mutableListOf<String>()
        
        clazz.declaredFields.forEach { field ->
            val argumentKey = field.getAnnotation(ArgumentKey::class.java)
            if (argumentKey != null && argumentKey.required && !argumentKey.autoInject) {
                val hasArg = argumentKey.args.any { args.containsKey(it) }
                if (!hasArg) {
                    missingArgs.add(argumentKey.args.joinToString(" or "))
                }
            }
        }
        
        return missingArgs
    }
    
    // 解析物品及其点击动作
    fun parseItemWithActionsFromConfig(section: ConfigurationSection): Pair<ItemStack?, List<ConfigActionTemplate>> {
        val itemStack = parseItemFromConfig(section)
        val clickActions = section.getConfigurationSectionList("click-actions")
        val actionTemplates = parseActionTemplates(clickActions)
        
        return itemStack to actionTemplates
    }

}