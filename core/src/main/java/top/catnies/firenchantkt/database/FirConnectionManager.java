package top.catnies.firenchantkt.database;

import com.j256.ormlite.support.ConnectionSource;
import com.zaxxer.hikari.HikariConfig;
import lombok.SneakyThrows;
import top.catnies.firenchantkt.config.SettingsConfig;
import top.catnies.firenchantkt.database.connection.MysqlConnection;
import top.catnies.firenchantkt.database.connection.SQLiteConnection;

@SuppressWarnings("unused")
public class FirConnectionManager implements ConnectionManager {

    // 单例
    private static FirConnectionManager instance;
    private FirConnectionManager() {}
    
    public static FirConnectionManager getInstance() {
        if (instance == null) {
            instance = new FirConnectionManager();
            instance.initDatabase(); // 创建链接
        }
        return instance;
    }

    private Connection connection;

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
                connection = new MysqlConnection(hikariConfig);
                connection.connect();
            }
            // 初始化Sqlite数据库的连接
            case "sqlite" -> {
                String fileName = config.getDATABASE_SQLITE_FILE();
                String url = "jdbc:sqlite:plugins/FirOnlineTime/" + fileName;
                connection = new SQLiteConnection(url);
                connection.connect();
            }
            default -> throw new RuntimeException("不支持的数据库类型: " + type);
        }
    }

    @Override
    @SneakyThrows
    public void close() {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public ConnectionSource getConnectionSource() {
        return connection != null ? connection.getConnectionSource() : null;
    }
}
