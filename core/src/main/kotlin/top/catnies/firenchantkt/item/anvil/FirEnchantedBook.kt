package top.catnies.firenchantkt.item.anvil

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.enchantment.FirEnchantmentSettingFactory
import top.catnies.firenchantkt.item.AnvilApplicable
import top.catnies.firenchantkt.util.ItemUtils.isCompatibleWithEnchantment

class FirEnchantedBook: AnvilApplicable {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = plugin.logger
    }

    // 抓取符合条件的物品
    override fun matches(itemStack: ItemStack): Boolean {
        FirEnchantmentSettingFactory.fromItemStack(itemStack) ?: return false
        return true
    }

    override fun onPrepare(event: PrepareAnvilEvent, context: AnvilContext) {
        val setting = FirEnchantmentSettingFactory.fromItemStack(context.secondItem)!!

        // 如果第一件物品也是附魔书, 触发合并逻辑.
        val firstSetting = FirEnchantmentSettingFactory.fromItemStack(context.firstItem)
        if (firstSetting != null && // 第一个物品可转换成附魔配置.
            setting.data.originEnchantment == firstSetting.data.originEnchantment && // 两个魔咒应当相同
            firstSetting.level < firstSetting.data.maxLevel && // 第一本附魔书应当小于最大等级, 否则无法升级.
            firstSetting.level ==  setting.level // 第一本和第二本附魔书应当等级相同.
            )
        {
            firstSetting.level++
            event.result = firstSetting.toItemStack()
            event.inventory.repairCostAmount = 12 // TODO 好像没效果. 那现在是什么方法? 经验消耗要不要通过原版方案计算? 原版好像有价值组件.
            // TODO 两本附魔书融合 是否需要考虑失败率?
            // TODO("触发事件? ")
        }

        // 如果第一件物品是普通物品, 检查是否可以附魔.
        if (context.firstItem.isCompatibleWithEnchantment(setting.data.originEnchantment)) {
            // TODO("预览结果, 触发事件")
        }

        return
    }

    override fun onCost(event: InventoryClickEvent, context: AnvilContext) {
        // TODO("计算结果, 触发事件, 触发原版事件?")
    }
}