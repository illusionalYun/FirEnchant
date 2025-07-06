package top.catnies.firenchantkt.compatibility.enchantmentslots

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import top.catnies.firenchantkt.api.event.AnvilApplicableItemRegisterEvent

class RegistryListener: Listener {

    @EventHandler
    fun onAnvilItemRegister(event: AnvilApplicableItemRegisterEvent) {
        event.register.registerItem(SlotRune())
    }

}