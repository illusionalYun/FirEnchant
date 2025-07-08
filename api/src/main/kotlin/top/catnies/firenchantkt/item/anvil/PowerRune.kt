package top.catnies.firenchantkt.item.anvil

import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.item.AnvilApplicable

interface PowerRune: AnvilApplicable {

    // 获取升级符文的成功率
    fun getChance(item: ItemStack): Int

    // 检查一件物品能不能使用升级符文升级
    fun canUpgrade(item: ItemStack): Boolean

    // 随机升级物品上的一个可升级魔咒, 如果没有可升级的, 则不会有变化.
    fun upgrade(item: ItemStack)

}