package top.catnies.firenchantkt.engine

import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible

abstract class AbstractAction(
    open val args: Map<String, Any?>
): Action {

    @ArgumentKey(["runSource"], autoInject = true, description = "触发运行的操作, 用于标记和判断.")
    protected lateinit var runSource: RunSource

    init {
        this::class.declaredMemberProperties.forEach { prop ->
            val annotation = prop.findAnnotation<ArgumentKey>() ?: return@forEach

            // 从注解的参数里找到第一个args里有的参数
            val value = annotation.args.firstNotNullOfOrNull { this.args[it] } ?: return@forEach

            // 注入字段
            prop.isAccessible = true
            if (prop is KMutableProperty<*>) {
                prop.setter.call(this, value)
            }
        }
    }
}