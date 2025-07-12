package top.catnies.firenchantkt.engine

abstract class AbstractAction(
    args: Map<String, Any?>
): Action {

    init {
        for (field in this::class.java.declaredFields) {
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