package top.catnies.firenchantkt.enchantment

import com.saicone.rtag.RtagItem
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir

class FirEnchantmentSetting(
    override var data: EnchantmentData,
    override var level: Int,
    override var failure: Int,
    override var consumedSouls: Int = 0
): EnchantmentSetting {

    override fun toItemStack(): ItemStack {
        // 查找缓存获取物品
        if (data.cacheItem?.nullOrAir() == true) {
            val itemProvider = data.itemProvider
            data.cacheItem = itemProvider.getItemById(data.hookedID) ?:
                    throw IllegalArgumentException("Can't found item ${data.hookedID} from plugin ${data.itemProvider}!")
        }
        var result = data.cacheItem!!
        result = injectCustomData(result)
        result = replacePlaceholder(result)
        return result
    }

    // 注入自定义数据
    private fun injectCustomData(item: ItemStack): ItemStack {
        RtagItem.of(item). let { tag ->
            tag.set(data.key.toString(), "FirEnchant", "Enchantment")
            tag.set(level.toString(), "FirEnchant", "Level")
            tag.set(failure.toString(), "FirEnchant", "Failure")
            tag.set(consumedSouls.toString(), "FirEnchant", "consumedSouls")
            return tag.load()
        }
    }

    // 替换Name和Lore的Placeholder
    private fun replacePlaceholder(item: ItemStack): ItemStack {
        val rawName = data.itemName
        val rawLore = data.itemLore
        // TODO
        return item
    }

}