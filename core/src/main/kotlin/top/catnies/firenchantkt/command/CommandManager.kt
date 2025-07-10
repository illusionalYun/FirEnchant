package top.catnies.firenchantkt.command

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.command.openmenu.ExtractSoulMenuCommand

class CommandManager private constructor() {
    val plugin get() = FirEnchantPlugin.instance
    val logger get() = plugin.logger

    companion object {
        val instance: CommandManager by lazy { CommandManager().apply { load() } }
    }

    // 初始化
    private fun load() {
        registerCommands()
    }

    fun reload () {}

    // 注册命令
    private fun registerCommands() {
        // 根命令
        val root = Commands.literal("firenchant").requires {
            return@requires it.sender.hasPermission("firenchant.command")
        }.apply {
            then(VersionCommand.create()) // 版本命令
            then(ReloadCommand.create()) // 重载插件命令
            then(GiveEnchantedBookCommand.create()) // 给予附魔书命令
        }

        // 菜单命令
        val menuRoot = Commands.literal("openmenu").requires {
            return@requires it.sender.hasPermission("firenchant.command.openmenu")
        }.apply {
            then(ExtractSoulMenuCommand.create()) // 灵魂提取菜单
        }.also { root.then(it) }

        // Debug命令
        val debugRoot = Commands.literal("debug").requires {
            return@requires it.sender.hasPermission("firenchant.command.debug")
        }.apply {
            // TODO 检查目标位置有效书架数量
        }.also { root.then(it) }

        // 注册命令到服务器
        val lifecycleEventManager = plugin.lifecycleManager
        lifecycleEventManager.registerEventHandler(LifecycleEvents.COMMANDS) {
            it.registrar().register(root.build())
        }
    }

}