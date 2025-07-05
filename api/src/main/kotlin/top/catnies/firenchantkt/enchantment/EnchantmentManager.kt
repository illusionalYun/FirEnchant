package top.catnies.firenchantkt.enchantment

import net.kyori.adventure.key.Key

interface EnchantmentManager {

    // 获取附魔数据.
    fun getEnchantmentData(key: String): EnchantmentData?
    fun getEnchantmentData(key: Key): EnchantmentData?

    // 获取全部魔咒包装数据
    fun getAllEnchantmentData(): List<EnchantmentData>

    // 检查服务器上是否存在某个魔咒
    fun hasEnchantment(key: String): Boolean
    fun hasEnchantment(key: Key): Boolean

}