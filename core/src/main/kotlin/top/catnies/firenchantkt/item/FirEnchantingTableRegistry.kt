package top.catnies.firenchantkt.item

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.api.ServiceContainer
import top.catnies.firenchantkt.api.event.enchantingapplicable.EnchantingApplicableItemRegisterEvent

class FirEnchantingTableRegistry: EnchantingTableItemRegistry {

    private val items = mutableListOf<EnchantingTableApplicable>()

    companion object {
        val instance: FirEnchantingTableRegistry by lazy { FirEnchantingTableRegistry().also {
            println("附魔台尝试注册")
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

    @Suppress("UNCHECKED_CAST")
    override fun <T : EnchantingTableApplicable> getItem(applicableClass: Class<T>): T? {
        return items.find { applicableClass.isInstance(it) } as T?
    }

    override fun getItems(): List<EnchantingTableApplicable> {
        return items.toList()
    }

    override fun findApplicableItem(itemStack: ItemStack): EnchantingTableApplicable? {
        return items.find { it.matches(itemStack) }
    }
}