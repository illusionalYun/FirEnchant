package top.catnies.firenchantkt.config


@Suppress("PropertyName")
class AnvilConfig private constructor():
    AbstractConfigFile("modules/anvil.yml")
{

    companion object {
        @JvmStatic
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

    /*魔咒之魂设置*/
    var ENCHANT_SOUL_ENABLE: Boolean = false             // 开启魔咒之魂道具
    var ENCHANT_SOUL_ITEM_PROVIDER: String? = null       // 魔咒之魂的道具提供者
    var ENCHANT_SOUL_ITEM_ID: String? = null             // 魔咒之魂的道具ID
    var ENCHANT_SOUL_EXP: Int = 3                        // 魔咒之魂消耗的经验等级
    var ENCHANT_SOUL_REDUCE_FAILURE: Int = 3             // 每个魔咒之魂可降低附魔书的失败率
    var ENCHANT_SOUL_MIN_FAILURE: Int = 5                // 附魔书最多可以使用魔咒之魂降低到的失败率
    var ENCHANT_SOUL_MAX_USE_SOULS: Int = 12             // 附魔书最多可以使用的魔咒之魂数量

    /*保护符文设置*/
    var PROTECTION_RUNE_ENABLE: Boolean = false             // 开启保护符文道具
    var PROTECTION_RUNE_ITEM_PROVIDER: String? = null       // 保护符文的道具提供者
    var PROTECTION_RUNE_ITEM_ID: String? = null             // 保护符文的道具ID
    var PROTECTION_RUNE_EXP: Int = 18                       // 保护符文消耗的经验等级

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

        /*魔咒之魂设置*/
        ENCHANT_SOUL_ENABLE = config().getBoolean("enchant-soul.enable", false)
        if (PROTECTION_RUNE_ENABLE) {
            ENCHANT_SOUL_ITEM_PROVIDER = config().getString("enchant-soul.hooked-plugin")!!
            ENCHANT_SOUL_ITEM_ID = config().getString("enchant-soul.hooked-id")!!
            ENCHANT_SOUL_EXP = config().getInt("enchant-soul.exp", 3)
            ENCHANT_SOUL_REDUCE_FAILURE = config().getInt("enchant-soul.reduce-failure", 3)
            ENCHANT_SOUL_MIN_FAILURE = config().getInt("enchant-soul.min-failure", 5)
            ENCHANT_SOUL_MAX_USE_SOULS = config().getInt("enchant-soul.max-use-souls", 12)
        }

        /*保护符文设置*/
        PROTECTION_RUNE_ENABLE = config().getBoolean("protection-rune.enable", false)
        if (PROTECTION_RUNE_ENABLE) {
            PROTECTION_RUNE_ITEM_PROVIDER = config().getString("protection-rune.hooked-plugin")!!
            PROTECTION_RUNE_ITEM_ID = config().getString("protection-rune.hooked-id")!!
            PROTECTION_RUNE_EXP = config().getInt("protection-rune.exp", 18)
        }

        /*升级符文设置*/
    }

}
