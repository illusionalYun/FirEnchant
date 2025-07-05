package top.catnies.firenchantkt.language

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslator
import top.catnies.firenchantkt.language.tags.PlaceholderTag
import java.util.*


object MessageTranslator : MiniMessageTranslator(
    MiniMessage.builder().tags(
        TagResolver.builder()
            .resolver(TagResolver.standard())
            .resolver(PlaceholderTag) // Papi标签解析器
            .build()
    ).build()
) {
    val key = Key.key("firenchant:lang")
    val translations = mutableMapOf<String, MutableMap<Locale, String>>()

    // 处理传统颜色代码 (&0-&f)
    val legacyMap = mapOf(
        "&0" to "<black>",
        "&1" to "<dark_blue>",
        "&2" to "<dark_green>",
        "&3" to "<dark_aqua>",
        "&4" to "<dark_red>",
        "&5" to "<dark_purple>",
        "&6" to "<gold>",
        "&7" to "<gray>",
        "&8" to "<dark_gray>",
        "&9" to "<blue>",
        "&a" to "<green>",
        "&b" to "<aqua>",
        "&c" to "<red>",
        "&d" to "<light_purple>",
        "&e" to "<yellow>",
        "&f" to "<white>",

        "&k" to "<obfuscated>",
        "&l" to "<bold>",
        "&m" to "<strikethrough>",
        "&n" to "<underlined>",
        "&o" to "<italic>",
        "&r" to "<reset>"
    )


    override fun name() = key

    // 从存储中寻找语言值
    override fun getMiniMessageString(key: String, locale: Locale): String? {
        var originText = translations[key]?.get(locale) ?: return null
        originText = PlaceholderAPI.setPlaceholders(null, originText) // 预处理一下
        return legacyConvert(originText)
    }

    // 将语言里的传统颜色符号转成最新的, 并且解析Placeholder.
    fun legacyConvert(content: String): String {
        if (!content.contains("&")) return content
//        val textComponent = LegacyComponentSerializer.legacySection().deserialize(content)
//        val result =  MiniMessage.miniMessage().serialize(textComponent)

        var result = content
        // 处理 &x 开头的十六进制颜色代码 (格式: &x&R&R&G&G&B&B)
        val hexPattern = Regex("&x(&[0-9a-fA-F]){6}")
        result = hexPattern.replace(result) { matchResult ->
            val hex = matchResult.groupValues.drop(1).joinToString("")
            "<#$hex>"
        }

        // 替换传统颜色代码
        legacyMap.forEach { (legacy, mini) ->
            result = result.replace(legacy, mini)
        }

//        println("完成替换 $result")
        return result
    }

    fun addTranslation(key: String, locale: Locale, message: String) { translations.getOrPut(key) { mutableMapOf() }[locale] = message }
    fun removeTranslation(key: String) = translations.remove(key)
    fun clearTranslations() = translations.clear()
}