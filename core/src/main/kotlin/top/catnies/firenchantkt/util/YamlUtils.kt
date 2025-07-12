package top.catnies.firenchantkt.util

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.yaml.snakeyaml.Yaml
import java.io.StringReader

object YamlUtils {

    // 将 ListMap 转换成 List<YamlConfiguration>
    fun ConfigurationSection?.getConfigurationSectionList(key: String) : List<YamlConfiguration> {
        val data: List<*> = this?.getList(key) ?: return ArrayList()
        return data.map { Yaml().dump(it) }
            .map { StringReader(it) }
            .map { YamlConfiguration.loadConfiguration(it) }
    }

}