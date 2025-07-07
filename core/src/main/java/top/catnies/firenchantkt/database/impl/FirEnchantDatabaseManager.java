package top.catnies.firenchantkt.database.impl;

import com.j256.ormlite.table.TableUtils;
import lombok.SneakyThrows;
import top.catnies.firenchantkt.FirEnchantPlugin;
import top.catnies.firenchantkt.config.SettingsConfig;
import top.catnies.firenchantkt.database.AbstractDatabaseManager;
import top.catnies.firenchantkt.entity.DatabaseConfig;
import top.catnies.firenchantkt.entity.PlayerEnchantLogData;

import java.io.File;

public class FirEnchantDatabaseManager extends AbstractDatabaseManager {
    public FirEnchantDatabaseManager() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setType(SettingsConfig.getInstance().DATABASE_TYPE);

        databaseConfig.setHost(SettingsConfig.getInstance().DATABASE_MYSQL_HOST);
        databaseConfig.setDatabase(SettingsConfig.getInstance().DATABASE_MYSQL_DATABASE);
        databaseConfig.setUser(SettingsConfig.getInstance().DATABASE_MYSQL_USER);
        databaseConfig.setPassword(SettingsConfig.getInstance().DATABASE_MYSQL_PASSWORD);

        databaseConfig.setFile(
                new File(FirEnchantPlugin.getInstance().getDataFolder(), SettingsConfig.getInstance().DATABASE_H2_FILE)
        );

        setConfig(databaseConfig);
    }


    @SneakyThrows
    public void initTable() {
        try {
            TableUtils.createTableIfNotExists(getConnectionSource(), PlayerEnchantLogData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
