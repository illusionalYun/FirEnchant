package top.catnies.firenchantkt.item.anvil

import top.catnies.firenchantkt.enchantment.EnchantmentSetting
import top.catnies.firenchantkt.item.AnvilApplicable

interface EnchantSoul: AnvilApplicable {

    /**
     * 检查概率是否已经达到的减少到的最低的概率
     */
    fun isLowestFailure(failure: Int): Boolean

    /**
     * 检查附魔书配置对象, 其还能使用多少个魔咒之魂
     */
    fun getReamingCanUse(setting: EnchantmentSetting): Int


}