package top.catnies.firenchantkt.api

import net.kyori.adventure.key.Key
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import top.catnies.firenchantkt.api.ServiceContainer.get
import top.catnies.firenchantkt.enchantment.EnchantmentData
import top.catnies.firenchantkt.enchantment.EnchantmentManager
import top.catnies.firenchantkt.enchantment.EnchantmentSetting
import top.catnies.firenchantkt.enchantment.EnchantmentSettingFactory
import top.catnies.firenchantkt.integration.ItemProviderRegistry
import top.catnies.firenchantkt.item.AnvilItemRegistry
import top.catnies.firenchantkt.item.EnchantingTableItemRegistry


object FirEnchantAPI {

    // 物品提供者注册表
    val itemProviderRegistry: () -> ItemProviderRegistry = { get(ItemProviderRegistry::class.java) }

    // 铁砧物品注册表
    val anvilItemRegistry: () -> AnvilItemRegistry = { get(AnvilItemRegistry::class.java) }

    // 附魔台物品注册表
    val enchantingTableItemRegistry: () -> EnchantingTableItemRegistry = { get(EnchantingTableItemRegistry::class.java) }


    /**
     * 构建一本附魔书.
     * @param key 魔咒的Key, 或者自行构建, 例如 `Key.key("minecraft:lure")`
     * @param level 附魔书魔咒的等级.
     * @param failure 附魔书的失败率.
     * @param consumedSouls 附魔书已经使用的灵魂数量.
     */
    @Nullable
    fun getSettingsByData(@NotNull key: Key, @NotNull level: Int, @NotNull failure: Int, @NotNull consumedSouls: Int = 0): EnchantmentSetting? {
        val data = getEnchantmentData(key) ?: return null
        return ServiceContainer.get(EnchantmentSettingFactory::class.java).fromData(data, level, failure, consumedSouls)
    }
    @Nullable
    fun getSettingsByItemStack(@NotNull item: ItemStack): EnchantmentSetting? {
        return ServiceContainer.get(EnchantmentSettingFactory::class.java).fromItemStack(item)
    }


    /**
     * 获取服务器内已经存在的附魔数据类, 注意, 如果插件reload了, 获取的副本可能会过期.
     * @param key 魔咒的ID, 如 "minecraft:unbreaking"
     */
    @Nullable
    fun getEnchantmentData(@NotNull key: String): EnchantmentData? {
        return ServiceContainer.get(EnchantmentManager::class.java).getEnchantmentData(key)
    }
    @Nullable
    fun getEnchantmentData(@NotNull key: Key): EnchantmentData? {
        return ServiceContainer.get(EnchantmentManager::class.java).getEnchantmentData(key)
    }
    @NotNull
    fun getAllEnchantmentData(): List<EnchantmentData> {
        return ServiceContainer.get(EnchantmentManager::class.java).getAllEnchantmentData()
    }

}