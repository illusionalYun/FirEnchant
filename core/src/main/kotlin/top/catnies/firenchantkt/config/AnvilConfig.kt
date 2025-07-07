package top.catnies.firenchantkt.config

import top.catnies.firenchantkt.integration.FirItemProviderRegistry
import top.catnies.firenchantkt.integration.ItemProvider


@Suppress("PropertyName")
class AnvilConfig private constructor():
    AbstractConfigFile("modules/anvil.yml")
{

    companion object {
        val instance by lazy { AnvilConfig() }
    }

    /*原版附魔书设置*/
    var VEB_DENY_USE: Boolean = false      // 是否禁用原版附魔书.

    /*附魔书设置*/
    lateinit var EB_USE_EXP_COST_MODE: String    // 经验值消耗模式
    var EB_USE_EXP_FIXED_VALUE: Int = 18         // 固定模式的值
    var EB_FAILURE_CORRECTION_MINMAX_ENABLED: Boolean = false    // 最大最小失败率强制控制功能
    var EB_FAILURE_CORRECTION_MINMAX_MIN: Int = 15               // 低于此失败率的附魔书将必定成功
    var EB_FAILURE_CORRECTION_MINMAX_MAX: Int = 90               // 高于此失败率的附魔书将必定失败
    var EB_FAILURE_CORRECTION_HISTORY_ENABLE: Boolean = false       // 附魔书的成功/失败历史记录功能
    lateinit var EB_FAILURE_CORRECTION_HISTORY_CACHE_TYPE: String   // 存储历史记录的方法
    lateinit var EB_MERGE_FAILURE_INHERITANCE: String // 当两本附魔书合并的结果附魔书的失败率处理模式
    lateinit var EB_MERGE_EXP_COST_MODE: String    // 经验值消耗模式
    var EB_MERGE_EXP_FIXED_VALUE: Int = 18         // 固定模式的值

    /*保护符文设置*/
    var PROTECTION_RUNE_ENABLE: Boolean = false             // 开启保护符文道具
    var PROTECTION_RUNE_ITEM_PROVIDER: String? = null       // 保护符文的道具提供者
    var PROTECTION_RUNE_ITEM_ID: String? = null             // 保护符文的道具ID

    /*升级符文设置*/




    override fun loadConfig() {
        /*原版附魔书设置*/
        VEB_DENY_USE = config().getBoolean("vanilla-enchanted-book.deny-use", false)

        /*附魔书设置*/
        EB_USE_EXP_COST_MODE = config().getString("enchanted-book.use-enchanted-book.exp.cost-mode", "FIXED")!!
        EB_USE_EXP_FIXED_VALUE = config().getInt("enchanted-book.use-enchanted-book.exp.fixed-value", 18)
        EB_FAILURE_CORRECTION_MINMAX_ENABLED = config().getBoolean("enchanted-book.failure-correction.min-max-limit.enable", false)
        EB_FAILURE_CORRECTION_MINMAX_MIN = config().getInt("enchanted-book.failure-correction.min-max-limit.min-success", 15)
        EB_FAILURE_CORRECTION_MINMAX_MAX = config().getInt("enchanted-book.failure-correction.min-max-limit.max-failure", 90)
        EB_FAILURE_CORRECTION_HISTORY_ENABLE = config().getBoolean("enchanted-book.failure-correction.history-compensation.enable", false)
        EB_FAILURE_CORRECTION_HISTORY_CACHE_TYPE = config().getString("enchanted-book.failure-correction.history-compensation.cache-type", "PDC")!!
        EB_MERGE_FAILURE_INHERITANCE = config().getString("enchanted-book.use-enchanted-book.failure-inheritance", "DEFAULT")!!
        EB_MERGE_EXP_COST_MODE = config().getString("enchanted-book.use-enchanted-book.exp.cost-mode", "FIXED")!!
        EB_MERGE_EXP_FIXED_VALUE = config().getInt("enchanted-book.use-enchanted-book.exp.fixed-value", 18)

        /*保护符文设置*/
        PROTECTION_RUNE_ENABLE = config().getBoolean("protection-rune.enable", false)
        if (PROTECTION_RUNE_ENABLE) {
            PROTECTION_RUNE_ITEM_PROVIDER = config().getString("protection-rune.hooked-plugin")
            PROTECTION_RUNE_ITEM_ID = config().getString("protection-rune.hooked-id")
        }


        /*升级符文设置*/
    }

}
