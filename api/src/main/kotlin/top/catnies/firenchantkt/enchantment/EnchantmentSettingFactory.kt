package top.catnies.firenchantkt.enchantment

import org.bukkit.inventory.ItemStack

// 附魔书工厂
interface EnchantmentSettingFactory {

    // 将物品转换成一个附魔书对象
    fun fromItemStack(item: ItemStack): EnchantmentSetting?

    // 使用数据构建附魔书对象
    fun fromData(data: EnchantmentData, level: Int, failure: Int, usedDustTime: Int = 0): EnchantmentSetting

    // 复制一份对象
    fun fromAnother(setting: EnchantmentSetting): EnchantmentSetting

}