package top.catnies.firenchantkt.engine

interface Condition {

    fun getType(): String

    fun require(): Boolean

    fun check(): Boolean

}