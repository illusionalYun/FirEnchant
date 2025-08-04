package top.catnies.firenchantkt.gui.item

import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.api.event.enchantingtable.EnchantItemEvent
import top.catnies.firenchantkt.database.FirCacheManager
import top.catnies.firenchantkt.database.FirConnectionManager
import top.catnies.firenchantkt.database.entity.EnchantingHistoryTable
import top.catnies.firenchantkt.engine.ConfigActionTemplate
import top.catnies.firenchantkt.engine.ConfigConditionTemplate
import top.catnies.firenchantkt.gui.FirEnchantingTableMenu
import top.catnies.firenchantkt.util.ConfigParser
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir
import top.catnies.firenchantkt.util.ItemUtils.serializeToBytes
import top.catnies.firenchantkt.util.TaskUtils
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.impl.AbstractItem

class MenuEnchantLineItem(
    val tableMenu: FirEnchantingTableMenu,
    val lineIndex: Int,
    var conditions: List<ConfigConditionTemplate>,
    var actions: List<ConfigActionTemplate>,
    var onlineSection: ConfigurationSection?,
    var offlineSection: ConfigurationSection?
): AbstractItem() {

    var canEnchant: Boolean = false

    override fun getItemProvider() = ItemProvider{ string ->
        val enchantmentSetting = tableMenu.getEnchantmentSettingByLine(lineIndex)

        // 未设置时直接返回空
        if (enchantmentSetting == null) {
            canEnchant = false
            return@ItemProvider ItemStack.empty()
        }

        // 如果条件符合
        val itemStack = enchantmentSetting.toItemStack()
        if (tableMenu.activeLine >= lineIndex) {
            canEnchant = true
            onlineSection?.let { ConfigParser.parseItemFromConfigWithBaseItem(itemStack, it) }
            return@ItemProvider itemStack
        }

        // 如果条件不符合
        canEnchant = false
        offlineSection?.let { ConfigParser.parseItemFromConfigWithBaseItem(itemStack, it) }
        return@ItemProvider itemStack
    }

    override fun handleClick(
        clickType: ClickType,
        player: Player,
        event: InventoryClickEvent
    ) {
        // 光标持有物品点击则不处理
        if (!event.cursor.nullOrAir()) return
        // 如果没有记录 或 可点亮栏位少于索引, 则代表条件现在已经不符合要求了
        if (!canEnchant || tableMenu.refreshCanLight() < lineIndex) {
            tableMenu.refreshLine()
            return
        }

        // 获取所需变量
        val inputItem = tableMenu.getInputInventoryItem() ?: return
        val setting = tableMenu.getEnchantmentSettingByLine(lineIndex)!!

        // 广播事件
        val enchantItemEvent = EnchantItemEvent(player, inputItem, setting, lineIndex)
        Bukkit.getPluginManager().callEvent(enchantItemEvent)
        if (enchantItemEvent.isCancelled) return

        // 记录缓存和数据
        val historyTable = EnchantingHistoryTable().apply {
            playerId = player.uniqueId
            inputItemData = inputItem.serializeToBytes()
            seed = player.enchantmentSeed
            bookShelfCount = tableMenu.bookShelves
            enchantable = tableMenu.enchantable
            enchantment = setting.data.key.asString()
            enchantmentLevel = setting.level
            enchantmentFailure = setting.failure
            timestamp = System.currentTimeMillis()
        }
        TaskUtils.runAsyncTask {
            FirCacheManager.getInstance().addEnchantingHistory(historyTable)
            FirConnectionManager.getInstance().enchantingHistoryData.create(historyTable)
        }

        // 执行附魔
        tableMenu.clearInputInventory() // 扣除物品
        tableMenu.clearEnchantmentMenu() // 刷新菜单状态
        player.setItemOnCursor(setting.toItemStack())
        player.enchantmentSeed = (0..Int.MAX_VALUE).random()

        // 执行动作
        actions.forEach { action ->
            action.executeIfAllowed(
                mapOf(
                    "player" to player,
                )
            )
        }
    }

}