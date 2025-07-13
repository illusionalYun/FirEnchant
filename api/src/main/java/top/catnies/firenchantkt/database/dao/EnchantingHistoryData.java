package top.catnies.firenchantkt.database.dao;

import top.catnies.firenchantkt.database.entity.EnchantingHistoryTable;

import java.util.List;
import java.util.UUID;

/**
 * 附魔历史数据管理器接口
 */
public interface EnchantingHistoryData {
    
    /**
     * 创建新的附魔历史记录
     */
    void create(EnchantingHistoryTable historyData);
    
    /**
     * 异步创建新的附魔历史记录
     */
    void createAsync(EnchantingHistoryTable historyData);
    
    /**
     * 根据ID获取附魔历史记录
     */
    EnchantingHistoryTable getById(int id);
    
    /**
     * 获取玩家的附魔历史记录
     * @param playerId 玩家UUID
     * @param limit 限制返回数量，-1表示不限制
     */
    List<EnchantingHistoryTable> getByPlayer(UUID playerId, int limit);
    
    /**
     * 获取指定物品类型的附魔历史
     * @param itemType 物品类型
     * @param limit 限制返回数量
     */
    List<EnchantingHistoryTable> getByItemType(String itemType, int limit);
    
    /**
     * 获取指定时间范围内的附魔历史
     * @param startTime 开始时间戳
     * @param endTime 结束时间戳
     * @param limit 限制返回数量
     */
    List<EnchantingHistoryTable> getByTimeRange(long startTime, long endTime, int limit);
    
    /**
     * 获取指定世界的附魔历史
     * @param worldName 世界名称
     * @param limit 限制返回数量
     */
    List<EnchantingHistoryTable> getByWorld(String worldName, int limit);
    
    /**
     * 获取最近的附魔历史记录
     * @param limit 限制返回数量
     */
    List<EnchantingHistoryTable> getRecent(int limit);
    
    /**
     * 删除指定ID的记录
     */
    void delete(int id);
    
    /**
     * 异步删除指定ID的记录
     */
    void deleteAsync(int id);
    
    /**
     * 清理过期的历史记录
     * @param daysToKeep 保留天数
     */
    void cleanupOldRecords(int daysToKeep);
}