package top.catnies.firenchantkt.database;

import top.catnies.firenchantkt.database.entity.AnvilEnchantLogTable;
import top.catnies.firenchantkt.database.entity.EnchantingHistoryTable;

import java.util.List;
import java.util.UUID;

public interface CacheManager {

    /**
     * 获取上一次附魔的种子, 如果没有则返回 -1 .
     * @param playerUUID 玩家UUID
     * @return 种子值
     */
    int getLastEnchantingTableSeed(UUID playerUUID);

    /**
     * 获取最近20次的铁砧成功记录
     * @param playerUUID 玩家UUID
     * @return 铁砧日志
     */
    List<AnvilEnchantLogTable> getRecentAnvilEnchantLogs(UUID playerUUID);

    /**
     * 推入新的附魔台记录
     */
    void addEnchantLog(AnvilEnchantLogTable log);


    /**
     * 推入附魔历史记录
     */
    void addEnchantingHistory(EnchantingHistoryTable history);


    /**
     * 清理指定玩家的缓存
     */
    void clearPlayerCache(UUID playerUUID);

}
