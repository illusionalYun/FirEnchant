package top.catnies.firenchantkt.enchantment

import com.saicone.rtag.RtagItem
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.enchantment.EnchantmentSettingFactory

// 附魔配置工厂类
object FirEnchantmentSettingFactoryImpl: EnchantmentSettingFactory {

    // 将物品转换成一个 FirEnchantmentSetting 对象.
    override fun fromItemStack(item: ItemStack): EnchantmentSetting? {
        val tag = RtagItem.of(item)
        val enchantmentKey: String = tag.get("FirEnchant", "Enchantment") ?: return null
        val data = FirEnchantmentManager.instance.getEnchantmentData(enchantmentKey) ?: return null
        val level: Int = tag.get("FirEnchant", "Level") ?: return null
        val failure: Int = tag.get("FirEnchant", "Failure") ?: return null
        val usedDustTime: Int = tag.get("FirEnchant", "DustTimes") ?: 0
        return FirEnchantmentSetting(data, level, failure, usedDustTime)
    }


    // 使用数据构建一个 FirEnchantmentSetting 对象.
    override fun fromData(data: EnchantmentData, level: Int, failure: Int, usedDustTime: Int): EnchantmentSetting {
        return FirEnchantmentSetting(data, level, failure, usedDustTime)
    }
}