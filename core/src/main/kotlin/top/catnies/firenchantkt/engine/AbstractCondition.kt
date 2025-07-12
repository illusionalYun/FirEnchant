package top.catnies.firenchantkt.engine

import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.KType

abstract class AbstractCondition(
    open val args: Map<String, Any?>
) : Condition {

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
                try {
                    val convertedValue = convertValue(value, prop.returnType)
                    prop.setter.call(this, convertedValue)
                } catch (e: Exception) {
                    // 如果转换失败，尝试直接注入原值
                    try {
                        prop.setter.call(this, value)
                    } catch (innerE: Exception) {
                        // 记录错误信息并跳过此字段
                        println("Failed to inject value for property ${prop.name}: ${innerE.message}")
                    }
                }
            }
        }
    }

    // 类型转换函数，支持常见类型的转换
    private fun convertValue(value: Any?, targetType: KType): Any? {
        if (value == null) return null
        val targetClass = targetType.classifier as? kotlin.reflect.KClass<*> ?: return value
        
        // 如果类型已经匹配，直接返回
        if (targetClass.isInstance(value)) return value
        
        // 支持的类型转换
        return when (targetClass) {
            String::class -> value.toString()
            else -> value // 对于其他类型，尝试直接使用原值
        }
    }

}