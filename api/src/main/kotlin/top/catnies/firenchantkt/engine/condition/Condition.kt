package top.catnies.firenchantkt.engine.condition

interface Condition {
    fun getType(): String

    fun require(): Boolean

    fun check(): Boolean
}