package top.catnies.firenchantkt.util

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.pointer.Pointered
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.translation.Argument
import org.bukkit.entity.Player
import top.catnies.firenchantkt.language.MessageTranslator
import top.catnies.firenchantkt.language.tags.PlaceholderTag


object MessageUtils {

    val miniMessage = MiniMessage.builder()
        .tags(TagResolver.builder()
            .resolver(TagResolver.standard())
            .resolver(PlaceholderTag) // Papi标签解析器
            .build()
    ).build()

    /**
     * 发送一个可翻译组件给目标.
     *
     * @param key 翻译键
     * @param ptr 上下文解析目标
     * @param args 消息参数
     */
    fun Audience.sendTranslatableComponent(key: String, ptr: Pointered, vararg args: Any) {
        val component = Component.translatable(
            key,
            Argument.target(ptr),
            *args.map {
                when (it) {
                    is String -> Component.text(it)
                    is Component -> it
                    else -> Component.text(it.toString())
                }
            }.toTypedArray()
        )
        this.sendMessage(component)
    }

    fun Audience.sendTranslatableComponent(key: String, vararg args: Any) {
        this.sendTranslatableComponent(key, this, *args)
    }


    /**
     * 将字符串解析成组件, 自动处理 PlaceholderAPI 和 LegacyColor.
     * 同时还可以传入运行时参数进行解析.
     * @param ptr PAPI 解析者
     * @return 文本组件.
     */
    fun String.renderToComponent(ptr: Pointered? = null, args: Map<String, String> = mutableMapOf()): Component{
        val parsedRuntimePlaceholders = this.replacePlaceholders(args)
        val player = ptr as? Player
        val parsedPlaceholderAPI = PlaceholderAPI.setPlaceholders(player, parsedRuntimePlaceholders)
        val miniMessageText = MessageTranslator.legacyToMiniMessage(MessageTranslator.legacyColorToMiniMessage(parsedPlaceholderAPI));
        return miniMessage.deserialize(miniMessageText)
    }

    // 递归替换 Component 中的占位符
    fun Component.replacePlaceholders(args: Map<String, String>): Component {
        return when (this) {
            is TextComponent -> {
                // 获取文本内容并替换占位符
                val content = this.content().replacePlaceholders(args)
                // 创建新的 TextComponent
                Component.text(content)
                    .style(this.style()) // 保留原有样式
                    .children(this.children().map { it.replacePlaceholders(args) }) // 递归处理子组件
            }
            else -> {
                // 对于其他类型的组件，只处理子组件
                this.children(this.children().map { it.replacePlaceholders(args) })
            }
        }
    }

    // 替换String中的占位符
    fun String.replacePlaceholders(args: Map<String, String>): String {
        // 依次对args的每个entry进行处理.
        return args.entries.fold(this) {acc, (key, value) -> acc.replace("\${$key}", value) }
    }

    // 替换String列表中的占位符
    fun List<String>.replacePlaceholders(list: List<String>, args: Map<String, String>): List<String> {
        return list.map { it.replacePlaceholders(args) }
    }

}
