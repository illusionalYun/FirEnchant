package top.catnies.firenchantkt.database.dao;

import top.catnies.firenchantkt.database.entity.EnchantLogDataTable;

import java.util.List;
import java.util.UUID;

public interface EnchantLogData {

    /**
     * 更新指定玩家附魔日志数据实例在数据库中的数据
     *
     * @param enchantLogDataTable 玩家附魔日志数据实例
     */
    void insert(EnchantLogDataTable enchantLogDataTable);

    /**
     * 删除指定ID的记录
     */
    void delete(int id);

    /**
     * 清理过期的历史记录
     * @param daysToKeep 保留天数
     */
    void cleanupOldRecords(int daysToKeep);

    /**
     * 获取所有附魔日志数据实例列表
     *
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getAllList();

    /**
     * 获取所有附魔日志数据实例列表(最近的)
     *
     * @param max 最大数量限制，-1表示无限制
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getCountList(int max);

    /**
     * 获取指定玩家UUID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getByPlayer(UUID uuid);

    /**
     * 获取指定玩家UUID的玩家附魔日志数据实例列表(最近的)
     *
     * @param uuid 玩家UUID
     * @param max 最大数量限制
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getByPlayerRecent(UUID uuid, int max);

    /**
     * 获取指定玩家UUID下指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @param enchantment 附魔ID
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getByPlayerAndEnchantment(UUID uuid, String enchantment);

    /**
     * 获取指定玩家UUID下指定附魔ID的玩家附魔日志数据实例列表(最近的)
     *
     * @param uuid 玩家UUID
     * @param enchantment 附魔ID
     * @param max 最大数量限制，-1表示无限制
     * @return 玩家附魔日志数据实例列表
     */
    List<EnchantLogDataTable> getByPlayerAndEnchantmentRecent(UUID uuid, String enchantment, int max);

}