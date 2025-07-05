package top.catnies.firenchantkt.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands

/**
 * 版本信息.
 */
object VersionCommand: AbstractCommand() {

    // 注册命令
    override fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("version")
            .requires { requires(it) }
            .executes { execute(it) }
    }

    // 检查执行条件
    override fun requires(requirement: CommandSourceStack): Boolean {
        return requirement.sender.hasPermission("firenchant.command.version")
    }

    // 执行命令
    override fun execute(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendRichMessage("<#96f896>[FirEnchant] <#cef8f5>Plugin Version is ${plugin.pluginMeta.version} .")
        return Command.SINGLE_SUCCESS
    }
}