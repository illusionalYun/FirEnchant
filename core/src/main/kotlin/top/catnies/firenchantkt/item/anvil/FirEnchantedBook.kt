package top.catnies.firenchantkt.item.anvil

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.view.AnvilView
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.api.event.EnchantedBookMergeEvent
import top.catnies.firenchantkt.api.event.PreEnchantedBookMergeEvent
import top.catnies.firenchantkt.config.AnvilConfig
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.enchantment.EnchantmentSetting
import top.catnies.firenchantkt.enchantment.FirEnchantmentSettingFactory
import top.catnies.firenchantkt.item.AnvilApplicable
import top.catnies.firenchantkt.util.ItemUtils.isCompatibleWithEnchantment

class FirEnchantedBook: AnvilApplicable {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = plugin.logger
        val config = AnvilConfig.instance
    }

    // 抓取符合条件的物品
    override fun matches(itemStack: ItemStack): Boolean {
        FirEnchantmentSettingFactory.fromItemStack(itemStack) ?: return false
        return true
    }

    override fun onPrepare(event: PrepareAnvilEvent, context: AnvilContext) {
        val setting = FirEnchantmentSettingFactory.fromItemStack(context.secondItem)!!
        val firstSetting = FirEnchantmentSettingFactory.fromItemStack(context.firstItem)
        val originEnchantment = setting.data.originEnchantment

        // 如果第一件物品也是附魔书, 触发合并逻辑.
        if (isEnchantedBookMerge(firstSetting, setting)) {
            // 计算结果
            val resultSetting = FirEnchantmentSettingFactory.fromAnother(firstSetting!!)
            resultSetting.level++

            // 计算经验消耗
            val costExp = 10 // TODO 经验消耗要不要通过原版方案计算? 原版好像有价值组件.

            // 触发事件
            val preEnchantedBookMergeEvent = PreEnchantedBookMergeEvent(context.viewer, event, costExp, firstSetting, setting, resultSetting)
            Bukkit.getPluginManager().callEvent(preEnchantedBookMergeEvent)
            if (preEnchantedBookMergeEvent.isCancelled) return

            // 显示结果
            event.result = resultSetting.toItemStack()
            event.view.repairCost = preEnchantedBookMergeEvent.costExp
        }

        // 如果第一件物品是普通物品, 检查是否可以附魔.
        else if (context.firstItem.isCompatibleWithEnchantment(originEnchantment)) {
            // TODO("预览结果, 触发事件")
        }

    }

    override fun onCost(event: InventoryClickEvent, context: AnvilContext) {
        val setting = FirEnchantmentSettingFactory.fromItemStack(context.secondItem)!!
        val firstSetting = FirEnchantmentSettingFactory.fromItemStack(context.firstItem)
        val originEnchantment = setting.data.originEnchantment

        // 如果第一件物品也是附魔书, 触发合并逻辑.
        // TODO (两本附魔书融合, 是否需要考虑失败率? 或者给出这个选项? -> 是否可以监听事件实现, 而非塞入主逻辑.)
        if (isEnchantedBookMerge(firstSetting, setting)) {
            // 计算结果
            val resultItem = context.result!!
            val anvilView = event.view as AnvilView

            // 触发事件
            val enchantedBookMergeEvent = EnchantedBookMergeEvent(context.viewer, event, anvilView, firstSetting!!, setting, resultItem)
            Bukkit.getPluginManager().callEvent(enchantedBookMergeEvent)
            if (enchantedBookMergeEvent.isCancelled) {
                event.isCancelled = true
                return
            }
        }

        // 如果第一件物品是普通物品, 检查物品是否可以附魔.
        else if (context.firstItem.isCompatibleWithEnchantment(originEnchantment)) {
            // TODO("预览结果, 触发事件")
        }

    }


    // 检查是否是两本附魔书合并的情况
    private fun isEnchantedBookMerge(firstSetting: EnchantmentSetting?, secondSetting: EnchantmentSetting?): Boolean {
        if (firstSetting == null || secondSetting == null) return false // 第一个物品可转换成附魔配置.
        if (secondSetting.data.originEnchantment != firstSetting.data.originEnchantment) return false // 两个魔咒应当相同
        if (firstSetting.level >= firstSetting.data.maxLevel) return false // 第一本附魔书应当小于最大等级, 否则无法升级.
        if (secondSetting.level != firstSetting.level) return false // 第一本和第二本附魔书应当等级相同.
        return true
    }


    // 根据失败率判断是否成功
    private fun isSuccess(failure: Int): Boolean {
        // TODO ( 研究一下更合适的算法, 纯随机玩家表示受不了, 另外要注意config里可能会有兜底和其他方案)
        return true
    }
}