package top.catnies.firenchantkt.gui

import org.bukkit.enchantments.Enchantment

interface EnchantingTableMenu {

    // 打开菜单
    fun openMenu(data: Map<String, Any>, async: Boolean = false)

    // 清空容器
    fun clearInputInventory()

    // 设置附魔台结果, 失败率来源于玩家的 seed + 0,1,2 生成随机数
    fun setEnchantmentResult(list: List<Map<Enchantment, Integer>>)

    // 检查玩家现在能点亮多少个栏位
    fun checkCanLight(): Int

}