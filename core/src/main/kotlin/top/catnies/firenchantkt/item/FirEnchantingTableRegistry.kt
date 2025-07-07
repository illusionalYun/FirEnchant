package top.catnies.firenchantkt.item

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.api.ServiceContainer
import top.catnies.firenchantkt.api.event.EnchantingApplicableItemRegisterEvent

class FirEnchantingTableRegistry: EnchantingTableItemRegistry {

    private val items = mutableListOf<EnchantingTableApplicable>()

    companion object {
        val instance: FirEnchantingTableRegistry by lazy { FirEnchantingTableRegistry().also {
            it.load()
        } }
    }

    fun load() {
        ServiceContainer.register(EnchantingTableItemRegistry::class.java, this)
        Bukkit.getPluginManager().callEvent(EnchantingApplicableItemRegisterEvent(this))
    }

    fun reload() {
        items.forEach { it.reload() }
    }

    override fun registerItem(item: EnchantingTableApplicable): Boolean {
        return items.add(item)
    }

    override fun unregisterItem(item: EnchantingTableApplicable): Boolean {
        return items.remove(item)
    }

    override fun getItems(): List<EnchantingTableApplicable> {
        return items.toList()
    }

    override fun findApplicableItem(itemStack: ItemStack): EnchantingTableApplicable? {
        return items.find { it.matches(itemStack) }
    }
}