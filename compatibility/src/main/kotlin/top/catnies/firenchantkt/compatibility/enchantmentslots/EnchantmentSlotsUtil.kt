package top.catnies.firenchantkt.compatibility.enchantmentslots

import cn.superiormc.enchantmentslots.managers.ConfigManager
import com.saicone.rtag.RtagItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object EnchantmentSlotsUtil {

    /**
     * 获取物品的最大附魔槽位数量.
     * @param player 目标玩家, 不同权限组的默认数量可能会不一致.
     * @param targetItem 目标物品.
     * @return 最大附魔槽数量.
     */
    fun getMaxEnchantmentSlots(player: Player?, targetItem: ItemStack?): Int {
        return ConfigManager.configManager.getMaxLimits(targetItem, player)
    }

    /**
     * 获取物品的默认附魔槽位数量.
     * @param player 目标玩家
     * @param targetItem 目标物品.
     * @return 默认附魔槽数量.
     */
    fun getDefaultEnchantmentSlots(player: Player?, targetItem: ItemStack?): Int {
        return ConfigManager.configManager.getDefaultLimits(targetItem, player)
    }

    /**
     * 获取物品当前的附魔槽位
     * @param player 目标玩家
     * @param targetItem 目标物品
     * @return 附魔槽数量
     */
    fun getCurrentEnchantmentSlotCount(player: Player?, targetItem: ItemStack?): Int {
        return RtagItem(targetItem)
            .getOptional("PublicBukkitValues", "enchantmentslots:enchantment_slots")
            .or(getDefaultEnchantmentSlots(player, targetItem))
    }

    /**
     * 检查物品是否允许附魔, 注意, 这不会检查附魔槽位数量是否足够, 只是检查是否在允许附魔的白名单内.
     * @param targetItem 目标物品
     * @return 返回值
     */
    fun canEnchant(targetItem: ItemStack?): Boolean {
        return !ConfigManager.configManager.isIgnore(targetItem)
    }

    /**
     * 获取物品剩余的附魔槽位数量
     * @param targetItem 目标物品
     * @return 剩余槽位数量
     */
    fun getRemainingSlots(player: Player?, targetItem: ItemStack): Int {
        val used = targetItem.enchantments.size
        return getCurrentEnchantmentSlotCount(player, targetItem) - used
    }

    /**
     * 设置物品的附魔槽位数量
     */
    fun setEnchantmentSlots(targetItem: ItemStack, slots: Int) {
        RtagItem.edit(targetItem){
            it.set(slots, "PublicBukkitValues", "enchantmentslots:enchantment_slots")
        }
    }
}