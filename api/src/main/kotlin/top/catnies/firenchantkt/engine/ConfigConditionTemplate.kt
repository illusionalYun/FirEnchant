package top.catnies.firenchantkt.engine

// Condition配置模板，在配置加载时预解析和验证
data class ConfigConditionTemplate(
    val type: String,
    val conditionClass: Class<out Condition>,
    val staticArgs: Map<String, Any?>,
    val missingRequiredArgs: List<String> = emptyList(),
    val isValid: Boolean = missingRequiredArgs.isEmpty()
) {

    // 检查条件是否通过
    fun check(runtimeArgs: Map<String, Any?> = emptyMap()): Boolean {
        if (!isValid) return false
        val combinedArgs = staticArgs.toMutableMap()
        combinedArgs.putAll(runtimeArgs)
        return conditionClass.getDeclaredConstructor(Map::class.java).newInstance(combinedArgs).check()
    }
}