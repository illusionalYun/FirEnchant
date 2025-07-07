package top.catnies.firenchantkt.database;

import top.catnies.firenchantkt.entity.PlayerEnchantLogData;

import java.util.List;
import java.util.UUID;

public interface PlayerEnchantLogDataManager {
    /**
     * 获取玩家附魔日志数据实例列表
     *
     * @return 玩家附魔日志数据实例列表
     */
    List<PlayerEnchantLogData> getList();

    /**
     * 获取指定玩家UUID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @return 玩家附魔日志数据实例列表
     */
    List<PlayerEnchantLogData> getList(UUID uuid);

    /**
     * 获取指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param enchantment 附魔ID
     * @return 玩家附魔日志数据实例列表
     */
    List<PlayerEnchantLogData> getList(String enchantment);

    /**
     * 获取指定玩家UUID下指定附魔ID的玩家附魔日志数据实例列表
     *
     * @param uuid 玩家UUID
     * @param enchantment 附魔ID
     * @return 玩家附魔日志数据实例列表
     */
    List<PlayerEnchantLogData> getList(UUID uuid, String enchantment);

    /**
     * 更新指定玩家附魔日志数据实例 在数据库中的数据
     *
     * @param playerEnchantLogData 玩家附魔日志数据实例
     * @param async  异步处理
     */
    void update(PlayerEnchantLogData playerEnchantLogData, boolean async);

    /**
     * 更新指定玩家附魔日志数据实例在数据库中的数据
     *
     * @param playerEnchantLogData 玩家附魔日志数据实例
     */
    void update(PlayerEnchantLogData playerEnchantLogData);
}
