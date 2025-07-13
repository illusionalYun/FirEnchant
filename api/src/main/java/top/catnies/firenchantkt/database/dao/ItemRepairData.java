package top.catnies.firenchantkt.database.dao;

import top.catnies.firenchantkt.database.entity.ItemRepairTable;

import java.util.List;
import java.util.UUID;

/**
 * 道具修复数据管理器接口
 */
public interface ItemRepairData {
    
    /**
     * 创建新的修复记录
     */
    void create(ItemRepairTable repairData);
    
    /**
     * 异步创建新的修复记录
     */
    void createAsync(ItemRepairTable repairData);
    
    /**
     * 更新修复记录
     */
    void update(ItemRepairTable repairData);
    
    /**
     * 异步更新修复记录
     */
    void updateAsync(ItemRepairTable repairData);
    
    /**
     * 根据ID获取修复记录
     */
    ItemRepairTable getById(int id);
    
    /**
     * 获取玩家所有的修复记录
     */
    List<ItemRepairTable> getByPlayer(UUID playerId);
    
    /**
     * 获取玩家未完成的修复记录
     */
    List<ItemRepairTable> getActiveRepairs(UUID playerId);
    
    /**
     * 获取所有已过期但未标记完成的记录
     */
    List<ItemRepairTable> getExpiredRepairs();
    
    /**
     * 标记修复为完成
     */
    void markAsCompleted(int id);
    
    /**
     * 删除修复记录
     */
    void delete(int id);
    
    /**
     * 异步删除修复记录
     */
    void deleteAsync(int id);
}