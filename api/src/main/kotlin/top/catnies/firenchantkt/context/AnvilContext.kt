package top.catnies.firenchantkt.context

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class AnvilContext(
    val firstItem: ItemStack,
    val secondItem: ItemStack,
    val result: ItemStack?,
    val viewer: Player,
): Context {

    private val data = mutableMapOf<String, Any>()

    override fun put(key: String, value: Any): Any? {
        return data.put(key, value)
    }

    override fun get(key: String): Any? {
        return data[key]
    }

    override fun remove(key: String): Any? {
        return data.remove(key)
    }

    override fun clear() {
        data.clear()
    }

    override fun getAll(): Map<String, Any> {
        return data.toMap()
    }

}