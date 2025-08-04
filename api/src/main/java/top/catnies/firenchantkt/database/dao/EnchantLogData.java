package top.catnies.firenchantkt.database.dao;

import top.catnies.firenchantkt.database.entity.AnvilEnchantLogTable;

import java.util.List;
import java.util.UUID;

public interface EnchantLogData {

    /**
     * 更新指定玩家附魔日志数据实例在数据库中的数据
     *
     * @param anvilEnchantLogTable 玩家附魔日志数据实例
     */
    void insert(AnvilEnchantLogTable anvilEnchantLogTable);

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
    List<AnvilEnchantLogTable> getAllList();

    /**
     * 获取所有附魔日志数据实例列表(最近的)
     *
     * @param max 最大数量限制，-1表示无限制
     * @return 玩家附魔日志数据实例列表
     */
    List<AnvilEnchantLogTable> getCountList(int max);

    /**
     * 获取指定玩家UUID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @return 玩家附魔日志数据实例列表
     */
    List<AnvilEnchantLogTable> getByPlayer(UUID uuid);

    /**
     * 获取指定玩家UUID的玩家附魔日志数据实例列表(最近的)
     *
     * @param uuid 玩家UUID
     * @param max 最大数量限制
     * @return 玩家附魔日志数据实例列表
     */
    List<AnvilEnchantLogTable> getByPlayerRecent(UUID uuid, int max);

    /**
     * 获取指定玩家UUID下指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @param enchantment 附魔ID
     * @return 玩家附魔日志数据实例列表
     */
    List<AnvilEnchantLogTable> getByPlayerAndEnchantment(UUID uuid, String enchantment);

    /**
     * 获取指定玩家UUID下指定附魔ID的玩家附魔日志数据实例列表(最近的)
     *
     * @param uuid 玩家UUID
     * @param enchantment 附魔ID
     * @param max 最大数量限制，-1表示无限制
     * @return 玩家附魔日志数据实例列表
     */
    List<AnvilEnchantLogTable> getByPlayerAndEnchantmentRecent(UUID uuid, String enchantment, int max);

}