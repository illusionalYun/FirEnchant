package top.catnies.firenchantkt.engine.condition

import org.bukkit.command.CommandSender
import top.catnies.firenchantkt.engine.ArgumentKey
import top.catnies.firenchantkt.engine.AbstractCondition

class PermissionImpl(
    args: Map<String, Any?>
) : AbstractCondition(args) {
    @ArgumentKey(["user"])
    private lateinit var user: CommandSender

    @ArgumentKey(["perm", "permission"])
    private lateinit var permission: String

    override fun getType() = "permission"
    override fun require() = true
    override fun check() = user.hasPermission(permission)

}