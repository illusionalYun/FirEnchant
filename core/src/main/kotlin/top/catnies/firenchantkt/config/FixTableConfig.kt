package top.catnies.firenchantkt.config

import org.bukkit.inventory.ItemStack
import top.catnies.firenchantkt.item.fixtable.BrokenMatchRule
import top.catnies.firenchantkt.util.ItemUtils.nullOrAir
import top.catnies.firenchantkt.util.YamlUtils
import top.catnies.firenchantkt.util.YamlUtils.getConfigurationSectionList

class FixTableConfig private constructor():
    AbstractConfigFile("modules/fix_table.yml")
{
    companion object {
        @JvmStatic
        val instance by lazy { FixTableConfig().apply { loadConfig() } }
    }

    var ENABLE: Boolean by ConfigProperty(false)


    /*破损物品*/
    var BROKEN_FALLBACK_WRAPPER_ITEM: ItemStack? by ConfigProperty(null)
    var BROKEN_MATCHES: MutableList<BrokenMatchRule> by ConfigProperty(mutableListOf())


    // 加载数据
    override fun loadConfig() {
        ENABLE = config().getBoolean("enable", false)
        if (ENABLE) {

        }
    }

    // 等待注册表完成后延迟加载的部分
    override fun loadLatePartConfig() {
        if (ENABLE) {
            /*破损物品*/
            val itemProviderId = config().getString("broken-item-model.fallback-item.hooked-plugin", null)
            val itemId = config().getString("broken-item-model.fallback-item.hooked-id", null)
            BROKEN_FALLBACK_WRAPPER_ITEM = YamlUtils.tryBuildItem(itemProviderId, itemId, fileName, "broken-item-model.fallback-item")
            if (BROKEN_FALLBACK_WRAPPER_ITEM.nullOrAir()) ENABLE = false

            // 构建捕捉规则
            config().getConfigurationSectionList("broken-item-model.matches").forEach {
                val matchIDs = it.getStringList("match-item")
                val itemProviderID = it.getString("wrapper-item.hooked-plugin")
                val itemID = it.getString("wrapper-item.hooked-id")
                val itemWrapper = YamlUtils.tryBuildItem(itemProviderID, itemID, fileName, "broken-item-model.matches") ?: return@forEach
                BROKEN_MATCHES.add(BrokenMatchRule(matchIDs, itemWrapper))
            }
        }
    }
}