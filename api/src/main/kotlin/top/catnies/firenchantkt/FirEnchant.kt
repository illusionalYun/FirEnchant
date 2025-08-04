package top.catnies.firenchantkt

import org.jetbrains.annotations.ApiStatus

// 不应当操作此类
@ApiStatus.Internal
interface FirEnchant {

    var isInitializedRegistry: Boolean

    fun initRegistry()

}