package top.catnies.firenchantkt.database;

import com.j256.ormlite.support.ConnectionSource;
import top.catnies.firenchantkt.database.dao.EnchantLogData;
import top.catnies.firenchantkt.database.dao.EnchantingHistoryData;
import top.catnies.firenchantkt.database.dao.ItemRepairData;

public interface ConnectionManager {

    // 根据配置文件选择数据库并初始化数据库
    void initDatabase();

    // 关闭数据库连接源
    void close();

    // 获取数据库链接源
    ConnectionSource getConnectionSource();

    // 获取DAO类
    EnchantingHistoryData getEnchantingHistoryData();

    EnchantLogData getEnchantLogData();

    ItemRepairData getItemRepairData();
}
