package top.catnies.firenchantkt.gui

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.config.EnchantingTableConfig
import top.catnies.firenchantkt.context.EnchantingTableContext
import top.catnies.firenchantkt.enchantment.EnchantmentSetting
import top.catnies.firenchantkt.gui.item.MenuCustomItem
import top.catnies.firenchantkt.gui.item.MenuEnchantLineItem
import top.catnies.firenchantkt.item.FirEnchantingTableRegistry
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir
import top.catnies.firenchantkt.util.MessageUtils.wrapTitle
import top.catnies.firenchantkt.util.PlayerUtils.giveOrDropList
import top.catnies.firenchantkt.util.TaskUtils
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.gui.structure.Structure
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.inventory.event.UpdateReason
import xyz.xenondevs.invui.window.Window
import java.util.function.Consumer

class FirEnchantingTableMenu(
    val player: Player,
    var bookShelves: Int = 0
): EnchantingTableMenu {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val config = EnchantingTableConfig.instance
    }

    val title000 = config.MENU_TITLE_000
    val title100 = config.MENU_TITLE_100
    val title110 = config.MENU_TITLE_110
    val title111 = config.MENU_TITLE_111
    val title222 = config.MENU_TITLE_222
    val structureArray = config.MENU_STRUCTURE_ARRAY
    val inputSlot = config.MENU_INPUT_SLOT
    val customItems = config.MENU_CUSTOM_ITEMS

    val enchantmentLine1Slot = config.MENU_SHOW_ENCHANTMENT_LINE_1_SLOT
    val enchantmentLine2Slot = config.MENU_SHOW_ENCHANTMENT_LINE_2_SLOT
    val enchantmentLine3Slot = config.MENU_SHOW_ENCHANTMENT_LINE_3_SLOT
    val enchantmentBook1Slot = config.MENU_SHOW_ENCHANTMENT_LINE_1_BOOK_SLOT
    val enchantmentBook2Slot = config.MENU_SHOW_ENCHANTMENT_LINE_2_BOOK_SLOT
    val enchantmentBook3Slot = config.MENU_SHOW_ENCHANTMENT_LINE_3_BOOK_SLOT

    var conditionLine1 = config.ENCHANT_COST_LINE_1_CONDITIONS
    var conditionLine2 = config.ENCHANT_COST_LINE_2_CONDITIONS
    var conditionLine3 = config.ENCHANT_COST_LINE_3_CONDITIONS
    var actionLine1 = config.ENCHANT_COST_LINE_1_ACTIONS
    var actionLine2 = config.ENCHANT_COST_LINE_2_ACTIONS
    var actionLine3 = config.ENCHANT_COST_LINE_3_ACTIONS

    val enchantmentOnline1 = config.MENU_SHOW_ENCHANTMENT_LINE_1_ONLINE
    val enchantmentOnline2 = config.MENU_SHOW_ENCHANTMENT_LINE_2_ONLINE
    val enchantmentOnline3 = config.MENU_SHOW_ENCHANTMENT_LINE_3_ONLINE
    val enchantmentOffline1 = config.MENU_SHOW_ENCHANTMENT_LINE_1_OFFLINE
    val enchantmentOffline2 = config.MENU_SHOW_ENCHANTMENT_LINE_2_OFFLINE
    val enchantmentOffline3 = config.MENU_SHOW_ENCHANTMENT_LINE_3_OFFLINE
    val enchantmentBookOnline1 = config.MENU_SHOW_ENCHANTMENT_LINE_1_BOOK_ONLINE
    val enchantmentBookOnline2 = config.MENU_SHOW_ENCHANTMENT_LINE_2_BOOK_ONLINE
    val enchantmentBookOnline3 = config.MENU_SHOW_ENCHANTMENT_LINE_3_BOOK_ONLINE
    val enchantmentBookOffline1 = config.MENU_SHOW_ENCHANTMENT_LINE_1_BOOK_OFFLINE
    val enchantmentBookOffline2 = config.MENU_SHOW_ENCHANTMENT_LINE_2_BOOK_OFFLINE
    val enchantmentBookOffline3 = config.MENU_SHOW_ENCHANTMENT_LINE_3_BOOK_OFFLINE

    var enchantable = 0
    var activeLine: Int = -1
    var settingLine1: EnchantmentSetting? = null
    var settingLine2: EnchantmentSetting? = null
    var settingLine3: EnchantmentSetting? = null


    lateinit var gui: Gui
    lateinit var window: Window
    lateinit var inputInventory: VirtualInventory

    lateinit var selectBottom1: MenuEnchantLineItem
    lateinit var selectBottom2: MenuEnchantLineItem
    lateinit var selectBottom3: MenuEnchantLineItem
    lateinit var bookBottom1: MenuEnchantLineItem
    lateinit var bookBottom2: MenuEnchantLineItem
    lateinit var bookBottom3: MenuEnchantLineItem

    // 关闭菜单时触发
    var closeHandlers: MutableList<Runnable> = mutableListOf()

    // 创建并且打开菜单
    override fun openMenu(data: Map<String, Any>, async: Boolean) {
        if (async) {
            TaskUtils.runAsyncTaskWithSyncCallback(
                async = {
                    buildBaseComponents()
                    buildLineBottom()
                    buildGuiAndWindow()
                },
                callback = { window.open() }
            )
        } else {
            buildBaseComponents()
            buildLineBottom()
            buildGuiAndWindow()
            window.open()
        }
    }

    // 创建基础组件
    private fun buildBaseComponents() {
        inputInventory = VirtualInventory(1)
        inputInventory.setMaxStackSize(0, 1)
        inputInventory.postUpdateHandler = Consumer { event ->
            // 添加了新物品, 执行检查
            if (event.isAdd || event.isSwap) {
                val applicableItem = FirEnchantingTableRegistry.instance.findApplicableItem(event.newItem!!)
                applicableItem?.onPostInput(event.newItem!!, EnchantingTableContext(player, bookShelves, this))
            }
            // 移除物品, 重置菜单
            if (event.isRemove) {
                clearEnchantmentMenu()
            }
        }

        // 如果关闭菜单则返回输入框里的所有物品.
        closeHandlers.add {
            val itemStacks = inputInventory.items.toList().filterNotNull()
            player.giveOrDropList(itemStacks)
        }
    }

    // 创建附魔栏物品
    private fun buildLineBottom() {
        selectBottom1 = MenuEnchantLineItem(this,
            1, conditionLine1, actionLine1, enchantmentOnline1, enchantmentOffline1)
        selectBottom2 = MenuEnchantLineItem(this,
            2, conditionLine2, actionLine2, enchantmentOnline2, enchantmentOffline2)
        selectBottom3 = MenuEnchantLineItem(this,
            3, conditionLine3, actionLine3, enchantmentOnline3, enchantmentOffline3)
        bookBottom1 = MenuEnchantLineItem(this,
            1, conditionLine1, actionLine1, enchantmentBookOnline1, enchantmentBookOffline1)
        bookBottom2 = MenuEnchantLineItem(this,
            2, conditionLine2, actionLine2, enchantmentBookOnline2, enchantmentBookOffline2)
        bookBottom3 = MenuEnchantLineItem(this,
            3, conditionLine3, actionLine3, enchantmentBookOnline3, enchantmentBookOffline3)
    }

    // 创建 GUI & Window
    private fun buildGuiAndWindow() {
        gui = Gui.normal()
            .setStructure(Structure(*structureArray))
            .addIngredient(inputSlot, inputInventory)

            .addIngredient(enchantmentBook1Slot, bookBottom1)
            .addIngredient(enchantmentBook2Slot, bookBottom2)
            .addIngredient(enchantmentBook3Slot, bookBottom3)
            .addIngredient(enchantmentLine1Slot, selectBottom1)
            .addIngredient(enchantmentLine2Slot, selectBottom2)
            .addIngredient(enchantmentLine3Slot, selectBottom3)

            .apply { addCustomItems(this) }
            .build()

        window = Window.single {
            it.setViewer(player)
            it.setTitle(title000.wrapTitle(player))
            it.setCloseHandlers(closeHandlers)
            it.setGui(gui)
            it.build()
        }
    }

    // 添加自定义物品
    private fun addCustomItems(building: Gui.Builder.Normal) {
        customItems
            .filter { getMarkCount(it.key) > 0 }
            .filterNot { it.value.first.nullOrAir() }
            .forEach { (char, pair) ->
                val menuCustomItem = MenuCustomItem({ s -> pair.first!! }, pair.second)
                building.addIngredient(char, menuCustomItem)
            }
    }

    // 设置附魔台的结果显示
    override fun setEnchantmentResult(list: List<EnchantmentSetting>) {
        settingLine1 = list[0]
        settingLine2 = list[1]
        settingLine3 = list[2]
    }

    // 检查玩家能亮几个附魔栏位
    override fun refreshCanLight(): Int {
        val args = mutableMapOf("player" to player)
        activeLine = when {
            conditionLine1.any { !it.check(args) } -> 0
            conditionLine2.any { !it.check(args) } -> 1
            conditionLine3.any { !it.check(args) } -> 2
            else -> 3
        }
        return activeLine
    }

    // 刷新附魔栏位
    override fun refreshLine() {
        when (activeLine) {
            0 -> window.changeTitle(title222.wrapTitle(player))
            1 -> window.changeTitle(title100.wrapTitle(player))
            2 -> window.changeTitle(title110.wrapTitle(player))
            3 -> window.changeTitle(title111.wrapTitle(player))
            -1 -> window.changeTitle(title000.wrapTitle(player))
        }
        selectBottom1.notifyWindows()
        selectBottom2.notifyWindows()
        selectBottom3.notifyWindows()
        bookBottom1.notifyWindows()
        bookBottom2.notifyWindows()
        bookBottom3.notifyWindows()
    }

    // 获取指定栏位的附魔书数据
    override fun getEnchantmentSettingByLine(line: Int): EnchantmentSetting? {
        return when (line) {
            1 -> settingLine1
            2 -> settingLine2
            3 -> settingLine3
            else -> throw IllegalArgumentException("获取索引只能是1~3.")
        }
    }

    // 清空附魔台状态
    fun clearEnchantmentMenu() {
        settingLine1 = null
        settingLine2 = null
        settingLine3 = null
        enchantable = 0
        activeLine = -1
        refreshLine()
        window.changeTitle(title000.wrapTitle(player))
    }

    // 设置记录的物品附魔力
    override fun setRecordEnchantable(enchantable: Int) {
        this.enchantable = enchantable
    }

    // 获取容器内的物品
    override fun getInputInventoryItem(): ItemStack? {
        return inputInventory.items.firstOrNull()
    }

    // 清空容器
    override fun clearInputInventory() {
        if (inputInventory.isEmpty) return
        inputInventory.removeIf(UpdateReason.SUPPRESSED) { true }
    }

    // 统计 Structure 里有多少个某种 Slot 字符
    private fun getMarkCount(char: Char) = structureArray.sumOf { it.count { c -> c == char } }
}