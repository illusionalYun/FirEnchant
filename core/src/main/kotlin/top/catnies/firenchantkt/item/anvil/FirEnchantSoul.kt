package top.catnies.firenchantkt.item.anvil

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.view.AnvilView
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.api.event.anvilapplicable.EnchantSoulPreUseEvent
import top.catnies.firenchantkt.api.event.anvilapplicable.EnchantSoulUseEvent
import top.catnies.firenchantkt.config.AnvilConfig
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.enchantment.FirEnchantmentSettingFactory
import top.catnies.firenchantkt.integration.FirItemProviderRegistry
import top.catnies.firenchantkt.integration.ItemProvider
import top.catnies.firenchantkt.language.MessageConstants.RESOURCE_HOOK_ITEM_NOT_FOUND
import top.catnies.firenchantkt.language.MessageConstants.RESOURCE_HOOK_ITEM_PROVIDER_NOT_FOUND
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent

class FirEnchantSoul: EnchantSoul {

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
        isEnabled = config.ENCHANT_SOUL_ENABLE
        if (isEnabled) {
            itemProvider = config.ENCHANT_SOUL_ITEM_PROVIDER?.let { FirItemProviderRegistry.instance.getItemProvider(it) }
            if (itemProvider == null) {
                Bukkit.getConsoleSender().sendTranslatableComponent(RESOURCE_HOOK_ITEM_PROVIDER_NOT_FOUND, config.fileName, config.ENCHANT_SOUL_ITEM_PROVIDER ?: "未设置")
                isEnabled = false
                return
            }

            itemID = config.ENCHANT_SOUL_ITEM_ID
            val item = itemID?.let { itemProvider?.getItemById(it) }
            if (item == null) {
                Bukkit.getConsoleSender().sendTranslatableComponent(RESOURCE_HOOK_ITEM_NOT_FOUND, config.fileName, itemID ?: "未设置")
                isEnabled = false
                return
            }
        }
    }

    override fun reload() = load()

    override fun matches(itemStack: ItemStack): Boolean {
        if (!isEnabled) return false
        return itemProvider!!.getIdByItem(itemStack) == itemID
    }

    override fun onPrepare(
        event: PrepareAnvilEvent,
        context: AnvilContext
    ) {
        val setting = FirEnchantmentSettingFactory.fromItemStack(context.firstItem) ?: return
        if (isLowestFailure(setting.failure)) return

        // 计算花费
        val canUseAmount = getCanUseAmount(setting.failure, context.secondItem.amount).also { if (it <= 0) return }
        val costExp = config.ENCHANT_SOUL_EXP * canUseAmount
        val resultSetting = FirEnchantmentSettingFactory.fromAnother(setting).apply { failure = failure - canUseAmount * config.ENCHANT_SOUL_REDUCE_FAILURE }

        // 触发事件
        val useEvent = EnchantSoulPreUseEvent(context.viewer, event, canUseAmount, costExp, resultSetting, context.firstItem)
        if (useEvent.isCancelled) return

        // 显示结果
        val resultItem = useEvent.resultSetting.toItemStack()
        // TODO 夹带私货

        event.result = resultItem
        event.view.repairCost = useEvent.useAmount * config.ENCHANT_SOUL_EXP
    }

    override fun onCost(
        event: InventoryClickEvent,
        context: AnvilContext
    ) {
        val firstSetting = FirEnchantmentSettingFactory.fromItemStack(context.firstItem) ?: return
        val resultSetting = context.result?.let { FirEnchantmentSettingFactory.fromItemStack(it) } ?: return

        // 触发事件
        val useEvent = EnchantSoulUseEvent(context.viewer, event, event.view as AnvilView, context.firstItem, context.result!!)
        Bukkit.getPluginManager().callEvent(useEvent)
        if (useEvent.isCancelled) {
            event.isCancelled = true
            return
        }

        // 计算使用的物品数量

        resultSetting.failure


    }

    // TODO 夹带私货, 把部分数据缓存到物品.
    private fun injectContextData(item: ItemStack) {
        return
    }
    private fun readContextData(item: ItemStack): Map<String, Any> {
        return emptyMap()
    }

    // 检查概率是否到达了最低的附魔书概率
    override fun isLowestFailure(failure: Int): Boolean {
        return failure <= config.ENCHANT_SOUL_MIN_FAILURE
    }

    // 计算当前最多可以使用多少个魔咒之魂
    private fun getCanUseAmount(failure: Int, inputAmount: Int): Int{
        return 0
    }

}