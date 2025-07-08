package top.catnies.firenchantkt.engine.condition

import org.bukkit.command.CommandSender
import top.catnies.firenchantkt.engine.args.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

class PermissionImpl(
    args: Map<String, Any?>
) : AbstractCondition(args) {
    @ArgumentKey(["user"])
    private lateinit var user: CommandSender

    @ArgumentKey(["perm", "permission"])
    private lateinit var permission: String

    override fun getType(): String {
        return "permission"
    }

    override fun require(): Boolean {
        return true
    }

    override fun check(): Boolean {
        return user.hasPermission(permission)
    }
}