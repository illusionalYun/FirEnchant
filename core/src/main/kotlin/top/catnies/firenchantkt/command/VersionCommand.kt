package top.catnies.firenchantkt.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import top.catnies.firenchantkt.language.MessageConstants.COMMAND_VERSION_SUCCESS
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent

/**
 * 版本信息.
 */
object VersionCommand : AbstractCommand() {

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
        context.source.sender.sendTranslatableComponent(COMMAND_VERSION_SUCCESS, plugin.pluginMeta.version)
        return Command.SINGLE_SUCCESS
    }
}