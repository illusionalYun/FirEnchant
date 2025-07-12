package top.catnies.firenchantkt.engine

@Retention(AnnotationRetention.RUNTIME)
annotation class ArgumentKey(
    val args: Array<String>,
    val autoInject: Boolean = false,
    val required: Boolean = true,
    val description: String = ""
)
