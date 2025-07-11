package top.catnies.firenchantkt.engine

// Action配置模板，在配置加载时预解析和验证
data class ConfigActionTemplate(
    val type: String,
    val actionClass: Class<out Action>,
    val staticArgs: Map<String, Any?>,
    val missingRequiredArgs: List<String> = emptyList(),
    val conditions: List<ConfigConditionTemplate> = emptyList(),
    val isValid: Boolean = missingRequiredArgs.isEmpty() && conditions.all { it.isValid }
) {
    
    /**
     * 使用模板类在运行时创建Action实例
     * @param runtimeArgs 运行时参数（如 user 等）
     */
    fun createInstance(runtimeArgs: Map<String, Any?> = emptyMap()): Action {
        val combinedArgs = staticArgs.toMutableMap()
        combinedArgs.putAll(runtimeArgs)
        return actionClass.getDeclaredConstructor(Map::class.java).newInstance(combinedArgs)
    }

    // 执行Action（包含条件检查）
    fun executeIfAllowed(runtimeArgs: Map<String, Any?> = emptyMap()) {
        if (isValid && checkConditions(runtimeArgs)) {
            createInstance(runtimeArgs).execute()
        }
    }

    // 检查所有条件是否通过
    fun checkConditions(runtimeArgs: Map<String, Any?> = emptyMap()): Boolean {
        return conditions.all { it.check(runtimeArgs) }
    }
}

