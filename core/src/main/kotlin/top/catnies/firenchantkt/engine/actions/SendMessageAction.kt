package top.catnies.firenchantkt.engine.actions

import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.condition.AbstractAction

class SendMessageAction(
    var args: Map<String, Any?>
): AbstractAction(args) {

    @ArgumentKey(["content", "message"])
    private var content: String = ""

    override fun getType(): String {
        TODO("Not yet implemented")
    }

    override fun execute(): Boolean {
        TODO("Not yet implemented")
    }

}