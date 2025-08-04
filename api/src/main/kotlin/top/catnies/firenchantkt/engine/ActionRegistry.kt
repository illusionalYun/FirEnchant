package top.catnies.firenchantkt.engine

// 点击动作注册器
interface ActionRegistry {

    // 注册新的点击动作
    fun registerAction(name: String, action: Class<out Action>)

    // 注销点击动作
    fun unregisterAction(name: String): Boolean

    // 获取点击动作类
    fun getAction(name: String): Class<out Action>?

    // 根据类获取名称
    fun getActionName(action: Class<out Action>): String?

}