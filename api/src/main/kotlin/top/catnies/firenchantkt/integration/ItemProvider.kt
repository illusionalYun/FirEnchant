package top.catnies.firenchantkt.integration

import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull
import javax.annotation.Nullable

interface ItemProvider {
    val enabled: Boolean

    @Nullable
    fun getItemById(id: String): ItemStack?

    @Nullable
    fun getIdByItem(item: ItemStack): String?

    @NotNull
    override fun toString(): String
}