package top.catnies.firenchantkt.util

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.pointer.Pointered
import net.kyori.adventure.text.Component
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
     * @param ptr PAPI 解析者
     * @return 文本组件.
     */
    fun String.renderToComponent(ptr: Pointered? = null): Component{
        val player = ptr as? Player
        val parsed = PlaceholderAPI.setPlaceholders(player, this)
        val miniMessageText = MessageTranslator.legacyToMiniMessage(MessageTranslator.legacyColorToMiniMessage(parsed));
        return miniMessage.deserialize(miniMessageText)
    }

}
