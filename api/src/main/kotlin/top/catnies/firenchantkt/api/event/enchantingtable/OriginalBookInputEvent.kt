package top.catnies.firenchantkt.api.event.enchantingtable

import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.enchantment.EnchantmentSetting

/**
 * 当将可附魔的附魔书放入附魔台时触发
 */
class OriginalBookInputEvent(
    player: Player,
    val inputItem: ItemStack,
    // 记录使用这个物品附魔时, 可能出现的所有魔咒.
    val allEnchantment: Set<Enchantment>,
    // 至少1个, 最多3个, 记录附魔台中出现的3个魔咒
    var enchantingTableEnchantments: List<EnchantmentSetting>
) : PlayerEvent(player) {

    companion object {
        val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLER_LIST
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }
}