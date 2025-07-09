package top.catnies.firenchantkt.compatibility.enchantmentslots

import com.saicone.rtag.RtagItem
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.view.AnvilView
import top.catnies.firenchantkt.api.event.anvilapplicable.SlotRunePreUseEvent
import top.catnies.firenchantkt.api.event.anvilapplicable.SlotRuneUseEvent
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.integration.ItemProvider
import top.catnies.firenchantkt.item.anvil.SlotRune
import kotlin.math.min

// 拓展符文
class SlotRuneImpl: SlotRune {

    companion object {
        var isEnabled = false
        var costExp = 18
        var itemProvider: ItemProvider? = null
        var itemID: String? = null
        var delegatesLoader: EnchantmentSlotsLoader? = null
    }

    override fun load() {
        delegatesLoader?.initSlotRuneImpl() ?: throw IllegalStateException("EnchantmentSlotLoader is not initialized")
    }

    override fun reload() {
        delegatesLoader?.initSlotRuneImpl() ?: throw IllegalStateException("EnchantmentSlotLoader is not initialized")
    }

    override fun matches(itemStack: ItemStack): Boolean {
        if (!isEnabled) return false
        if (itemProvider?.getIdByItem(itemStack) == itemID) return true
        return false
    }

    override fun onPrepare(
        event: PrepareAnvilEvent,
        context: AnvilContext
    ) {
        if (getReamingSlots(context.viewer, context.firstItem) <= 0) return
        val slotCount = EnchantmentSlotsUtil.getCurrentEnchantmentSlotCount(context.viewer, context.firstItem)
        val maxSlotCount = EnchantmentSlotsUtil.getMaxEnchantmentSlots(context.viewer, context.firstItem)
        val useAmount = getCanUseAmount(slotCount, maxSlotCount, context.secondItem.amount).takeIf { it > 0 } ?: return

        // 事件
        val preUseEvent = SlotRunePreUseEvent(context.viewer, event, useAmount * costExp, slotCount, slotCount + useAmount, useAmount, context.firstItem)
        Bukkit.getPluginManager().callEvent(preUseEvent)
        if (preUseEvent.isCancelled) return

        // 展示结果
        val resultItem = context.firstItem.clone()
        injectContextData(resultItem, useAmount)
        setEnchantmentSlots(resultItem, preUseEvent.targetSlots)
        event.result = resultItem
        event.view.repairCost = preUseEvent.costExp
    }

    override fun onCost(event: InventoryClickEvent, context: AnvilContext) {
        val usedAmount = context.result?.let { readAndClearContextData(it) }?.takeIf { it != -1 } ?: return
        val anvilView = event.view as AnvilView

        // 事件
        val useEvent = SlotRuneUseEvent(context.viewer, event, anvilView, context.firstItem, usedAmount, context.result!!)
        Bukkit.getPluginManager().callEvent(useEvent)
        if (useEvent.isCancelled) {
            event.isCancelled = true
            return
        }

        event.isCancelled = true
        context.viewer.level -= anvilView.repairCost // 扣除经验值
        anvilView.setItem(0, ItemStack.empty())
        anvilView.setItem(2, ItemStack.empty())

        // 计算使用的物品数量
        val backItem = context.secondItem.clone()
        val resultAmount = backItem.amount - useEvent.usedAmount
        if (resultAmount <= 0) anvilView.setItem(1, ItemStack.empty())
        else anvilView.setItem(1, backItem.apply { amount = resultAmount })

        // 光标给物品
        anvilView.setCursor(useEvent.resultItem)
        context.viewer.playSound(context.viewer.location, "block.anvil.use", 1f, 1f)
    }

    private fun getCanUseAmount(currentSlots: Int, maxSlots: Int, inputAmount: Int): Int {
        val reaming = (maxSlots - currentSlots).takeIf { it >= 0 } ?: return 0
        return min(inputAmount, reaming)
    }

    override fun getReamingSlots(player: Player, item: ItemStack) = EnchantmentSlotsUtil.getRemainingSlots(player, item)
    override fun getEnchantmentSlots(player: Player, item: ItemStack) = EnchantmentSlotsUtil.getCurrentEnchantmentSlotCount(player, item)
    override fun getMaxEnchantmentSlots(player: Player, item: ItemStack) = EnchantmentSlotsUtil.getMaxEnchantmentSlots(player, item)
    override fun setEnchantmentSlots(item: ItemStack, amount: Int) = EnchantmentSlotsUtil.setEnchantmentSlots(item, amount)

    // 夹带私货, 把部分数据缓存到物品.
    private fun injectContextData(item: ItemStack, useAmount: Int) {
        RtagItem.edit(item) { tag ->
            tag.set(useAmount, "FirEnchantTempData", "CostSlotRuneAmount")
        }
    }
    private fun readAndClearContextData(item: ItemStack): Int {
        return RtagItem.of(item).get<Int>("FirEnchantTempData", "CostSlotRuneAmount")?.also {
            RtagItem.edit(item) { tag ->
                tag.remove("FirEnchantTempData")
            }
        } ?: -1
    }
}