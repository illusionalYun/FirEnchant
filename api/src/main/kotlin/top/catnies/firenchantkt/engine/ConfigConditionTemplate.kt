package top.catnies.firenchantkt.engine

// Condition配置模板，在配置加载时预解析和验证
data class ConfigConditionTemplate(
    val type: String,
    val conditionClass: Class<out Condition>,
    val staticArgs: Map<String, Any?>,
) {

    // 检查条件是否通过
    fun check(runtimeArgs: Map<String, Any?> = emptyMap()): Boolean {
        val combinedArgs = staticArgs.toMutableMap()
        combinedArgs.putAll(runtimeArgs)
        
        return try {
            conditionClass.getDeclaredConstructor(Map::class.java).newInstance(combinedArgs).check()
        } catch (e: Exception) {
            e.printStackTrace()
            // 如果条件检查失败，返回 false 而不是抛出异常
            false
        }
    }
    
    // 创建条件实例
    fun createInstance(runtimeArgs: Map<String, Any?> = emptyMap()): Condition? {
        val combinedArgs = staticArgs.toMutableMap()
        combinedArgs.putAll(runtimeArgs)
        
        return try {
            conditionClass.getDeclaredConstructor(Map::class.java).newInstance(combinedArgs)
        } catch (e: Exception) {
            null
        }
    }
}