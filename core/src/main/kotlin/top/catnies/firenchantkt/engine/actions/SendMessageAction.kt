package top.catnies.firenchantkt.engine.actions

import org.bukkit.command.CommandSender
import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractAction
import top.catnies.firenchantkt.util.MessageUtils.renderToComponent

class SendMessageAction(
    var args: Map<String, Any?>
): AbstractAction(args) {

    @ArgumentKey(["user"])
    private lateinit var user: CommandSender

    @ArgumentKey(["content", "message"])
    private lateinit var content: String

    override fun getType() = "send_message"

    override fun execute() {
        content.renderToComponent(user).also { user.sendMessage(it) }
    }

}