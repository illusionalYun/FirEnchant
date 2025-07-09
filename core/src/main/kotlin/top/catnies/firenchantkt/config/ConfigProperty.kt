package top.catnies.firenchantkt.config

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// 自定义属性委托
class ConfigProperty<T>(
    private val defaultValue: T
) : ReadWriteProperty<Any?, T> {
    private var value: T = defaultValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}