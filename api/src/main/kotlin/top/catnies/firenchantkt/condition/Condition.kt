package top.catnies.firenchantkt.condition

interface Condition {
    fun getType(): String

    fun require(): Boolean

    fun check(): Boolean
}