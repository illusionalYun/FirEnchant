package top.catnies.firenchantkt.util

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.CustomModelData
import io.papermc.paper.datacomponent.item.DyedItemColor
import io.papermc.paper.datacomponent.item.ItemLore
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.integration.FirItemProviderRegistry

class ItemBuilder private constructor() {
    private var item: ItemStack = ItemStack(Material.STONE)

    companion object {
        fun builder(): ItemBuilder {
            return ItemBuilder();
        }
    }

    fun setItem(item: ItemStack?): ItemBuilder {
        this.item = item ?: return this
        return this
    }

    fun setType(plugin: String, type: String): ItemBuilder {
        val provider = FirItemProviderRegistry.instance.getItemProvider(plugin) ?: return this
        this.setItem(provider.getItemById(type))

        return this;
    }

    fun setDisplayName(name: Component): ItemBuilder {
        this.item.setData(DataComponentTypes.ITEM_NAME, name)
        return this
    }

    fun setLore(lore: List<Component>): ItemBuilder {
        this.item.setData(DataComponentTypes.LORE, ItemLore.lore(lore))
        return this
    }

    fun setCustomModelData(modelId: Float): ItemBuilder {
        this.item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addFloat(modelId).build())
        return this
    }

    fun setItemModel(id: String): ItemBuilder {
        this.item.setData(DataComponentTypes.ITEM_MODEL, Key.key(id))
        return this
    }

//    fun setColor(alpine: Int = 1, red: Int, green: Int, blue: Int, shownInTooltip: Boolean = true): ItemBuilder {
//        this.item.setData(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromARGB(alpine, red, green, blue), shownInTooltip))
//        return this
//    }
//
//    fun setFireworkStarColor(
//        alpine: Int = 1, red: Int, green: Int, blue: Int,
//        fadeAlpine: Int = 1, fadeRed : Int = red, fadeGreen: Int = green, fadeBlue: Int = blue,
//        trail: Boolean = false, twinkle: Boolean = false, shape: FireworkEffect.Type = FireworkEffect.Type.STAR
//    ): ItemBuilder {
//        if (this.item.type != Material.FIREWORK_STAR) return this
//        this.item.setData(DataComponentTypes.FIREWORK_EXPLOSION, FireworkEffect.builder()
//            .withColor(Color.fromARGB(alpine, red, green, blue))
//            .withFade(Color.fromARGB(fadeAlpine, fadeRed, fadeGreen, fadeBlue))
//            .trail(trail)
//            .flicker(twinkle)
//            .with(shape)
//            .build()
//        )
//        return this
//    }

    fun setDurability(value: Int): ItemBuilder {
        this.item.setData(DataComponentTypes.DAMAGE, value)
        return this
    }

    fun setMaxDurability(value: Int): ItemBuilder {
        this.item.setData(DataComponentTypes.MAX_DAMAGE, value)
        return this
    }

    fun setAmount(value: Int): ItemBuilder {
        this.item.amount = value
        return this
    }

    fun setMaxAmount(value: Int): ItemBuilder {
        this.item.setData(DataComponentTypes.MAX_STACK_SIZE, value)
        return this
    }

    fun build(): ItemStack {
        return this.item
    }
}