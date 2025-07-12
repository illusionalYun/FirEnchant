package top.catnies.firenchantkt.engine.condition.player

import org.bukkit.entity.Player
import top.catnies.firenchantkt.engine.AbstractCondition
import top.catnies.firenchantkt.engine.ArgumentKey

class PermissionCondition(
    args: Map<String, Any?>
) : AbstractCondition(args) {
    @ArgumentKey(["player"], autoInject = true, description = "权限的被检查者")
    private lateinit var player: Player

    @ArgumentKey(["perm", "permission"])
    private lateinit var permission: String

    override fun require() = true
    override fun check() = player.hasPermission(permission)
}