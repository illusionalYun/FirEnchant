package top.catnies.firenchantkt.gui

import org.bukkit.entity.Player
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.config.ExtractSoulSetting
import top.catnies.firenchantkt.util.PlayerUtils.giveOrDropList
import top.catnies.firenchantkt.util.TaskUtils
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.gui.structure.Structure
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.window.Window

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

    var gui: Gui? = null
    var window: Window? = null
    var inputInventory: VirtualInventory? = null
    var outputInventory: VirtualInventory? = null

    // 关闭菜单时触发
    var closeHandlers: MutableList<() -> Unit> = mutableListOf()

    // 创建并且打开菜单
    override fun openMenu(data: Map<String, Any>, async: Boolean) {
        if (async) {
            TaskUtils.runAsyncTaskWithSyncCallback(
                async = { buildBaseComponents(); buildGuiAndWindow() },
                callback = { window?.open() ?: throw IllegalStateException("构建菜单时出现错误，请保存错误日志联系开发者解决.") }
            )
        } else {
            buildBaseComponents()
            buildGuiAndWindow()
            window?.open() ?: throw IllegalStateException("构建菜单时出现错误，请保存错误日志联系开发者解决.")
        }
    }

    // 创建基础组件
    private fun buildBaseComponents() {
        inputInventory = VirtualInventory(getMarkCount(inputSlot))
        outputInventory = VirtualInventory(getMarkCount(outputSlot))

        // 如果关闭菜单则返回输入框里的所有物品.
        closeHandlers.add {
            val itemStacks = inputInventory?.items?.toList()?.filterNotNull() ?: emptyList()
            player.giveOrDropList(itemStacks)
        }
    }

    // 创建 GUI & Window
    private fun buildGuiAndWindow() {
        gui = Gui.normal()
            .setStructure(Structure(*structureArray))
            .addIngredient(inputSlot, inputInventory!!)
            .addIngredient(outputSlot, outputInventory!!)
            .apply { addCustomItems(this) }
            .build()

        window = Window.single {
            it.setViewer(player)
            it.setTitle(title)
            it.setGui(gui!!)
            it.build()
        }
    }

    // 添加自定义物品
    private fun addCustomItems(building: Gui.Builder.Normal) {
        return
    }

    // 统计 Structure 里有多少个某种 Slot 字符
    private fun getMarkCount(char: Char) = structureArray.sumOf { it.count { c -> c == char } }
}