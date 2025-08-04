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
     * 删除指定ID的记录
     */
    void delete(int id);

    /**
     * 清理过期的历史记录
     * @param daysToKeep 保留天数
     */
    void cleanupOldRecords(int daysToKeep);

    /**
     * 获取玩家的附魔历史记录
     * @param playerId 玩家UUID
     */
    List<EnchantingHistoryTable> getByPlayer(UUID playerId);

    /**
     * 获取玩家的附魔历史记录, 有数量限制时返回最近的
     * @param playerId 玩家UUID
     * @param limit 限制返回数量
     */
    List<EnchantingHistoryTable> getByPlayerRecent(UUID playerId, int limit);

    /**
     * 获取指定时间范围内的附魔历史
     * @param startTime 开始时间戳
     * @param endTime 结束时间戳
     * @param limit 限制返回数量
     */
    List<EnchantingHistoryTable> getByTimeRange(long startTime, long endTime, int limit);
    
    /**
     * 获取最近的附魔历史记录
     * @param limit 限制返回数量
     */
    List<EnchantingHistoryTable> getRecent(int limit);
}