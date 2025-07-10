package top.catnies.firenchantkt.engine.condition

import top.catnies.firenchantkt.engine.Action
import top.catnies.firenchantkt.engine.ArgumentKey

abstract class AbstractAction(
    args: Map<String, Any?>
): Action {

    init {
        for (field in this.javaClass.declaredFields) {
            val key = field.getAnnotation(ArgumentKey::class.java) ?: continue
            field.isAccessible = true

            for (arg in key.args) {
                if (args.containsKey(arg)) {
                    field.set(this, args[arg])
                    break
                }
            }
        }
    }

}