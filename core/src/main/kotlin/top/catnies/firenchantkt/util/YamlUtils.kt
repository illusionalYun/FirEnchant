package top.catnies.firenchantkt.util

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.yaml.snakeyaml.Yaml
import java.io.StringReader

object YamlUtils {
    fun ConfigurationSection?.getConfigurationSectionList(key: String) : List<YamlConfiguration> {
        val data: List<*> = this?.getList(key) ?: return ArrayList()

        val yaml = Yaml()
        return data
            .map(yaml::dump)
            .map { s: String? -> StringReader(s!!) }
            .map { reader: StringReader? -> YamlConfiguration.loadConfiguration(reader!!) }
            .toList()
    }
}