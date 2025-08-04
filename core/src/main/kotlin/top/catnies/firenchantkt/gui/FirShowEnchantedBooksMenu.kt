package top.catnies.firenchantkt.gui

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.config.ShowEnchantedBooksConfig
import top.catnies.firenchantkt.gui.item.MenuCustomItem
import top.catnies.firenchantkt.gui.item.MenuPageItem
import top.catnies.firenchantkt.util.ItemUtils.replacePlaceholder
import top.catnies.firenchantkt.util.MessageUtils.wrapTitle
import top.catnies.firenchantkt.util.TaskUtils
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.gui.structure.Structure
import xyz.xenondevs.invui.item.Item
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.window.Window
import kotlin.math.max
import kotlin.math.min

class FirShowEnchantedBooksMenu(
    val player: Player
): ShowEnchantedBooksMenu {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val config = ShowEnchantedBooksConfig.instance
    }

    /*配置文件缓存*/
    val title = config.MENU_TITLE
    val structureArray = config.MENU_STRUCTURE_ARRAY
    val contentSlot = config.MENU_CONTENT_SLOT
    val previousPageSlot = config.MENU_PREPAGE_SLOT
    val previousPageItem = config.MENU_PREPAGE_SLOT_ITEM
    val nextPageSlot = config.MENU_NEXTPAGE_SLOT
    val nextPageItem = config.MENU_NEXTPAGE_SLOT_ITEM
    val customItems = config.MENU_CUSTOM_ITEMS
    val enchantedBookList = config.SHOW_ENCHANTEDBOOKS

    /*构建时对象*/
    lateinit var gui: PagedGui<Item>
    lateinit var window: Window
    lateinit var previousPageBottom: MenuPageItem
    lateinit var nextPageBottom: MenuPageItem

    override fun openMenu(data: Map<String, Any>, async: Boolean) {
        val buildTask = {
            buildPageItem()
            buildGuiAndWindow()
        }
        if (async) {
            TaskUtils.runAsyncTaskWithSyncCallback(
                async = buildTask,
                callback = { window.open() }
            )
        } else {
            buildTask()
            window.open()
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
            if (gui.pageAmount == 0) return@MenuPageItem ItemStack.empty() // 总页数为0代表目前没有正在修复的装备
            if (gui.currentPage == gui.pageAmount - 1) return@MenuPageItem ItemStack.empty() // 如果当前页数 = (总页数 - 1)就代表是最后一页

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
            .addIngredient(contentSlot, Markers.CONTENT_LIST_SLOT_HORIZONTAL)
            .addIngredient(previousPageSlot, previousPageBottom)
            .addIngredient(nextPageSlot, nextPageBottom)
            .setContent(enchantedBookList.map { SimpleItem(it) }) // 翻页内容
            // 自定义物品
            .also {
                customItems.filter { customItem -> getMarkCount(customItem.key) > 0 }
                    .forEach { (char, pair) ->
                        val menuCustomItem = MenuCustomItem({ s -> pair.first!! }, pair.second)
                        it.addIngredient(char, menuCustomItem)
                    }
            }
            .build()

        window = Window.single {
            it.setViewer(player)
            it.setTitle(title.wrapTitle(player))
            it.setGui(gui)
            it.build()
        }
    }

    // 统计 Structure 里有多少个某种 Slot 字符
    private fun getMarkCount(char: Char) = structureArray.sumOf { it.count { c -> c == char } }
}