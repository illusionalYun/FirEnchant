package top.catnies.firenchantkt.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import top.catnies.firenchantkt.FirEnchantPlugin

abstract class AbstractCommand {
    val plugin get() = FirEnchantPlugin.instance
    val logger get() = plugin.logger

    // 创建方法
    abstract fun create(): LiteralArgumentBuilder<CommandSourceStack>
    // 检查执行条件
    abstract fun requires(requirement: CommandSourceStack): Boolean
    // 执行方法
    abstract fun execute(context: CommandContext<CommandSourceStack>): Int
}