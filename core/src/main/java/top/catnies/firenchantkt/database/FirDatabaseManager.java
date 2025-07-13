package top.catnies.firenchantkt.database;

import com.zaxxer.hikari.HikariConfig;
import lombok.SneakyThrows;
import top.catnies.firenchantkt.config.SettingsConfig;
import top.catnies.firenchantkt.database.impl.MysqlDatabase;
import top.catnies.firenchantkt.database.impl.SQLiteDatabase;

@SuppressWarnings("unused")
public class FirDatabaseManager implements DatabaseManager {

    // 单例
    public static FirDatabaseManager instance;
    private FirDatabaseManager() {}
    public static FirDatabaseManager getInstance() {
        if (instance == null) {
            instance = new FirDatabaseManager();
            instance.initDatabase(); // 创建链接
        }
        return instance;
    }

    private Database database;

    @Override
    public void initDatabase() {
        SettingsConfig config = SettingsConfig.getInstance();
        String type = config.getDATABASE_TYPE().toLowerCase();
        switch (type) {
            // 初始化MySQL数据库的连接
            case "mysql" -> {
                HikariConfig hikariConfig = new HikariConfig();
                hikariConfig.setJdbcUrl(config.getDATABASE_MYSQL_JDBC_URL());
                hikariConfig.setDriverClassName(config.getDATABASE_MYSQL_JDBC_CLASS());
                hikariConfig.setUsername(config.getDATABASE_MYSQL_USER());
                hikariConfig.setPassword(config.getDATABASE_MYSQL_PASSWORD());
                database = new MysqlDatabase(hikariConfig);
            }
            // 初始化Sqlite数据库的连接
            case "sqlite" -> {
                String fileName = config.getDATABASE_SQLITE_FILE();
                String url = "jdbc:sqlite:plugins/FirOnlineTime/" + fileName;
                database = new SQLiteDatabase(url);
            }
            default -> throw new RuntimeException("不支持的数据库类型: " + type);
        }
    }


    @Override
    @SneakyThrows
    public void close() {
        database.close();
    }

    @Override
    public Database getConnectionSource() {
        return database;
    }

}
