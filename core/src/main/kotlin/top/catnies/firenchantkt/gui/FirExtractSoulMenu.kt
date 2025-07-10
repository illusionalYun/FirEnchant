package top.catnies.firenchantkt.gui

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.entity.Player
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.config.ExtractSoulSetting
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

    // 创建并且打开菜单
    override fun openMenu(data: Map<String, Any>, async: Boolean) {
        if (async) {
            plugin.launch {
                buildBaseGui()
                buildWindow()
                withContext(plugin.mainDispatcher) {
                    window?.open() ?: throw IllegalStateException("构建菜单时出现错误，请保存错误日志联系开发者解决.")
                }
            }
        } else {
            buildBaseGui()
            buildWindow()
            window?.open() ?: throw IllegalStateException("构建菜单时出现错误，请保存错误日志联系开发者解决.")
        }
    }

    // 创建 GUI
    private fun buildBaseGui() {
        val inputInventory = VirtualInventory(getMarkCount(inputSlot))
        val outputInventory = VirtualInventory(getMarkCount(outputSlot))
        val structure = Structure(*structureArray)
        gui = Gui.normal()
            .setStructure(structure)
            .addIngredient(inputSlot, inputInventory)
            .addIngredient(outputSlot, outputInventory)
            .apply { addCustomItems(this) }
            .build()
    }
    private fun addCustomItems(building: Gui.Builder.Normal) {
        return
    }

    // 创建 Window
    private fun buildWindow() {
        window = Window.single {
            it.setViewer(player)
            it.setTitle(title)
            it.setGui(gui!!)
            it.build()
        }
    }


    //


    // 统计 Structure 里有多少个某种 Slot 字符
    private fun getMarkCount(char: Char) = structureArray.sumOf { it.count { c -> c == char } }
}