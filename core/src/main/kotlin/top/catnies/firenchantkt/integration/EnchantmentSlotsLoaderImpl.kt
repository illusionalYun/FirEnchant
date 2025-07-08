package top.catnies.firenchantkt.integration

import org.bukkit.Bukkit
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.compatibility.enchantmentslots.EnchantmentSlotsListener
import top.catnies.firenchantkt.compatibility.enchantmentslots.EnchantmentSlotsLoader
import top.catnies.firenchantkt.compatibility.enchantmentslots.SlotRuneImpl
import top.catnies.firenchantkt.config.AnvilConfig
import top.catnies.firenchantkt.item.anvil.FirProtectionRune.Companion.config
import top.catnies.firenchantkt.language.MessageConstants.RESOURCE_HOOK_ITEM_NOT_FOUND
import top.catnies.firenchantkt.language.MessageConstants.RESOURCE_HOOK_ITEM_PROVIDER_NOT_FOUND
import top.catnies.firenchantkt.util.MessageUtils.sendTranslatableComponent

class EnchantmentSlotsLoaderImpl private constructor(): EnchantmentSlotsLoader {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = FirEnchantPlugin.instance.logger

        val instance by lazy { EnchantmentSlotsLoaderImpl().also {
            SlotRuneImpl.delegatesLoader = it
            Bukkit.getPluginManager().registerEvents(EnchantmentSlotsListener(), plugin)
            it.initSlotRuneImpl()
        }}
    }

    override fun initSlotRuneImpl() {
        SlotRuneImpl.isEnabled = AnvilConfig.instance.SLOT_RUNE_ENABLE
        if (SlotRuneImpl.isEnabled) {
            SlotRuneImpl.itemProvider = config.SLOT_RUNE_ITEM_PROVIDER?.let { FirItemProviderRegistry.instance.getItemProvider(it) }
            if (SlotRuneImpl.itemProvider == null) {
                Bukkit.getConsoleSender().sendTranslatableComponent(RESOURCE_HOOK_ITEM_PROVIDER_NOT_FOUND, config.fileName, "slot-rune", config.SLOT_RUNE_ITEM_PROVIDER ?: "null")
                SlotRuneImpl.isEnabled = false
                return
            }

            SlotRuneImpl.itemID = config.PROTECTION_RUNE_ITEM_ID
            val item = SlotRuneImpl.itemID?.let { SlotRuneImpl.itemProvider?.getItemById(it) }
            if (item == null) {
                Bukkit.getConsoleSender().sendTranslatableComponent(RESOURCE_HOOK_ITEM_NOT_FOUND, config.fileName, "slot-rune", SlotRuneImpl.itemID ?: "null")
                SlotRuneImpl.isEnabled = false
                return
            }
        }
    }
}