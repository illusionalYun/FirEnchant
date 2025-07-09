package top.catnies.firenchantkt.database.impl;

import com.j256.ormlite.table.TableUtils;
import lombok.SneakyThrows;
import top.catnies.firenchantkt.FirEnchantPlugin;
import top.catnies.firenchantkt.config.SettingsConfig;
import top.catnies.firenchantkt.database.AbstractDatabaseManager;
import top.catnies.firenchantkt.entity.DatabaseConfig;
import top.catnies.firenchantkt.database.PlayerEnchantLogData;

import java.io.File;

public class FirEnchantDatabaseManager extends AbstractDatabaseManager {

    private static FirEnchantDatabaseManager instance;

    private FirEnchantDatabaseManager() {}

    public static FirEnchantDatabaseManager getInstance() {
        if (instance == null) {
            instance = new FirEnchantDatabaseManager();
            instance.crateDatabaseConfig();
            instance.connect();
            instance.initTable();
        }
        return instance;
    }

    // 创建数据库配置
    private void crateDatabaseConfig() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setType(SettingsConfig.getInstance().DATABASE_TYPE);

        databaseConfig.setHost(SettingsConfig.getInstance().DATABASE_MYSQL_HOST);
        databaseConfig.setDatabase(SettingsConfig.getInstance().DATABASE_MYSQL_DATABASE);
        databaseConfig.setUser(SettingsConfig.getInstance().DATABASE_MYSQL_USER);
        databaseConfig.setPassword(SettingsConfig.getInstance().DATABASE_MYSQL_PASSWORD);

        File h2 = new File(FirEnchantPlugin.getInstance().getDataFolder(), SettingsConfig.getInstance().DATABASE_H2_FILE);
        databaseConfig.setFile(h2);

        this.setConfig(databaseConfig);
    }

    // 创建数据库表
    @SneakyThrows
    public void initTable() {
        try {
            TableUtils.createTableIfNotExists(getConnectionSource(), PlayerEnchantLogData.class);
        } catch (Exception e) {
            if (!e.getCause().toString().contains("Duplicate key name")) {
                throw new RuntimeException(e);
            }
        }
    }
}
