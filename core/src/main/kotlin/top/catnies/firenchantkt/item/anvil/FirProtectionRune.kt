package top.catnies.firenchantkt.item.anvil

import com.saicone.rtag.RtagItem
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.view.AnvilView
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.api.event.anvil.ProtectionRunePreUseEvent
import top.catnies.firenchantkt.api.event.anvil.ProtectionRuneUseEvent
import top.catnies.firenchantkt.config.AnvilConfig
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.integration.FirItemProviderRegistry
import top.catnies.firenchantkt.integration.ItemProvider
import top.catnies.firenchantkt.language.MessageConstants.RESOURCE_HOOK_ITEM_NOT_FOUND
import top.catnies.firenchantkt.language.MessageConstants.RESOURCE_HOOK_ITEM_PROVIDER_NOT_FOUND
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent

class FirProtectionRune(): ProtectionRune {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = plugin.logger
        val config = AnvilConfig.instance
    }

    var isEnabled: Boolean = false
    var itemProvider: ItemProvider? = null
    var itemID: String? = null

    init {
        load()
    }

    // 检查配置合法性
    override fun load() {
        isEnabled = config.PROTECTION_RUNE_ENABLE
        if (isEnabled) {
            itemProvider = config.PROTECTION_RUNE_ITEM_PROVIDER?.let { FirItemProviderRegistry.instance.getItemProvider(it) }
            if (itemProvider == null) {
                Bukkit.getConsoleSender()
                    .sendTranslatableComponent(RESOURCE_HOOK_ITEM_PROVIDER_NOT_FOUND, config.fileName, "protection-rune", config.PROTECTION_RUNE_ITEM_PROVIDER ?: "null")
                isEnabled = false
                return
            }

            itemID = config.PROTECTION_RUNE_ITEM_ID
            val item = itemID?.let { itemProvider?.getItemById(it) }
            if (item == null) {
                Bukkit.getConsoleSender()
                    .sendTranslatableComponent(RESOURCE_HOOK_ITEM_NOT_FOUND, config.fileName, "protection-rune", itemID ?: "null")
                isEnabled = false
                return
            }
        }
    }

    override fun reload() = load()

    override fun hasProtectionRune(item: ItemStack): Boolean {
        if (!isEnabled) return false // 如果功能未开启就均视为没有保护.
        RtagItem.of(item).let { tag ->
            return tag.get<String>("FirEnchant", "HasProtection") == "yes"
        }
    }

    override fun removeProtectionRune(item: ItemStack) {
        RtagItem.edit(item) { tag ->
            tag.remove("FirEnchant", "HasProtection")
        }
    }

    override fun addProtectionRune(item: ItemStack) {
        RtagItem.edit(item) { tag ->
            tag.set("yes", "FirEnchant", "HasProtection")
        }
    }

    override fun matches(itemStack: ItemStack): Boolean {
        if (!isEnabled) return false
        return itemProvider!!.getIdByItem(itemStack) == itemID
    }

    override fun onPrepare(
        event: PrepareAnvilEvent,
        context: AnvilContext
    ) {
        if (hasProtectionRune(context.firstItem)) return

        // 经验值
        val costExp = config.PROTECTION_RUNE_EXP

        // 触发事件
        val protectionRunePreUseEvent = ProtectionRunePreUseEvent(context.viewer, event, costExp, context.firstItem)
        Bukkit.getPluginManager().callEvent(protectionRunePreUseEvent)
        if (protectionRunePreUseEvent.isCancelled) return

        // 显示结果
        event.result = context.firstItem.clone().also { addProtectionRune(it) }
        event.view.repairCost = config.PROTECTION_RUNE_EXP
    }

    override fun onCost(
        event: InventoryClickEvent,
        context: AnvilContext
    ) {
        val anvilView = event.view as AnvilView

        // 触发事件
        val protectionRuneUseEvent = ProtectionRuneUseEvent(context.viewer, event, event.view as AnvilView, context.firstItem, context.result!!)
        Bukkit.getPluginManager().callEvent(protectionRuneUseEvent)
        if (protectionRuneUseEvent.isCancelled) {
            event.isCancelled = true
            return
        }

        // TODO 有没有什么不需要取消事件就能处理堆叠物品的方案?
        event.isCancelled = true
        context.viewer.level -= anvilView.repairCost // 扣除经验值
        anvilView.setItem(0, ItemStack.empty())
        anvilView.setItem(2, ItemStack.empty())

        // 扣除一个保护符文
        if (context.secondItem.amount > 1) context.secondItem.amount -= 1
        else anvilView.setItem(1, ItemStack.empty())

        // 设置结果
        anvilView.setCursor(context.result!!)
        context.viewer.playSound(context.viewer.location, "block.anvil.use", 1f, 1f)
    }

}