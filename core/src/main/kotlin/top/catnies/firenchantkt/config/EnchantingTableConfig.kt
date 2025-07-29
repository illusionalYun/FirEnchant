package top.catnies.firenchantkt.config

import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.engine.ConfigActionTemplate
import top.catnies.firenchantkt.engine.ConfigConditionTemplate
import top.catnies.firenchantkt.language.MessageConstants.RESOURCE_MENU_STRUCTURE_ERROR
import top.catnies.firenchantkt.util.ConfigParser
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent
import top.catnies.firenchantkt.util.YamlUtils.getConfigurationSectionList
import xyz.xenondevs.invui.gui.structure.Structure

class EnchantingTableConfig private constructor():
    AbstractConfigFile("modules/enchanting_table.yml")
{
    companion object {
        @JvmStatic
        val instance by lazy { EnchantingTableConfig().apply { loadConfig() } }
    }

    val fallbackMenuStructure = arrayOf(
        "........?",
        ".I.aaaaaA",
        "...bbbbbB",
        ".X.cccccC",
    )

    var REPLACE_VANILLA_ENCHANTMENT_TABLE: Boolean by ConfigProperty(true)      // 点击原版附魔台时打开新版附魔gui

    var MENU_TITLE_000: String by ConfigProperty("000")
    var MENU_TITLE_100: String by ConfigProperty("100")
    var MENU_TITLE_110: String by ConfigProperty("110")
    var MENU_TITLE_111: String by ConfigProperty("111")
    var MENU_TITLE_222: String by ConfigProperty("222")
    var MENU_STRUCTURE_ARRAY: Array<String> by ConfigProperty(fallbackMenuStructure)    // 菜单结构
    var MENU_INPUT_SLOT: Char by ConfigProperty('I')                                    // 放入物品的槽位

    var MENU_SHOW_ENCHANTMENT_LINE_1_SLOT: Char by ConfigProperty('a')
    var MENU_SHOW_ENCHANTMENT_LINE_2_SLOT: Char by ConfigProperty('b')
    var MENU_SHOW_ENCHANTMENT_LINE_3_SLOT: Char by ConfigProperty('c')
    var MENU_SHOW_ENCHANTMENT_LINE_1_BOOK_SLOT: Char by ConfigProperty('A')
    var MENU_SHOW_ENCHANTMENT_LINE_2_BOOK_SLOT: Char by ConfigProperty('B')
    var MENU_SHOW_ENCHANTMENT_LINE_3_BOOK_SLOT: Char by ConfigProperty('C')

    var MENU_SHOW_ENCHANTMENT_LINE_1_ONLINE: ConfigurationSection? by ConfigProperty(null)
    var MENU_SHOW_ENCHANTMENT_LINE_2_ONLINE: ConfigurationSection? by ConfigProperty(null)
    var MENU_SHOW_ENCHANTMENT_LINE_3_ONLINE: ConfigurationSection? by ConfigProperty(null)
    var MENU_SHOW_ENCHANTMENT_LINE_1_OFFLINE: ConfigurationSection? by ConfigProperty(null)
    var MENU_SHOW_ENCHANTMENT_LINE_2_OFFLINE: ConfigurationSection? by ConfigProperty(null)
    var MENU_SHOW_ENCHANTMENT_LINE_3_OFFLINE: ConfigurationSection? by ConfigProperty(null)

    var MENU_CUSTOM_ITEMS: Map<Char, Pair<ItemStack?, List<ConfigActionTemplate>>> by ConfigProperty(emptyMap())    // 菜单中的自定义物品

    var ENCHANT_COST_LINE_1_CONDITIONS: List<ConfigConditionTemplate> by ConfigProperty(listOf())
    var ENCHANT_COST_LINE_2_CONDITIONS: List<ConfigConditionTemplate> by ConfigProperty(listOf())
    var ENCHANT_COST_LINE_3_CONDITIONS: List<ConfigConditionTemplate> by ConfigProperty(listOf())
    var ENCHANT_COST_LINE_1_ACTIONS: List<ConfigActionTemplate> by ConfigProperty(listOf())
    var ENCHANT_COST_LINE_2_ACTIONS: List<ConfigActionTemplate> by ConfigProperty(listOf())
    var ENCHANT_COST_LINE_3_ACTIONS: List<ConfigActionTemplate> by ConfigProperty(listOf())


    // 加载数据
    override fun loadConfig() {
        REPLACE_VANILLA_ENCHANTMENT_TABLE = config().getBoolean("replace-vanilla-enchantment-table", true)

        MENU_TITLE_000 = config().getString("menu-setting.title-000", "000")!!
        MENU_TITLE_100 = config().getString("menu-setting.title-100", "100")!!
        MENU_TITLE_110 = config().getString("menu-setting.title-110", "110")!!
        MENU_TITLE_111 = config().getString("menu-setting.title-111", "111")!!
        MENU_TITLE_222 = config().getString("menu-setting.title-222", "222")!!
        try { config().getStringList("menu-setting.structure").toTypedArray()
            .also { Structure(*it); MENU_STRUCTURE_ARRAY = it } // 测试合法性然后再赋值
        } catch (exception: Exception) {
            Bukkit.getConsoleSender().sendTranslatableComponent(RESOURCE_MENU_STRUCTURE_ERROR, fileName) }
        MENU_INPUT_SLOT = config().getString("menu-setting.input-slot", "I")?.first() ?: 'I'

        MENU_SHOW_ENCHANTMENT_LINE_1_SLOT = config().getString("menu-setting.show-enchantment-slot.line-1.slot", "a")?.first() ?: 'a'
        MENU_SHOW_ENCHANTMENT_LINE_2_SLOT = config().getString("menu-setting.show-enchantment-slot.line-2.slot", "b")?.first() ?: 'b'
        MENU_SHOW_ENCHANTMENT_LINE_3_SLOT = config().getString("menu-setting.show-enchantment-slot.line-3.slot", "c")?.first() ?: 'c'
        MENU_SHOW_ENCHANTMENT_LINE_1_BOOK_SLOT = config().getString("menu-setting.show-enchantment-slot.line-1-book.slot", "A")?.first() ?: 'A'
        MENU_SHOW_ENCHANTMENT_LINE_2_BOOK_SLOT = config().getString("menu-setting.show-enchantment-slot.line-2-book.slot", "B")?.first() ?: 'B'
        MENU_SHOW_ENCHANTMENT_LINE_3_BOOK_SLOT = config().getString("menu-setting.show-enchantment-slot.line-3-book.slot", "C")?.first() ?: 'C'

        MENU_SHOW_ENCHANTMENT_LINE_1_ONLINE = config().getConfigurationSection("menu-setting.show-enchantment-slot.line-1.online")
        MENU_SHOW_ENCHANTMENT_LINE_2_ONLINE = config().getConfigurationSection("menu-setting.show-enchantment-slot.line-2.online")
        MENU_SHOW_ENCHANTMENT_LINE_3_ONLINE = config().getConfigurationSection("menu-setting.show-enchantment-slot.line-3.online")
        MENU_SHOW_ENCHANTMENT_LINE_1_OFFLINE = config().getConfigurationSection("menu-setting.show-enchantment-slot.line-1.offline")
        MENU_SHOW_ENCHANTMENT_LINE_2_OFFLINE = config().getConfigurationSection("menu-setting.show-enchantment-slot.line-2.offline")
        MENU_SHOW_ENCHANTMENT_LINE_3_OFFLINE = config().getConfigurationSection("menu-setting.show-enchantment-slot.line-3.offline")
    }

    // 等待注册表完成后延迟加载的部分
    override fun loadLatePartConfig() {
        // 自定义物品
        config().getConfigurationSection("menu-setting.custom-items")?.let { customItemsSection ->
            val customItems = mutableMapOf<Char, Pair<ItemStack?, List<ConfigActionTemplate>>>() // 创建结果列表
            customItemsSection.getKeys(false).forEach { itemSectionKey ->
                // 解析物品节点如 'X', '?' 等节点
                val itemSection = customItemsSection.getConfigurationSection(itemSectionKey) // 这些 key 就是 如 'X', '?' 等
                itemSection?.let { section ->
                    // 使用节点构建物品
                    val itemStack = ConfigParser.parseItemFromConfig(section, fileName, itemSectionKey)
                    // 获取动作节点, 解析动作
                    val actionList = section.getConfigurationSectionList("click-actions")
                    val actionTemplates = actionList.mapNotNull { actionNode ->
                        ConfigParser.parseActionTemplate(actionNode, fileName, itemSectionKey)
                    }
                    if (itemStack.nullOrAir()) return@forEach // 如果物品是空则跳过保存, 延迟处理是想要继续解析物品动作一类的并给予警告, 虽然物品无效整个节点都无效就是了.
                    // 保存到结果列表里
                    customItems[itemSectionKey.first()] = itemStack to actionTemplates
                }
            }
            MENU_CUSTOM_ITEMS = customItems
        }

        // 条件
        ENCHANT_COST_LINE_1_CONDITIONS = config().getConfigurationSectionList("enchant-cost.line-1.conditions")
            .mapNotNull { ConfigParser.parseConditionTemplate(it, fileName, "enchant-cost.line-1.conditions") }
        ENCHANT_COST_LINE_2_CONDITIONS = config().getConfigurationSectionList("enchant-cost.line-2.conditions")
            .mapNotNull { ConfigParser.parseConditionTemplate(it, fileName, "enchant-cost.line-2.conditions") }
        ENCHANT_COST_LINE_3_CONDITIONS = config().getConfigurationSectionList("enchant-cost.line-3.conditions")
            .mapNotNull { ConfigParser.parseConditionTemplate(it, fileName, "enchant-cost.line-3.conditions") }

        ENCHANT_COST_LINE_1_ACTIONS = config().getConfigurationSectionList("enchant-cost.line-1.actions")
            .mapNotNull { ConfigParser.parseActionTemplate(it, fileName, "enchant-cost.line-1.actions") }
        ENCHANT_COST_LINE_2_ACTIONS = config().getConfigurationSectionList("enchant-cost.line-2.actions")
            .mapNotNull { ConfigParser.parseActionTemplate(it, fileName, "enchant-cost.line-2.actions") }
        ENCHANT_COST_LINE_3_ACTIONS = config().getConfigurationSectionList("enchant-cost.line-3.actions")
            .mapNotNull { ConfigParser.parseActionTemplate(it, fileName, "enchant-cost.line-3.actions") }
    }
}