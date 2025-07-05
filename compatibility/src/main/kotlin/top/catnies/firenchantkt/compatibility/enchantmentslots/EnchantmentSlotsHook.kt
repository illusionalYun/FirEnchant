package top.catnies.firenchantkt.compatibility.enchantmentslots

import org.bukkit.Bukkit


// EnchantmentSlots Hook
class EnchantmentSlotsHook private constructor() {

    companion object {
        val instance by lazy { EnchantmentSlotsHook().also {
            it.load()
        }}
    }

    fun load() {
        // TODO 注册监听器? 直接从容器里拿 Registry 注册?
        val plugin = Bukkit.getPluginManager().getPlugin("EnchantmentSlots")!!
        Bukkit.getPluginManager().registerEvents(EnchantmentSlotsRegistryListener(), plugin)
    }


}