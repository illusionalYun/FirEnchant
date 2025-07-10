package top.catnies.firenchantkt.item.fixtable

import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin

class FirBrokenGear: BrokenGear {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = FirEnchantPlugin.instance.logger
    }

    override fun isBrokenGear(item: ItemStack): Boolean {
        TODO("Not yet implemented")
    }

    override fun toBrokenGear(item: ItemStack): ItemStack {
        TODO("Not yet implemented")
    }

    override fun repairBrokenGear(item: ItemStack): ItemStack {
        TODO("Not yet implemented")
    }


}