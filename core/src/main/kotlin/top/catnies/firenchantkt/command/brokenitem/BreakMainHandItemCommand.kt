package top.catnies.firenchantkt.command.brokenitem

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import org.bukkit.entity.Player
import top.catnies.firenchantkt.api.FirEnchantAPI
import top.catnies.firenchantkt.command.AbstractCommand
import top.catnies.firenchantkt.config.RepairTableConfig
import top.catnies.firenchantkt.language.MessageConstants.COMMAND_BROKEN_GEAR_BREAK_MAIN_HAND_FAIL
import top.catnies.firenchantkt.language.MessageConstants.COMMAND_BROKEN_GEAR_BREAK_MAIN_HAND_SUCCESS
import top.catnies.firenchantkt.language.MessageConstants.COMMAND_CONSOLE_CANT_EXECUTE
import top.catnies.firenchantkt.language.MessageConstants.PLUGIN_FUNCTION_NOT_ENABLED
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent

object BreakMainHandItemCommand: AbstractCommand() {

    override fun create(): LiteralArgumentBuilder<CommandSourceStack> =
        Commands.literal("breakMianHandItem")
            .executes { FixMainHandItemCommand.execute(it) }
            .then(Commands.argument("player", ArgumentTypes.player())
                .executes { execute(it) }
            )

    override fun requires(requirement: CommandSourceStack): Boolean {
        return true
    }

    override fun execute(context: CommandContext<CommandSourceStack>): Int {
        // 功能未开启或配置文件存在异常
        if (!RepairTableConfig.instance.ENABLE) {
            context.source.sender.sendTranslatableComponent(PLUGIN_FUNCTION_NOT_ENABLED, "fix_table")
            return Command.SINGLE_SUCCESS
        }

        val player = if (context.nodes.last().node.name == "player")
            context.getArgument("player", PlayerSelectorArgumentResolver::class.java).resolve(context.source)[0]
        else context.source.sender as? Player
        if (player == null) {
            context.source.sender.sendTranslatableComponent(COMMAND_CONSOLE_CANT_EXECUTE)
            return Command.SINGLE_SUCCESS
        }

        val brokenGear = FirEnchantAPI.toBrokenGear(player.inventory.itemInMainHand)
        if (brokenGear == null) {
            context.source.sender.sendTranslatableComponent(COMMAND_BROKEN_GEAR_BREAK_MAIN_HAND_FAIL, player.name)
            return Command.SINGLE_SUCCESS
        }
        context.source.sender.sendTranslatableComponent(COMMAND_BROKEN_GEAR_BREAK_MAIN_HAND_SUCCESS, player.name)
        player.inventory.setItemInMainHand(brokenGear)
        return Command.SINGLE_SUCCESS
    }
}