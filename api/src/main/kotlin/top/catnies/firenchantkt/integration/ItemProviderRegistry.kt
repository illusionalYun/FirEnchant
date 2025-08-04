package top.catnies.firenchantkt.integration

import org.jetbrains.annotations.NotNull
import javax.annotation.Nullable

/**
 * 第三方插件物品注册管理器, 当你监听到事件时, 插件已经将默认支持的ItemProvider已经注册完成.
 */
interface ItemProviderRegistry {

    @Nullable
    fun registerItemProvider(plugin: String, provider: ItemProvider): ItemProvider?

    @Nullable
    fun unregisterItemProvider(plugin: String): ItemProvider?

    @NotNull
    fun getItemProviders(): List<ItemProvider>

    @Nullable
    fun getItemProvider(plugin: String): ItemProvider?

}