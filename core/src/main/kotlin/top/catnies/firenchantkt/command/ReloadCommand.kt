package top.catnies.firenchantkt.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import top.catnies.firenchantkt.language.MessageConstants.COMMAND_RELOAD_SUCCESS
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent


/**
 * 插件重载命令
 */
object ReloadCommand : AbstractCommand() {

    // 注册命令
    override fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("reload")
            .requires { requires(it) }
            .executes { execute(it) }
    }

    // 检查执行条件
    override fun requires(requirement: CommandSourceStack): Boolean {
        return requirement.sender.hasPermission("firenchant.command.reload")
    }

    // 执行命令
    override fun execute(context: CommandContext<CommandSourceStack>): Int {
        val start = System.currentTimeMillis()
        plugin.reload()
        val cost = System.currentTimeMillis() - start
        context.source.sender.sendTranslatableComponent(COMMAND_RELOAD_SUCCESS, cost.toString())
        return Command.SINGLE_SUCCESS
    }

}