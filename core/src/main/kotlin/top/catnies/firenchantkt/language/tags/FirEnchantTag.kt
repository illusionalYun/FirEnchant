package top.catnies.firenchantkt.language.tags

import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import top.catnies.firenchantkt.enchantment.EnchantmentData

class FirEnchantTag(
    val enchantmentData: EnchantmentData
): TagResolver {

    override fun has(name: String) = "firenchant" == name

    override fun resolve(
        name: String,
        arguments: ArgumentQueue,
        ctx: Context
    ): Tag? {
        // 判断是否是解析标签
        if (!has(name)) { return null }
        // 获取参数尝试解析
        val rawArgument = arguments.popOr("No argument provided").toString()
        // TODO 根据data解析标签
        return null
    }

}