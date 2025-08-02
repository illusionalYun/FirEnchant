package top.catnies.firenchantkt.compatibility.auraskill

import dev.aurelium.auraskills.api.AuraSkillsApi
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import top.catnies.firenchantkt.api.event.enchantingtable.EnchantItemEvent


class EnchantEventListener: Listener {

    @EventHandler(ignoreCancelled = true)
    fun onEnchantingTableEnchant(event: EnchantItemEvent) {
        val auraSkills = AuraSkillsApi.get()
        val user = auraSkills.getUser(event.player.uniqueId)
        // TODO AuraSKill是怎么计算经验的?
    }
}