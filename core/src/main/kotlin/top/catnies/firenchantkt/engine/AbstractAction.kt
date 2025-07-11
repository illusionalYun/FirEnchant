package top.catnies.firenchantkt.engine

abstract class AbstractAction(
    args: Map<String, Any?>
): Action {

    init {
        for (field in this.javaClass.declaredFields) {
            // TODO get不到
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