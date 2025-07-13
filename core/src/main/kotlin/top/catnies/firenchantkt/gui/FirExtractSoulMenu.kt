package top.catnies.firenchantkt.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.api.event.extractsoul.ExtractSoulEvent
import top.catnies.firenchantkt.config.ExtractSoulSetting
import top.catnies.firenchantkt.enchantment.FirEnchantmentSettingFactory
import top.catnies.firenchantkt.engine.RunSource
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir
import top.catnies.firenchantkt.util.PlayerUtils.giveOrDrop
import top.catnies.firenchantkt.util.PlayerUtils.giveOrDropList
import top.catnies.firenchantkt.util.TaskUtils
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.gui.structure.Structure
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.inventory.event.UpdateReason
import xyz.xenondevs.invui.item.Click
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.window.Window
import java.util.function.Consumer

class FirExtractSoulMenu(
    val player: Player,
): ExtractSoulMenu {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val config = ExtractSoulSetting.instance
    }

    val title = config.MENU_TITLE
    val structureArray = config.MENU_STRUCTURE_ARRAY
    val inputSlot = config.MENU_INPUT_SLOT
    val outputSlot = config.MENU_OUTPUT_SLOT
    val customItems = config.MENU_CUSTOM_ITEMS
    val resultItem = config.MENU_RESULT_ITEM!!.clone()
    val actions = config.MENU_RESULT_ITEM_CLICK_ACTIONS

    lateinit var gui: Gui
    lateinit var window: Window
    lateinit var inputInventory: VirtualInventory
    lateinit var outputItem: SimpleItem

    // 关闭菜单时触发
    var closeHandlers: MutableList<Runnable> = mutableListOf()

    // 创建并且打开菜单
    override fun openMenu(data: Map<String, Any>, async: Boolean) {
        if (async) {
            TaskUtils.runAsyncTaskWithSyncCallback(
                async = { buildBaseComponents(); buildOutputItem(); buildGuiAndWindow() },
                callback = { window.open() }
            )
        } else {
            buildBaseComponents()
            buildOutputItem()
            buildGuiAndWindow()
            window.open()
        }
    }

    // 创建基础组件
    private fun buildBaseComponents() {
        inputInventory = VirtualInventory(getMarkCount(inputSlot))
        inputInventory.postUpdateHandler = Consumer { event -> outputItem.notifyWindows()}

        // 如果关闭菜单则返回输入框里的所有物品.
        closeHandlers.add {
            val itemStacks = inputInventory.items.toList().filterNotNull()
            player.giveOrDropList(itemStacks)
        }
    }

    // 创建 GUI & Window
    private fun buildGuiAndWindow() {
        gui = Gui.normal()
            .setStructure(Structure(*structureArray))
            .addIngredient(inputSlot, inputInventory)
            .addIngredient(outputSlot, outputItem)
            .apply { addCustomItems(this) }
            .build()

        window = Window.single {
            it.setViewer(player)
            it.setTitle(title)
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
                val menuItem = MenuItem({ s -> pair.first!! }, pair.second)
                building.addIngredient(char, menuItem)
            }
    }

    // 获取输出物品
    private fun buildOutputItem() {
        outputItem = SimpleItem(
            { s: String? ->
                // 检查合法附魔书数量
                val count = inputInventory.count { !it.nullOrAir() && FirEnchantmentSettingFactory.fromItemStack(it) != null }
                // 设置结果物品
                if (count == 0) return@SimpleItem ItemStack(Material.AIR)
                else return@SimpleItem resultItem.apply {amount = count}
            }
        ) { click: Click? ->
            // 获取有效的附魔书
            val preRemoved = inputInventory.items.filter { !it.nullOrAir() && FirEnchantmentSettingFactory.fromItemStack(it) != null }
            val removedCount = preRemoved.size
            if (removedCount > 0) {
                // 结果物品
                val resultItem = resultItem.apply {amount = removedCount}
                // 触发事件
                val event = ExtractSoulEvent(player, preRemoved, mutableListOf(resultItem))
                Bukkit.getPluginManager().callEvent(event)
                if (event.isCancelled) return@SimpleItem
                // 扣除物品
                inputInventory.removeIf(UpdateReason.SUPPRESSED) { event.removedEnchantedBooks.contains(it) }
                // 给予物品
                player.giveOrDropList(event.resultItems)
                // 执行动作
                val runtimeArgs = mapOf(
                    "checkSource" to RunSource.MENUCLICK,
                    "player" to player,
                    "clickType" to click?.clickType?.name,
                    "event" to click?.event,
                    "removedCount" to removedCount
                )
                actions!!.forEach { it.executeIfAllowed(runtimeArgs) }
                // 刷新按钮
                outputItem.notifyWindows()
            }
        }
    }

    // 统计 Structure 里有多少个某种 Slot 字符
    private fun getMarkCount(char: Char) = structureArray.sumOf { it.count { c -> c == char } }
}