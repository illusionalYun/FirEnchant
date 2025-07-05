package top.catnies.firenchantkt.config


class AnvilConfig private constructor():
    AbstractConfigFile("modules/anvil.yml")
{

    companion object {
        val instance by lazy { AnvilConfig() }
    }

    // 是否禁用原版附魔书.
    var isDenyVanillaEnchantedBook: Boolean = false


    override fun loadConfig() {
        val cfg = config()
        isDenyVanillaEnchantedBook = cfg.getBoolean("vanilla-enchanted-book.deny-use", false)
    }

}
