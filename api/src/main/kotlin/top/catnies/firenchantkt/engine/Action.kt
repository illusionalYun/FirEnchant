package top.catnies.firenchantkt.engine


interface Action {

    fun getType(): String

    fun execute()

}