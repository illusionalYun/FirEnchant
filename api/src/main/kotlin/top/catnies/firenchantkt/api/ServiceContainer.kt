package top.catnies.firenchantkt.api


// 管理所有服务实例的依赖注入管理器
object ServiceContainer {
    private val services = mutableMapOf<Class<*>, Any>()

    fun <T : Any> register(serviceClass: Class<T>, implementation: T) {
        services.putIfAbsent(serviceClass, implementation)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(serviceClass: Class<T>): T {
        return services[serviceClass] as? T
            ?: throw IllegalStateException("Service ${serviceClass.simpleName} not registered")
    }

    fun <T : Any> isRegistered(serviceClass: Class<T>): Boolean {
        return services.containsKey(serviceClass)
    }

    fun clear() {
        services.clear()
    }
}