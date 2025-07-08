package top.catnies.firenchantkt.enchantment

import com.saicone.rtag.RtagItem
import io.papermc.paper.datacomponent.DataComponentTypes
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
                    // 不应出现, 在 load 时就检查过.
                    throw IllegalArgumentException("Can't found item ${data.hookedID} from plugin ${data.itemProvider}!")
        }
        var result = data.cacheItem!!
        result = injectCustomData(result)
        result = replacePlaceholder(result)
        return result
    }

    // 注入自定义数据
    @Suppress("UnstableApiUsage")
    private fun injectCustomData(item: ItemStack): ItemStack {
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1)
        RtagItem.of(item). let { tag ->
            tag.set(data.key.asString(), "FirEnchant", "Enchantment")
            tag.set(level, "FirEnchant", "Level")
            tag.set(failure, "FirEnchant", "Failure")
            tag.set(consumedSouls, "FirEnchant", "consumedSouls")
            return tag.load()
        }
    }

    // 替换Name和Lore的Placeholder
    private fun replacePlaceholder(item: ItemStack): ItemStack {
        val rawName = data.itemName
        val rawLore = data.itemLore
        // TODO 通过自定义 TagResolver 解析.
        return item
    }

}