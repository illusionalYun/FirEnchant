package top.catnies.firenchantkt.compatibility.provider

import com.nexomc.nexo.api.NexoItems
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.integration.ItemProvider
import javax.annotation.Nullable

class NexoItemProvider private constructor(
    override val enabled: Boolean
): ItemProvider {
    constructor(): this(Bukkit.getPluginManager().isPluginEnabled("Nexo"))

    @Nullable
    override fun getItemById(id: String): ItemStack? {
        return NexoItems.itemFromId(id)?.build()
    }

    @Nullable
    override fun getIdByItem(item: ItemStack): String? {
        return NexoItems.idFromItem(item)
    }

    override fun toString(): String {
        return "Nexo"
    }
}