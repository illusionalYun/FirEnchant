package top.catnies.firenchantkt.config

import org.bukkit.configuration.file.YamlConfiguration
import top.catnies.firenchantkt.FirEnchantPlugin
import java.io.File
import kotlin.properties.Delegates

abstract class AbstractConfigFile(
    private val fileName: String
) {
    protected val plugin get() = FirEnchantPlugin.instance
    protected val logger get() = plugin.logger

    private var config: YamlConfiguration by Delegates.notNull()

    init {
        initFile()
        loadConfig()
    }

    // 初始化文件
    private fun initFile() {
        val file = File(plugin.dataFolder, fileName)
        if (!file.exists()) {
            plugin.saveResource(fileName, false)
        }
        config = YamlConfiguration.loadConfiguration(file)
    }

    // 安全获取属性
    protected fun config() = config

    // 子类实现
    protected abstract fun loadConfig()

    // 重新加载配置
    fun reload() {
        initFile()
        loadConfig()
    }
}