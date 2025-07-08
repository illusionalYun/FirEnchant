package top.catnies.firenchantkt.item.anvil

import io.papermc.paper.datacomponent.DataComponentTypes
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.view.AnvilView
import top.catnies.firenchantkt.FirEnchantPlugin
import top.catnies.firenchantkt.api.FirEnchantAPI
import top.catnies.firenchantkt.api.event.anvilapplicable.EnchantedBookMergeEvent
import top.catnies.firenchantkt.api.event.anvilapplicable.EnchantedBookPreMergeEvent
import top.catnies.firenchantkt.api.event.anvilapplicable.EnchantedBookPreUseEvent
import top.catnies.firenchantkt.api.event.anvilapplicable.EnchantedBookUseEvent
import top.catnies.firenchantkt.config.AnvilConfig
import top.catnies.firenchantkt.context.AnvilContext
import top.catnies.firenchantkt.enchantment.EnchantmentSetting
import top.catnies.firenchantkt.enchantment.FirEnchantmentSettingFactory
import top.catnies.firenchantkt.util.ItemUtils.isCompatibleWithEnchantment
import kotlin.math.max
import kotlin.math.min

class FirEnchantedBook : EnchantedBook {

    companion object {
        val plugin = FirEnchantPlugin.instance
        val logger = plugin.logger
        val config = AnvilConfig.instance
    }

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
            resultSetting.failure = getMergeEnchantmentFailureRate(firstSetting, setting)
            resultSetting.level++

            // 计算经验消耗
            val costExp = if (config.EB_MERGE_EXP_COST_MODE.uppercase() == "FIXED") config.EB_MERGE_EXP_FIXED_VALUE
            else getMergeRepairCost(firstSetting, setting)

            // 触发事件
            val enchantedBookPreMergeEvent =
                EnchantedBookPreMergeEvent(context.viewer, event, costExp, firstSetting, setting, resultSetting)
            Bukkit.getPluginManager().callEvent(enchantedBookPreMergeEvent)
            if (enchantedBookPreMergeEvent.isCancelled) return

