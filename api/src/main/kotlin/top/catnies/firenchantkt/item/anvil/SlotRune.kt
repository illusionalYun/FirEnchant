package top.catnies.firenchantkt.item.anvil

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull
import top.catnies.firenchantkt.item.AnvilApplicable

interface SlotRune: AnvilApplicable {

    // 检查物品还剩下多少个附魔槽位可使用.
    @NotNull
    fun getReamingSlots(player: Player, item: ItemStack): Int

    // 获取物品现在的附魔槽位数量
    @NotNull
    fun getEnchantmentSlots(player: Player, item: ItemStack): Int

    // 获取物品最大的附魔槽位数量
    @NotNull
    fun getMaxEnchantmentSlots(player: Player, item: ItemStack): Int

    // 设置物品的附魔槽位(不会检查最大数量)
    fun setEnchantmentSlots(item: ItemStack, amount: Int)

}