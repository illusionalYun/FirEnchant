package top.catnies.firenchantkt.gui

interface EnchantingTableMenu {

    // 打开菜单
    fun openMenu(data: Map<String, Any>, async: Boolean = false)

    // 减少菜单容器内的物品数量
    fun clearInputInventory()

}