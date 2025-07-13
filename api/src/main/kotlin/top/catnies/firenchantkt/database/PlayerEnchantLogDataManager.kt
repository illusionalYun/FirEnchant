package top.catnies.firenchantkt.database

import top.catnies.firenchantkt.database.table.EnchantLogDataTable
import java.util.*

interface PlayerEnchantLogDataManager {

    /**
     * 获取玩家附魔日志数据实例列表
     *
     * @return 玩家附魔日志数据实例列表
     */
    fun getList(max: Int = -1): MutableList<EnchantLogDataTable?>?

    /**
     * 获取指定玩家UUID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @return 玩家附魔日志数据实例列表
     */
    fun getList(uuid: UUID?, max: Int = -1): MutableList<EnchantLogDataTable?>?

    /**
     * 获取指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param enchantment 附魔ID
     * @return 玩家附魔日志数据实例列表
     */
    fun getList(enchantment: String?, max: Int = -1): MutableList<EnchantLogDataTable?>?

    /**
     * 获取指定玩家UUID下指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @param enchantment 附魔ID
     * @return 玩家附魔日志数据实例列表
     */
    fun getList(uuid: UUID?, enchantment: String?, max: Int = -1): MutableList<EnchantLogDataTable?>?

    /**
     * 更新指定玩家附魔日志数据实例 在数据库中的数据
     *
     * @param enchantLogDataTable 玩家附魔日志数据实例
     * @param async  异步处理
     */
    fun update(enchantLogDataTable: EnchantLogDataTable?, async: Boolean)

    /**
     * 更新指定玩家附魔日志数据实例在数据库中的数据
     *
     * @param enchantLogDataTable 玩家附魔日志数据实例
     */
    fun update(enchantLogDataTable: EnchantLogDataTable?)

}