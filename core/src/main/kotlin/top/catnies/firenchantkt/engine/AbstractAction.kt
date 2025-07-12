package top.catnies.firenchantkt.engine

import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible

abstract class AbstractAction(
    open val args: Map<String, Any?>
): Action {

    init {
        this::class.declaredMemberProperties.forEach { prop ->
            val annotation = prop.findAnnotation<ArgumentKey>() ?: return@forEach

            // 从注解的参数里找到第一个args里有的参数
            val value = annotation.args.firstNotNullOfOrNull { this.args[it] }
            if (value == null) {
                throw IllegalStateException("没有找到参数 ${prop.name}, 此错误不应出现, 请联系开发者检查.")
            }

            // 注入字段
            prop.isAccessible = true
            if (prop is KMutableProperty<*>) {
                prop.setter.call(this, value)
            }
        }
    }
}