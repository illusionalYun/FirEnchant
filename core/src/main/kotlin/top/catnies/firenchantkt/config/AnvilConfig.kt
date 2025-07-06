package top.catnies.firenchantkt.config


class AnvilConfig private constructor():
    AbstractConfigFile("modules/anvil.yml")
{

    companion object {
        val instance by lazy { AnvilConfig() }
    }

    /*原版附魔书设置*/
    var VANILLAENCHANTEDBOOK_DENY_USE: Boolean = false  // 是否禁用原版附魔书.

    /*附魔书设置*/
    var ENCHANTEDBOOK_USE_EXP_COST_MODE: String = "FIXED"   // 经验值消耗模式
    var ENCHANTEDBOOK_USE_EXP_FIXED_VALUE: Int = 0          // 固定模式的值


    override fun loadConfig() {
        /*原版附魔书设置*/
        VANILLAENCHANTEDBOOK_DENY_USE = config().getBoolean("vanilla-enchanted-book.deny-use", false)

        /*附魔书设置*/
        ENCHANTEDBOOK_USE_EXP_COST_MODE = config().getString("enchanted-book.use-enchanted-book.exp.cost-mode", "FIXED")!!
        ENCHANTEDBOOK_USE_EXP_FIXED_VALUE= config().getInt("enchanted-book.use-enchanted-book.exp.fixed-value", 18)
    }

}
