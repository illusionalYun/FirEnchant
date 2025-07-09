package top.catnies.firenchantkt.util

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.pointer.Pointered
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.translation.Argument


object MessageUtils {
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
}
