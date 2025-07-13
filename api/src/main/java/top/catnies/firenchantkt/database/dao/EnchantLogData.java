package top.catnies.firenchantkt.database.dao;

import top.catnies.firenchantkt.database.entity.EnchantLogDataTable;

import java.util.List;
import java.util.UUID;

public interface EnchantLogData {

    /**
     * 获取玩家附魔日志数据实例列表
     *
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getList();

    /**
     * 获取玩家附魔日志数据实例列表
     *
     * @param max 最大数量限制，-1表示无限制
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getList(int max);

    /**
     * 获取指定玩家UUID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getList(UUID uuid);

    /**
     * 获取指定玩家UUID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @param max 最大数量限制，-1表示无限制
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getList(UUID uuid, int max);

    /**
     * 获取指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param enchantment 附魔ID
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getList(String enchantment);

    /**
     * 获取指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param enchantment 附魔ID
     * @param max 最大数量限制，-1表示无限制
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getList(String enchantment, int max);

    /**
     * 获取指定玩家UUID下指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @param enchantment 附魔ID
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getList(UUID uuid, String enchantment);

    /**
     * 获取指定玩家UUID下指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @param enchantment 附魔ID
     * @param max 最大数量限制，-1表示无限制
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getList(UUID uuid, String enchantment, int max);

    /**
     * 更新指定玩家附魔日志数据实例 在数据库中的数据
     *
     * @param enchantLogDataTable 玩家附魔日志数据实例
     * @param async 异步处理
     */
    void update(EnchantLogDataTable enchantLogDataTable, boolean async);

    /**
     * 更新指定玩家附魔日志数据实例在数据库中的数据
     *
     * @param enchantLogDataTable 玩家附魔日志数据实例
     */
    void update(EnchantLogDataTable enchantLogDataTable);

}