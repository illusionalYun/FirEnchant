package top.catnies.firenchantkt.compatibility.enchantmentslots

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import top.catnies.firenchantkt.api.event.AnvilApplicableItemRegisterEvent
import top.catnies.firenchantkt.api.event.PreEnchantedBookUseEvent

class EnchantmentSlotsListener: Listener {

    // 注册附魔槽位相关物品
    @EventHandler
    fun onAnvilItemRegister(event: AnvilApplicableItemRegisterEvent) {
        event.register.registerItem(SlotRune())
    }

    // 监听使用附魔书事件, 如果槽位不够则不处理
    @EventHandler
    fun onPreEnchantedBookUseEvent(event: PreEnchantedBookUseEvent) {
        val remainingSlots = EnchantmentSlotsUtil.getRemainingSlots(event.player, event.firstItem)
        if (remainingSlots <= 0) {
            event.isCancelled = true
        }
    }

}