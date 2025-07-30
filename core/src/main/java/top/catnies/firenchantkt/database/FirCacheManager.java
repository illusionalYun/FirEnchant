package top.catnies.firenchantkt.database;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.EvictingQueue;
import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.entity.AnvilEnchantLogTable;
import top.catnies.firenchantkt.database.entity.EnchantingHistoryTable;

import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class FirCacheManager implements CacheManager {

    private static final int MAX_RECORDS_PER_PLAYER = 20;
    private static final int CACHE_EXPIRE_HOURS = 365;

    private static FirCacheManager instance;

    private FirCacheManager() {}

    public static FirCacheManager getInstance() {
        if (instance == null) {
            instance = new FirCacheManager();
            ServiceContainer.INSTANCE.register(CacheManager.class, instance);
        }
        return instance;
    }

    // 使用 Guava Cache + EvictingQueue 实现有界缓存
    private final Cache<UUID, Queue<AnvilEnchantLogTable>> enchantLogsCache =
            CacheBuilder.newBuilder()
                    .expireAfterAccess(CACHE_EXPIRE_HOURS, TimeUnit.DAYS) // 2小时未访问则过期
                    .build();

    private final Cache<UUID, Queue<EnchantingHistoryTable>> enchantingHistoryCache =
            CacheBuilder.newBuilder()
                    .expireAfterAccess(CACHE_EXPIRE_HOURS, TimeUnit.DAYS)
                    .build();

    // 获取最后一次附魔的种子
    @Override
    public int getLastEnchantingTableSeed(UUID playerUUID) {
        Queue<EnchantingHistoryTable> data = enchantingHistoryCache.getIfPresent(playerUUID);
        if (data == null || data.isEmpty()) return -1;

        // 获取队列中最后一个元素
        EnchantingHistoryTable last = null;
        for (EnchantingHistoryTable item : data) {
            last = item;
        }
        return last != null ? last.getSeed() : -1;
    }

    // 获取最近20次的铁砧成功记录
    @Override
    public List<EnchantingHistoryTable> getRecentAnvilEnchantLogs(UUID playerUUID) {
        Queue<EnchantingHistoryTable> data = enchantingHistoryCache.getIfPresent(playerUUID);
        if (data == null || data.isEmpty()) return List.of();
        return data.stream().limit(MAX_RECORDS_PER_PLAYER).toList();
    }

    // 添加附魔台记录
    @Override
    public void addEnchantLog(UUID playerUUID, AnvilEnchantLogTable log) {
        Queue<AnvilEnchantLogTable> queue = enchantLogsCache.getIfPresent(playerUUID);
        if (queue == null) {
            queue = EvictingQueue.create(MAX_RECORDS_PER_PLAYER);
            enchantLogsCache.put(playerUUID, queue);
        }
        queue.offer(log); // 自动淘汰最旧的记录
    }

    // 添加附魔历史记录
    @Override
    public void addEnchantingHistory(UUID playerUUID, EnchantingHistoryTable history) {
        Queue<EnchantingHistoryTable> queue = enchantingHistoryCache.getIfPresent(playerUUID);
        if (queue == null) {
            queue = EvictingQueue.create(MAX_RECORDS_PER_PLAYER);
            enchantingHistoryCache.put(playerUUID, queue);
        }
        queue.offer(history);
    }

    // 清理指定玩家的缓存
    @Override
    public void clearPlayerCache(UUID playerUUID) {
        enchantLogsCache.invalidate(playerUUID);
        enchantingHistoryCache.invalidate(playerUUID);
    }

    // 获取缓存统计信息
    public void printCacheStats() {
        System.out.println("EnchantLogs Cache Stats: " + enchantLogsCache.stats());
        System.out.println("EnchantingHistory Cache Stats: " + enchantingHistoryCache.stats());
    }
}
