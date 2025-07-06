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
        val plugin = Bukkit.getPluginManager().getPlugin("EnchantmentSlots")!!

    }


}