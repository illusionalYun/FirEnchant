package top.catnies.firenchantkt.context

interface Context {

    fun put(key: String, value: Any): Any?

    fun get(key: String): Any?

    fun remove(key: String): Any?

    fun clear()

    fun getAll(): Map<String, Any>

}