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
import top.catnies.firenchantkt.database.PlayerEnchantLogData
import top.catnies.firenchantkt.database.PlayerEnchantLogDataManager
import top.catnies.firenchantkt.enchantment.EnchantmentSetting
import top.catnies.firenchantkt.enchantment.FirEnchantmentSettingFactory
import top.catnies.firenchantkt.util.ItemUtils.isCompatibleWithEnchantment
import java.util.Random
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
        val firstSetting = FirEnchantmentSettingFactory.fromItemStack(context.firstItem)
        val setting = FirEnchantmentSettingFactory.fromItemStack(context.secondItem)!!
        val repairCost = context.firstItem.getDataOrDefault(DataComponentTypes.REPAIR_COST, 0)!!
        val repairCost2 = context.secondItem.getDataOrDefault(DataComponentTypes.REPAIR_COST, 0)!!
        val originEnchantment = setting.data.originEnchantment

        when {
            isEnchantedBookMerge(firstSetting, setting) -> {
                val resultSetting = FirEnchantmentSettingFactory.fromAnother(firstSetting!!).apply {
                    failure = getMergeEnchantmentFailureRate(firstSetting, setting)
                    level++
                }
                val costExp = if (config.EB_MERGE_EXP_COST_MODE.equals("FIXED", true)) config.EB_MERGE_EXP_FIXED_VALUE
                else getCost(resultSetting, resultSetting.level, repairCost, repairCost2)

                val mergeEvent =
                    EnchantedBookPreMergeEvent(context.viewer, event, costExp, firstSetting, setting, resultSetting)
                Bukkit.getPluginManager().callEvent(mergeEvent)
                if (mergeEvent.isCancelled) return

                event.result = resultSetting.toItemStack()
                event.view.repairCost = mergeEvent.costExp
            }

            context.firstItem.isCompatibleWithEnchantment(originEnchantment, setting.level) -> {
                val oldLevel = context.firstItem.getEnchantmentLevel(originEnchantment)
                val targetLevel = if (oldLevel != 0 && oldLevel == setting.level) setting.level + 1 else setting.level

                val costExp = if (config.EB_USE_EXP_COST_MODE.equals("FIXED", true)) config.EB_USE_EXP_FIXED_VALUE
                else getCost(setting, targetLevel, repairCost, repairCost2)
                val resultItem = context.firstItem.clone().apply {
                    setData(DataComponentTypes.REPAIR_COST, repairCost * 2 + 1)
                    addEnchantment(originEnchantment, targetLevel)
                }

                val useEvent =
                    EnchantedBookPreUseEvent(context.viewer, event, costExp, context.firstItem, setting, resultItem)
                Bukkit.getPluginManager().callEvent(useEvent)
                if (useEvent.isCancelled) return

                event.result = useEvent.resultItem
                event.view.repairCost = useEvent.costExp
            }
        }
    }


    override fun onCost(event: InventoryClickEvent, context: AnvilContext) {
        val firstSetting = FirEnchantmentSettingFactory.fromItemStack(context.firstItem)
        val setting = FirEnchantmentSettingFactory.fromItemStack(context.secondItem)!!
        val originEnchantment = setting.data.originEnchantment
        val resultItem = context.result!!
        val anvilView = event.view as AnvilView

        when {
            isEnchantedBookMerge(firstSetting, setting) -> {
                val mergeEvent =
                    EnchantedBookMergeEvent(context.viewer, event, anvilView, firstSetting!!, setting, resultItem)
                Bukkit.getPluginManager().callEvent(mergeEvent)
                if (mergeEvent.isCancelled) {
                    event.isCancelled = true
                    return
                }
            }

            context.firstItem.isCompatibleWithEnchantment(originEnchantment, setting.level) -> {
                val useEvent = EnchantedBookUseEvent(
                    context.viewer, event, anvilView, context.firstItem, setting, resultItem,
                    isSuccess(context.viewer, originEnchantment.key.asString(), anvilView.repairCost, setting.failure)
                )
                Bukkit.getPluginManager().callEvent(useEvent)
                if (useEvent.isCancelled) {
                    event.isCancelled = true
                    return
                }
                if (useEvent.isSuccess) return

                event.isCancelled = true
                anvilView.setItem(1, ItemStack.empty())
                anvilView.setItem(2, ItemStack.empty())

                if (FirEnchantAPI.hasProtectionRune(context.firstItem)) {
                    FirEnchantAPI.removeProtectionRune(context.firstItem)
                    anvilView.setItem(0, context.firstItem)
                    anvilView.setCursor(ItemStack(Material.SOUL_SAND))
                    context.viewer.sendMessage("失败但是有符文!")
                } else {
                    anvilView.setItem(0, FirEnchantAPI.toBrokenGear(context.firstItem))
                    anvilView.setCursor(ItemStack(Material.SAND))
                    context.viewer.sendMessage("失败也没有符文!")
                }
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

    // 获取经验花费
    private fun getCost(setting: EnchantmentSetting, level: Int, repairCost: Int, repairCost2: Int): Int {
        return setting.data.originEnchantment.anvilCost * level + repairCost + repairCost2
    }

    // 根据失败率判断是否成功
    private fun isSuccess(player: Player, enchantment: String, level: Int, baseFailure: Int): Boolean {
        val manager = FirEnchantAPI.playerEnchantLogDataManager.invoke()

        // 1. 获取玩家历史记录（最近20次）
        val logList = manager.getList(player.uniqueId, 20)

        // 2. 计算连续失败次数（心理补偿核心）
        val consecutiveFails = logList!!.takeLastWhile { !it!!.isSuccess }.size

        // 3. 动态调整实际成功率（补偿公式）
        val actualFailure = when {
            consecutiveFails >= 5 -> baseFailure + 40  // 保底：连续5次失败后大幅提升概率
            consecutiveFails >= 3 -> baseFailure + 20  // 补偿：连续3次失败后中等提升
            else -> baseFailure
        }.coerceAtMost(95)  // 上限95%避免必成

        // 4. 概率判定
        val random = (0..100).random()
        val success = random < actualFailure

        // 5. 记录日志
        val log = PlayerEnchantLogData().apply {
            this.player = player.uniqueId
            this.enchantment = enchantment
            this.takeLevel = level
            this.random = random
            this.isSuccess = success
            this.baseFailure = baseFailure
            this.actualFailure = actualFailure
        }
        manager.update(log, true)

        return success
    }
}