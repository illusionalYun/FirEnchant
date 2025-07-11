package top.catnies.firenchantkt.engine

annotation class ArgumentKey(
    val args: Array<String>,
    val autoInject: Boolean = false,
    val required: Boolean = true,
    val description: String = ""
)
