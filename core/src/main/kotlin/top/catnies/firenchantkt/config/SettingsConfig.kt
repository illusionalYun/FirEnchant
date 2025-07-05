package top.catnies.firenchantkt.config


class SettingsConfig private constructor():
    AbstractConfigFile("settings.yml")
{

    companion object {
        val instance by lazy { SettingsConfig() }
    }


    var LANGUAGE: String = "zh_CN"


    override fun loadConfig() {
        val cfg = getConfig()
        LANGUAGE = cfg.getString("language", "zh_CN")!!
    }

}
