package top.catnies.firenchantkt.engine

// 点击动作注册器
interface ActionRegistry {

    // 注册新的点击动作
    fun registerClickAction(name: String, action: Class<out Action>)

    // 注销点击动作
    fun unregisterClickAction(name: String): Boolean

    // 获取点击动作类
    fun getClickAction(name: String): Class<out Action>?

}