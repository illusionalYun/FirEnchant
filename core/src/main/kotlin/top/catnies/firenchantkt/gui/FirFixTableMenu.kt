package top.catnies.firenchantkt.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.config.FixTableConfig
import top.catnies.firenchantkt.database.entity.ItemRepairTable
import top.catnies.firenchantkt.item.fixtable.FirBrokenGear
import top.catnies.firenchantkt.util.PlayerUtils.giveOrDropList
import top.catnies.firenchantkt.util.TaskUtils
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.gui.structure.Structure
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.item.impl.controlitem.PageItem
import xyz.xenondevs.invui.window.Window
import java.util.function.Consumer

class FirFixTableMenu(
    val player: Player
): FixTableMenu {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val config = FixTableConfig.instance
        val brokenGear = FirBrokenGear.instance
    }

    /*配置文件缓存*/
    val titleDeny = config.MENU_TITLE_DENY
    val titleAccept = config.MENU_TITLE_ACCEPT
    val structureArray = config.MENU_STRUCTURE_ARRAY
    val inputSlot = config.MENU_INPUT_SLOT
    val outputSlot = config.MENU_OUTPUT_SLOT
    val fixSlot = config.MENU_FIX_SLOT
    val fixSlotItem = config.MENU_FIX_SLOT_ITEM
    val previousPageSlot = config.MENU_PREPAGE_SLOT
    val previousPageItem = config.MENU_PREPAGE_SLOT_ITEM
    val nextPageSlot = config.MENU_NEXTPAGE_SLOT
    val nextPageItem = config.MENU_NEXTPAGE_SLOT_ITEM
    val customItems = config.MENU_CUSTOM_ITEMS

    /*构建时对象*/
    lateinit var gui: Gui
    lateinit var window: Window
    lateinit var inputInventory: VirtualInventory
    lateinit var confirmBottom: SimpleItem
    lateinit var previousPageBottom: PageItem
    lateinit var nextPageBottom: PageItem

    var closeHandlers: MutableList<Runnable> = mutableListOf() // 关闭菜单时触发
    var showBottom: Boolean = false

    /*数据对象*/
    lateinit var activeData: List<ItemRepairTable> // 玩家正在修复+修复完成的物品列表

    // 打开菜单
    override fun openMenu(data: Map<String, Any>, async: Boolean) {
        if (async) {
            TaskUtils.runAsyncTaskWithSyncCallback(
                async = {
                    // TODO 读取玩家正在修复+修复完成的列表
                    buildInputInventory()
                    buildConfirmItem()
                    buildPageItem()
                    buildGuiAndWindow()
                },
                callback = { window.open() }
            )
        } else {
            buildConfirmItem()
            buildPageItem()
            buildGuiAndWindow()
            window.open()
        }
    }

    // 添加输入虚拟背包
    private fun buildInputInventory() {
        inputInventory = VirtualInventory(1)

        // 禁止非破损物品放入, 同时刷新菜单标题.
        inputInventory.preUpdateHandler = Consumer { event ->
            if (event.isSwap || event.isAdd) {
                if (!brokenGear.isBrokenGear(event.newItem)) {
                    event.isCancelled = true
                    return@Consumer
                }
                window.changeTitle(titleAccept)
                showBottom = true
                confirmBottom.notifyWindows()
            }
            if (event.isRemove) {
                window.changeTitle(titleDeny)
                showBottom = false
                confirmBottom.notifyWindows()
            }
        }
    }

    // 添加确认修复按钮
    private fun buildConfirmItem() {
        confirmBottom = SimpleItem({ s: String? ->
            if (!showBottom) return@SimpleItem ItemStack(Material.AIR)
            else return@SimpleItem fixSlotItem!!
        }) { click ->
            val inputItem = inputInventory.items.first()
            if (!brokenGear.isBrokenGear(inputItem)) return@SimpleItem
            // TODO 录入修复队列
        }
    }

    // 上一页 和 下一页
    private fun buildPageItem() {
        previousPageBottom = object :PageItem(false) {
            override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
                val builder = ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                builder.setDisplayName("Previous page")
                    .addLoreLines(
                        if (gui.hasPreviousPage()) "Go to page " + gui.currentPage + "/" + gui.pageAmount
                        else "You can't go further back"
                    )
                return builder
            }
        }
        nextPageBottom = object :PageItem(true) {
            override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
                val builder = ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                builder.setDisplayName("Next page")
                    .addLoreLines(
                        if (gui.hasNextPage()) "Go to page " + (gui.currentPage + 2) + "/" + gui.pageAmount
                        else "There are no more pages"
                    )
                return builder
            }
        }
    }

    // 创建 GUI & Window
    private fun buildGuiAndWindow() {
        gui = PagedGui.items()
            .setStructure(Structure(*structureArray))
            .addIngredient(outputSlot, Markers.CONTENT_LIST_SLOT_HORIZONTAL)
            .addIngredient(inputSlot, inputInventory)
            .addIngredient(fixSlot, confirmBottom)
            .addIngredient(previousPageSlot, previousPageBottom)
            .addIngredient(nextPageSlot, nextPageBottom)
            // 翻页内容
            .setContent(
                // TODO 创建内容, 根据列表实际大小创建, max(数据量, 最大数据量)
                Material.values()
                .filter { !it.isAir && it.isItem }
                .map { SimpleItem(ItemBuilder(it)) }
            )
            // 自定义物品
            .also {
                customItems.filter { customItem -> getMarkCount(customItem.key) > 0 }
                    .forEach { (char, pair) ->
                        val menuItem = MenuItem({ s -> pair.first!! }, pair.second)
                        it.addIngredient(char, menuItem)
                    }
            }
            .build()

        // 如果关闭菜单则返回输入框里的所有物品.
        closeHandlers.add {
            val itemStacks = inputInventory.items.toList().filterNotNull()
            player.giveOrDropList(itemStacks)
        }

        window = Window.single {
            it.setViewer(player)
            it.setTitle(titleDeny)
            it.setCloseHandlers(closeHandlers)
            it.setGui(gui)
            it.build()
        }
    }

    // 统计 Structure 里有多少个某种 Slot 字符
    private fun getMarkCount(char: Char) = structureArray.sumOf { it.count { c -> c == char } }
}