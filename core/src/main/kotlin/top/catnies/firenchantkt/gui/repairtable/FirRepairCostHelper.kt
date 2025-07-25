package top.catnies.firenchantkt.gui.repairtable

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.config.RepairTableConfig
import top.catnies.firenchantkt.util.ItemUtils.sumAllEnchantmentCount
import top.catnies.firenchantkt.util.ItemUtils.sumAllEnchantmentLevel
import kotlin.math.ceil

object FirRepairCostHelper{

    /**
     * 获取一件装备修复所需的时间;
     */
    fun getRepairTimeCost(player: Player, item: ItemStack): Long {
        val magnification = getPlayerMagnification(player)
        val repairTime = when (RepairTableConfig.instance.REPAIR_TIMERULE_RULE) {
            "static" -> RepairTableConfig.instance.REPAIR_TIMERULE_STATIC_TIME
            "level" -> {
                val level = item.sumAllEnchantmentLevel()
                if (level <= 0) return RepairTableConfig.instance.REPAIR_TIMERULE_LEVEL_FALLBACK
                level * RepairTableConfig.instance.REPAIR_TIMERULE_LEVEL_TIME
            }
            "count" -> {
                val count = item.sumAllEnchantmentCount()
                if (count <= 0) return RepairTableConfig.instance.REPAIR_TIMERULE_COUNT_FALLBACK
                count * RepairTableConfig.instance.REPAIR_TIMERULE_COUNT_TIME
            }
            else -> throw AssertionError("不可能进入的分支.")
        }
        return ceil(repairTime * magnification).toLong()
    }


    /**
     * 获取玩家修复装备时的折扣力度
     */
    fun getPlayerMagnification(player: Player): Double {
        RepairTableConfig.instance.REPAIR_MAGNIFICATION_RULE.forEach {
            if (player.hasPermission(it.key)) {
                return it.value
            }
        }
        return 1.0
    }

}