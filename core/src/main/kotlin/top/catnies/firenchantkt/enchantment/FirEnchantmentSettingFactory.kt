package top.catnies.firenchantkt.enchantment

import com.saicone.rtag.RtagItem
import org.bukkit.inventory.ItemStack

// 附魔配置工厂类
object FirEnchantmentSettingFactory: EnchantmentSettingFactory {

    // 将物品转换成一个 FirEnchantmentSetting 对象.
    override fun fromItemStack(item: ItemStack): EnchantmentSetting? {
        val tag = RtagItem.of(item)
        val enchantmentKey: String = tag.get("FirEnchant", "Enchantment") ?: return null
        val data = FirEnchantmentManager.instance.getEnchantmentData(enchantmentKey) ?: return null
        // TODO 旧版本是String, 这里需要处理兼容旧版本.
        val level: Int = tag.get("FirEnchant", "Level") ?: return null
        val failure: Int = tag.get("FirEnchant", "Failure") ?: return null
        val usedDustTime: Int = tag.get("FirEnchant", "DustTimes") ?: 0
        return FirEnchantmentSetting(data, level, failure, usedDustTime)
    }


    // 使用数据构建一个 FirEnchantmentSetting 对象.
    override fun fromData(data: EnchantmentData, level: Int, failure: Int, usedDustTime: Int): EnchantmentSetting {
        return FirEnchantmentSetting(data, level, failure, usedDustTime)
    }

    // 复制一份对象
    override fun fromAnother(setting: EnchantmentSetting): EnchantmentSetting = fromData(
        setting.data, setting.level, setting.failure, setting.consumedSouls
    )
}