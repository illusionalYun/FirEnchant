package top.catnies.firenchantkt.compatibility.provider

import io.lumine.mythic.bukkit.MythicBukkit
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.integration.ItemProvider

class MythicMobsItemProvider private constructor(
    override val enabled: Boolean
): ItemProvider {
    constructor(): this(Bukkit.getPluginManager().isPluginEnabled("MythicMobs"))

    var mythicBukkit: MythicBukkit? = null

    override fun getItemById(id: String): ItemStack? {
        if (mythicBukkit == null || mythicBukkit?.isClosed == true) this.mythicBukkit = MythicBukkit.inst()
        return mythicBukkit?.itemManager?.getItemStack(id);
    }

    override fun getIdByItem(item: ItemStack): String? {
        if (mythicBukkit == null || mythicBukkit?.isClosed == true) this.mythicBukkit = MythicBukkit.inst()
        return mythicBukkit?.itemManager?.getMythicTypeFromItem(item);
    }

    override fun toString(): String {
        return "MythicMobs"
    }

}