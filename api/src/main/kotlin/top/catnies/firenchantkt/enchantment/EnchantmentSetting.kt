package top.catnies.firenchantkt.enchantment

import org.bukkit.inventory.ItemStack

interface EnchantmentSetting {

    var data: EnchantmentData
    var level: Int
    var failure: Int
    var consumedSouls: Int

    // 将配置对象转换成附魔书物品.
    fun toItemStack(): ItemStack

}