package top.catnies.firenchantkt.engine

import org.bukkit.Bukkit
import top.catnies.firenchantkt.engine.args.ArgumentKey
import top.catnies.firenchantkt.engine.condition.Condition
import top.catnies.firenchantkt.language.MessageConstants
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent

abstract class AbstractCondition(
    args: Map<String, Any?>
) : Condition {
    init {
        for (field in this.javaClass.fields) {
            val key = field.getAnnotation(ArgumentKey::class.java) ?: continue

            for (arg in key.args) {
                if (args.containsKey(arg)) {
                    field.set(this, args[arg])
                    break
                }
            }

            if (field.get(this) == null) {
                Bukkit.getConsoleSender()
                    .sendTranslatableComponent(MessageConstants.PLUGIN_CONDITION_ARGS_INVALID, getType(), key.args.toString())
                throw IllegalArgumentException()
            }
        }
    }

    fun onCheck(): Boolean {
        if (!require()) {
            return false
        }

        return check()
    }
}