package top.catnies.firenchantkt.database

import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull

// 数据库接口
interface Database {

    /* 记录使用附魔书的成功和失败历史记录 */

    /* 获取使用附魔书的成功和失败历史记录 */

    /* 获取附魔台的种子 */
    fun getEnchantTableSeed(@NotNull player: Player): Int

    /* 设置附魔台的种子 */
    fun setEnchantTableSeed(@NotNull player: Player, @NotNull seed: Long)



}