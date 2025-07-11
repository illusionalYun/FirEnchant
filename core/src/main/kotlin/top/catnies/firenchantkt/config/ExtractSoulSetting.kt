package top.catnies.firenchantkt.config

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.engine.ConfigActionTemplate
import top.catnies.firenchantkt.language.MessageConstants.CONFIG_ACTION_INVALID_CUSTOM_ITEM
import top.catnies.firenchantkt.language.MessageConstants.CONFIG_CONDITION_INVALID_CUSTOM_ITEM
import top.catnies.firenchantkt.language.MessageConstants.RESOURCE_MENU_STRUCTURE_ERROR
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent
import top.catnies.firenchantkt.util.YamlUtils
import xyz.xenondevs.invui.gui.structure.Structure

class ExtractSoulSetting private constructor():
    AbstractConfigFile("modules/extract_soul.yml")
{

    companion object {
        @JvmStatic
        val instance by lazy { ExtractSoulSetting().apply { loadConfig() } }
    }

    val fallbackMenuStructure = arrayOf(
        "X.......?",
        "IIIIIIIII",
        "IIIIIIIII",
        "IIIIIIIII",
        ".........",
        "....O...."
    )

    /*菜单设置*/
    var MENU_TITLE: String by ConfigProperty("Extract Soul Menu")
    var MENU_STRUCTURE_ARRAY: Array<String> by ConfigProperty(fallbackMenuStructure)
    var MENU_INPUT_SLOT: Char by ConfigProperty('I')
    var MENU_OUTPUT_SLOT: Char by ConfigProperty('O')
    var MENU_CUSTOM_ITEMS: Map<String, Pair<ItemStack?, List<ConfigActionTemplate>>> by ConfigProperty(emptyMap())


    // 加载数据
    override fun loadConfig() {
        /*菜单设置*/
        MENU_TITLE = config().getString("menu-setting.title", "Extract Soul Menu")!!
        try { config().getStringList("menu-setting.structure").toTypedArray()
                .also { Structure(*it); MENU_STRUCTURE_ARRAY = it } // 测试合法性然后再赋值
        } catch (exception: IllegalArgumentException) {
            Bukkit.getConsoleSender().sendTranslatableComponent(RESOURCE_MENU_STRUCTURE_ERROR, fileName) }
        MENU_INPUT_SLOT = config().getString("menu-setting.input-slot", "I")?.first() ?: 'I'
        MENU_OUTPUT_SLOT = config().getString("menu-setting.output-slot", "O")?.first() ?: 'O'

        // 读取配置文件, 尝试构建物品, 尝试构建点击逻辑链并缓存.
        config().getConfigurationSection("menu-setting.custom-items")?.let { customItemsSection ->
            val customItems = mutableMapOf<String, Pair<ItemStack?, List<ConfigActionTemplate>>>()
            
            customItemsSection.getKeys(false).forEach { key ->
                val itemSection = customItemsSection.getConfigurationSection(key)
                itemSection?.let { section ->
                    // 解析物品和动作模板并缓存
                    val (itemStack, actionTemplates) = YamlUtils.parseItemWithActionsFromConfig(section)
                    
                    // 验证配置并记录错误
                    actionTemplates.forEach { template ->
                        if (!template.isValid) {
                            Bukkit.getConsoleSender().sendTranslatableComponent(
                                CONFIG_ACTION_INVALID_CUSTOM_ITEM, 
                                key, 
                                template.missingRequiredArgs.joinToString(", ")
                            )
                        }
                        template.conditions.forEach { condition ->
                            if (!condition.isValid) {
                                Bukkit.getConsoleSender().sendTranslatableComponent(
                                    CONFIG_CONDITION_INVALID_CUSTOM_ITEM, 
                                    key, 
                                    condition.missingRequiredArgs.joinToString(", ")
                                )
                            }
                        }
                    }
                    
                    customItems[key] = itemStack to actionTemplates
                }
            }
            
            MENU_CUSTOM_ITEMS = customItems
        }

    }

}