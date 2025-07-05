package top.catnies.firenchantkt.integration

/**
 * 第三方插件物品注册管理器, 当你监听到事件时, 插件已经将默认支持的ItemProvider已经注册完成.
 */
interface ItemProviderRegistry {

    fun registerItemProvider(plugin: String, provider: ItemProvider): ItemProvider?

    fun unregisterItemProvider(plugin: String): ItemProvider?

    fun getItemProviders(): List<ItemProvider>

    fun getItemProvider(plugin: String): ItemProvider?

}