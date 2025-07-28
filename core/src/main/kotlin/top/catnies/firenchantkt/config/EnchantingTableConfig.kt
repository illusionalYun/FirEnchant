package top.catnies.firenchantkt.config

class EnchantingTableConfig private constructor():
    AbstractConfigFile("modules/enchanting_table.yml")
{
    companion object {
        @JvmStatic
        val instance by lazy { EnchantingTableConfig().apply { loadConfig() } }
    }



    // 加载数据
    override fun loadConfig() {
    }

    // 等待注册表完成后延迟加载的部分
    override fun loadLatePartConfig() {
    }
}