            // 显示结果
            event.result = resultSetting.toItemStack()
            event.view.repairCost = enchantedBookPreMergeEvent.costExp
        }

        // 如果第一件物品是普通物品, 检查是否可以附魔.
        else if (context.firstItem.isCompatibleWithEnchantment(originEnchantment)) {
            // 计算结果
            val resultItem = context.firstItem.clone()
            val oldLevel = resultItem.getEnchantmentLevel(setting.data.originEnchantment);
            var level = setting.level
            if (oldLevel >= setting.level) {
                return
            }

            if (oldLevel == setting.level) {
                level++
            }

            val repairCost: Int = resultItem.getDataOrDefault(DataComponentTypes.REPAIR_COST, 0)!!

            // 计算经验消耗
            val costExp = if (config.EB_USE_EXP_COST_MODE.uppercase() == "FIXED") config.EB_USE_EXP_FIXED_VALUE
            else getUseRepairCost(context.firstItem, setting, level, repairCost)

            resultItem.setData(DataComponentTypes.REPAIR_COST, repairCost * 2 + 1)
            resultItem.addEnchantment(setting.data.originEnchantment, setting.level)

            // 触发事件
            val enchantedBookPreUseEvent =
                EnchantedBookPreUseEvent(context.viewer, event, costExp, context.firstItem, setting, resultItem)
            Bukkit.getPluginManager().callEvent(enchantedBookPreUseEvent)
            if (enchantedBookPreUseEvent.isCancelled) return

            // 显示结果
            event.result = enchantedBookPreUseEvent.resultItem
            event.view.repairCost = enchantedBookPreUseEvent.costExp
        }

    }

    override fun onCost(event: InventoryClickEvent, context: AnvilContext) {
        val setting = FirEnchantmentSettingFactory.fromItemStack(context.secondItem)!!
        val firstSetting = FirEnchantmentSettingFactory.fromItemStack(context.firstItem)
        val originEnchantment = setting.data.originEnchantment

        // 如果第一件物品也是附魔书, 触发合并逻辑.
        // TODO (两本附魔书融合, 是否需要考虑失败率? 或者给出这个选项? -> 是否可以监听事件实现拓展, 而非塞入主逻辑.)
        if (isEnchantedBookMerge(firstSetting, setting)) {
            val resultItem = context.result!!
            val anvilView = event.view as AnvilView

            // 触发事件
            val enchantedBookMergeEvent =
                EnchantedBookMergeEvent(context.viewer, event, anvilView, firstSetting!!, setting, resultItem)
            Bukkit.getPluginManager().callEvent(enchantedBookMergeEvent)
            if (enchantedBookMergeEvent.isCancelled) {
                event.isCancelled = true
                return
            }
        }

        // 如果第一件物品是普通物品, 检查物品是否可以附魔.
        else if (context.firstItem.isCompatibleWithEnchantment(originEnchantment)) {
            val resultItem = context.result!!
            val anvilView = event.view as AnvilView
            val isSuccess = isSuccess(context.viewer, setting.failure)

            // 触发事件
            val enchantedBookUseEvent = EnchantedBookUseEvent(
                context.viewer,
                event,
                anvilView,
                context.firstItem,
                setting,
                resultItem,
                isSuccess
            )
            Bukkit.getPluginManager().callEvent(enchantedBookUseEvent)
            if (enchantedBookUseEvent.isCancelled) {
                event.isCancelled = true
                return
            }

            // 成功直接返回
            if (enchantedBookUseEvent.isSuccess) return

            // 失败逻辑
            event.isCancelled = true
            // 检查保护符文功能是否开启, 物品有没有保护符文
            if (FirEnchantAPI.hasProtectionRune(context.firstItem)) {
                // 移除保护,
                FirEnchantAPI.removeProtectionRune(context.firstItem)
                anvilView.setItem(0, context.firstItem)
                anvilView.setItem(1, ItemStack.empty())
                anvilView.setItem(2, ItemStack.empty())
                anvilView.setCursor(ItemStack(Material.SOUL_SAND))
                // 发送消息 TODO 翻译键
                context.viewer.sendMessage("失败但是有符文!")
            }

            // 没有保护符文
            else {
                val brokenGear = FirEnchantAPI.toBrokenGear(context.firstItem)
                anvilView.setItem(0, brokenGear)
                anvilView.setItem(1, ItemStack.empty())
                anvilView.setItem(2, ItemStack.empty())
                anvilView.setCursor(ItemStack(Material.SAND))
                // TODO 翻译键
                context.viewer.sendMessage("失败也没有符文!")
            }
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

    // 根据模式计算合并附魔书时最终附魔书的失败率
    private fun getMergeEnchantmentFailureRate(
        firstSetting: EnchantmentSetting,
        secondSetting: EnchantmentSetting
    ): Int {
        return when (config.EB_MERGE_FAILURE_INHERITANCE) {
            "HIGHER" -> max(firstSetting.failure, secondSetting.failure)
            "LOWER" -> min(firstSetting.failure, secondSetting.failure)
            "RANDOM" -> {
                val min = min(firstSetting.failure, secondSetting.failure)
                val max = max(firstSetting.failure, secondSetting.failure)
                (min..max).random()
            }

            else -> firstSetting.failure
        }
    }

    // 根据模式计算消耗的经验值
    // TODO 经验消耗通过原版方案计算? 原版好像有价值组件.
    private fun getMergeRepairCost(firstSetting: EnchantmentSetting, secondSetting: EnchantmentSetting): Int {
        return 10
    }

    private fun getUseRepairCost(item: ItemStack, setting: EnchantmentSetting, level: Int, repairCost: Int): Int {
        return setting.data.originEnchantment.anvilCost * level + repairCost
    }

    // 根据失败率判断是否成功
    private fun isSuccess(player: Player, failure: Int): Boolean {
        // TODO (研究一下更合适的算法, 纯随机玩家表示受不了, 另外要注意config里可能会有兜底和其他方案)
        return true
    }
}