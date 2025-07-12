package top.catnies.firenchantkt.engine

interface Condition {

    // 启用条件的条件
    fun require(): Boolean {
        return true
    }

    // 检查条件
    fun check(): Boolean

}