package top.catnies.firenchantkt.context

import org.bukkit.entity.Player
import top.catnies.firenchantkt.gui.EnchantingTableMenu

data class EnchantingTableContext(
    val player: Player,
    var bookShelves: Int,
    val menu: EnchantingTableMenu
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