package top.catnies.firenchantkt.command.debug

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.command.AbstractCommand
import top.catnies.firenchantkt.integration.NMSHandlerHolder

object GetNextEnchantingTableResultCommand : AbstractCommand() {
    // /firenchantkt debug getNextEnchantingTableResult <player> <level> <count> <item>
    override fun create(): LiteralArgumentBuilder<CommandSourceStack> =
        Commands.literal("getNextEnchantingTableResult")
            .then(
                Commands.argument("player", ArgumentTypes.player())
                    .then(
                        Commands.argument("level", IntegerArgumentType.integer(1, 50))
                            .then(
                                Commands.argument("count", IntegerArgumentType.integer(1, 15))
                                    .then(
                                        Commands.argument("item", ArgumentTypes.itemStack())
                                            .executes { execute(it) })
                            )
                    )
            )

    override fun requires(requirement: CommandSourceStack): Boolean = true

    override fun execute(context: CommandContext<CommandSourceStack>): Int {
        val player =
            context.getArgument("player", PlayerSelectorArgumentResolver::class.java).resolve(context.source)[0]
        val level = context.getArgument("level", Integer::class.java)
        val count = context.getArgument("count", Integer::class.java)
        val item = context.getArgument("item", ItemStack::class.java)

        val enchantmentRegistry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);

        val enchantmentList = mutableListOf<Enchantment>();
        enchantmentRegistry.tags
            .filter { it.tagKey().key().value() == "in_enchanting_table" }
            .map { it.values() }
            .forEach { it -> it.forEach{ enchantmentList.add(enchantmentRegistry.getOrThrow(it))} };

        val result =
            NMSHandlerHolder.getNMSHandler()
                .getPlayerNextEnchantmentTableResult(player, level.toInt(), count.toInt(), item.type,
                    HashSet(enchantmentList)
                )
        println(result)

        return Command.SINGLE_SUCCESS
    }
}