package top.catnies.firenchantkt.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.config.FixTableConfig
import top.catnies.firenchantkt.database.FirConnectionManager
import top.catnies.firenchantkt.database.dao.ItemRepairData
import top.catnies.firenchantkt.database.entity.ItemRepairTable
import top.catnies.firenchantkt.engine.RunSource
import top.catnies.firenchantkt.gui.item.MenuCustomItem
import top.catnies.firenchantkt.gui.item.MenuPageItem
import top.catnies.firenchantkt.gui.item.MenuRepairItem
import top.catnies.firenchantkt.item.fixtable.FirBrokenGear
import top.catnies.firenchantkt.language.MessageConstants.FIXTABLE_REPAIR_ITEM_RECEIVE_FAIL
import top.catnies.firenchantkt.language.MessageConstants.FIXTABLE_REPAIR_ITEM_RECEIVE_SUCCESS
import top.catnies.firenchantkt.util.ItemUtils.deserializeFromBytes
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir
import top.catnies.firenchantkt.util.ItemUtils.replacePlaceholder
import top.catnies.firenchantkt.util.ItemUtils.serializeToBytes
import top.catnies.firenchantkt.util.MessageUtils.renderToComponent
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent
import top.catnies.firenchantkt.util.PlayerUtils.giveOrDrop
import top.catnies.firenchantkt.util.PlayerUtils.giveOrDropList
import top.catnies.firenchantkt.util.TaskUtils
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.gui.structure.Structure
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.inventory.event.UpdateReason
import xyz.xenondevs.invui.item.Item
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.window.Window
import java.util.function.Consumer
import kotlin.math.max
import kotlin.math.min

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

    val outputUpdateTime = config.MENU_OUTPUT_UPDATE_TIME
    val activeAdditionLores = config.MENU_OUTPUT_ACTIVE_ADDITION_LORE
    val completedAdditionLores = config.MENU_OUTPUT_COMPLETED_ADDITION_LORE

    /*构建时对象*/
    lateinit var gui: PagedGui<Item>
    lateinit var window: Window
    lateinit var inputInventory: VirtualInventory
    lateinit var confirmBottom: SimpleItem
    lateinit var previousPageBottom: MenuPageItem
    lateinit var nextPageBottom: MenuPageItem

    var closeHandlers: MutableList<Runnable> = mutableListOf() // 关闭菜单时触发
    var showBottom: Boolean = false

    /*数据对象*/
    val itemRepairData: ItemRepairData = FirConnectionManager.getInstance().itemRepairData
    val repairList: MutableList<Item> = mutableListOf() // 展示在菜单的修复的列表

    // 打开菜单
    override fun openMenu(data: Map<String, Any>, async: Boolean) {
        if (async) {
            TaskUtils.runAsyncTaskWithSyncCallback(
                async = {
                    initRepairItems() // 读取玩家修复列表, 构建列表
                    buildInputInventory()
                    buildConfirmItem()
                    buildPageItem()
                    buildGuiAndWindow()
                },
                callback = { window.open() }
            )
        } else {
            buildConfirmItem() // 读取玩家修复列表, 构建列表
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
            val isInputBrokenGear = brokenGear.isBrokenGear(event.newItem)
            when {
                (event.isAdd || event.isSwap) && isInputBrokenGear -> {
                    TaskUtils.runAsyncTasksLater({ window.changeTitle(titleAccept) }, delay = 1L) // 延迟刷新标题, 否则可能会把物品刷新给覆盖掉.
                    showBottom = true
                    confirmBottom.notifyWindows()
                }
                event.isRemove -> {
                    TaskUtils.runAsyncTasksLater({ window.changeTitle(titleDeny) }, delay = 1L)
                    showBottom = false
                    confirmBottom.notifyWindows()
                }
                else -> {
                    event.isCancelled = true
                    return@Consumer
                }
            }
        }
    }

    // 添加确认修复按钮
    private fun buildConfirmItem() {
        confirmBottom = SimpleItem({ s: String? ->
            if (!showBottom) return@SimpleItem ItemStack(Material.AIR)
            else return@SimpleItem fixSlotItem!!.first!!
        }) { click ->
            if (!showBottom) return@SimpleItem // 无显示时不做任何操作

            // 执行动作
            val args = mutableMapOf<String, Any?>()
            args["checkSource"] = RunSource.MENUCLICK
            args["player"] = player
            args["clickType"] = click.clickType.name
            args["event"] = click.event
            fixSlotItem?.second?.forEach { it.executeIfAllowed(args) }

            // 执行修复功能
            val inputItem = inputInventory.items.first() ?: return@SimpleItem
            if (!brokenGear.isBrokenGear(inputItem)) return@SimpleItem

            // TODO 计算物品修复时间
            val repairTime = 600 * 1000L
            val repairTable = ItemRepairTable(player.uniqueId, inputItem.serializeToBytes(), repairTime)

            // 将物品删除
            inputInventory.removeIf(UpdateReason.SUPPRESSED) { !it.nullOrAir() }
            // 插入队列, 刷新队列
            addDataToRepairList(repairTable)
            itemRepairData.insert(repairTable)
            gui.setContent(repairList)
            // 刷新自己
            showBottom = false
            confirmBottom.notifyWindows()
        }
    }

    // 上一页 和 下一页
    private fun buildPageItem() {
        previousPageBottom = MenuPageItem(false, previousPageItem!!.second) { s ->
            if (gui.currentPage == 0) return@MenuPageItem ItemStack.empty()

            val itemStack = previousPageItem.first!!.clone()
            itemStack.replacePlaceholder(
                mutableMapOf(
                    "currentPage" to "${gui.currentPage}",
                    "pageAmount" to "${gui.pageAmount}",
                    "previousPage" to "${max(0, gui.currentPage - 1)}",
                    "nextPage" to "${min(gui.pageAmount, gui.currentPage + 1)}"
                )
            )
            return@MenuPageItem itemStack
        }

        nextPageBottom = MenuPageItem(true, nextPageItem!!.second) { s ->
            if (gui.currentPage == gui.pageAmount - 1) return@MenuPageItem ItemStack.empty()

            val itemStack = nextPageItem.first!!.clone()
            itemStack.replacePlaceholder(
                mutableMapOf(
                    "currentPage" to "${gui.currentPage}",
                    "pageAmount" to "${gui.pageAmount}",
                    "previousPage" to "${max(0, gui.currentPage - 1)}",
                    "nextPage" to "${min(gui.pageAmount, gui.currentPage + 1)}"
                )
            )
            return@MenuPageItem itemStack
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
            .setContent(repairList) // 翻页内容
            // 自定义物品
            .also {
                customItems.filter { customItem -> getMarkCount(customItem.key) > 0 }
                    .forEach { (char, pair) ->
                        val menuCustomItem = MenuCustomItem({ s -> pair.first!! }, pair.second)
                        it.addIngredient(char, menuCustomItem)
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

    // 创建修复队列
    private fun initRepairItems() {
        val activeData = itemRepairData.getByPlayerActiveAndCompletedList(player.uniqueId)
        activeData.forEach { addDataToRepairList(it) }
    }

    // 往修复队列里添加新的数据, 并上传更新数据库
    private fun addDataToRepairList(itemRepairTable: ItemRepairTable) {
        if (itemRepairTable.isReceived) return

        val originItem = itemRepairTable.itemData.deserializeFromBytes()
        val builder = ItemBuilder(originItem)
        if (itemRepairTable.isCompleted) builder.addLoreLines(completedAdditionLores.map { line -> AdventureComponentWrapper(line.renderToComponent())})
        else builder.addLoreLines(activeAdditionLores.map { line -> AdventureComponentWrapper(line.renderToComponent())})
        val autoUpdateItem = MenuRepairItem(itemRepairTable, outputUpdateTime, originItem, builder, { click ->
            when {
                // 当装备已经被领取
                itemRepairTable.isReceived -> return@MenuRepairItem true
                // 当装备修复完成
                itemRepairTable.isCompleted -> {
                    removeDataFromRepairList(itemRepairTable)
                    itemRepairData.insert(itemRepairTable.apply { isReceived = true })
                    gui.setContent(repairList)
                    player.giveOrDrop(itemRepairTable.repairedItem)
                    click.player.sendTranslatableComponent(FIXTABLE_REPAIR_ITEM_RECEIVE_SUCCESS)
                }
                // 当装备还在修复中
                else -> {
                    click.player.sendTranslatableComponent(FIXTABLE_REPAIR_ITEM_RECEIVE_FAIL)
                }
            }
            true
        })
        repairList.add(autoUpdateItem)
    }

    // 从修复队列里删除数据
    private fun removeDataFromRepairList(itemRepairTable: ItemRepairTable) {
        repairList.removeIf { (it as? MenuRepairItem)?.data?.id == itemRepairTable.id }
    }

    // 统计 Structure 里有多少个某种 Slot 字符
    private fun getMarkCount(char: Char) = structureArray.sumOf { it.count { c -> c == char } }
}