package top.catnies.firenchantkt.item

import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.item.anvil.FirEnchantedBook
import top.catnies.firenchantkt.item.anvil.VanillaEnchantedBook


class FirAnvilItemRegistry: AnvilItemRegistry {

    private val items = mutableListOf<AnvilApplicable>()

    companion object {
        val instance: FirAnvilItemRegistry by lazy { FirAnvilItemRegistry().also {
            it.load()
        } }
    }

    // 初始化注册物品
    fun load() {
        registerItem(VanillaEnchantedBook())
        registerItem(FirEnchantedBook())
    }

    // 注册新的铁砧物品
    override fun registerItem(item: AnvilApplicable): Boolean {
        return items.add(item)
    }

    // 删除铁砧物品
    override fun unregisterItem(item: AnvilApplicable): Boolean {
        return items.remove(item)
    }

    // 获取所有铁砧物品
    override fun getItems(): List<AnvilApplicable> {
        return items.toList()
    }

    // 找到物品对应的处理器
    override fun findApplicableItem(itemStack: ItemStack): AnvilApplicable? {
        return items.find { it.matches(itemStack) }
    }

}