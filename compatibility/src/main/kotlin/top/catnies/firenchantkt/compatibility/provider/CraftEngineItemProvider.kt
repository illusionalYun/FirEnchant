package top.catnies.firenchantkt.compatibility.provider

import net.momirealms.craftengine.bukkit.api.CraftEngineItems
import net.momirealms.craftengine.core.util.Key
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.integration.ItemProvider

class CraftEngineItemProvider private constructor(
    override val enabled: Boolean
): ItemProvider {
    constructor(): this(Bukkit.getPluginManager().isPluginEnabled("CraftEngine"))

    override fun getItemById(id: String): ItemStack? {
        return CraftEngineItems.byId(Key.of(id))?.buildItemStack()
    }

    override fun getIdByItem(item: ItemStack): String? {
        return CraftEngineItems.byItemStack(item)?.id().toString()
    }

    override fun toString(): String {
        return "CraftEngine"
    }
}