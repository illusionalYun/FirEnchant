package top.catnies.firenchantkt.compatibility.enchantmentslots


// EnchantmentSlots Hook
class EnchantmentSlotsHook private constructor() {

    companion object {
        val instance by lazy { EnchantmentSlotsHook().also {
            it.load()
        }}
    }

    fun load() {
        // TODO 注册监听器? 直接从容器里拿Registry注册?
    }


}