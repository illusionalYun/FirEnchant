package top.catnies.firenchantkt.database.connection;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import lombok.Getter;
import org.bukkit.Bukkit;
import top.catnies.firenchantkt.FirEnchantPlugin;
import top.catnies.firenchantkt.database.Connection;
import top.catnies.firenchantkt.language.MessageConstants;
import top.catnies.firenchantkt.util.MessageUtils;

/**
 * SQLite数据库实现
 */
public class SQLiteConnection implements Connection {

    private final String jdbcUrl;
    @Getter
    private ConnectionSource connectionSource;

    public SQLiteConnection(String url) {
        this.jdbcUrl = url;
    }

    @Override
    public void connect() {
        try {
            // 创建ORMLite连接源
            connectionSource = new JdbcConnectionSource(jdbcUrl);
            connectionSource.getReadWriteConnection(null);
        } catch (Exception e) {
            MessageUtils.INSTANCE.sendTranslatableComponent(Bukkit.getConsoleSender(), MessageConstants.DATABASE_CONNECT_ERROR);
            Bukkit.getPluginManager().disablePlugin(FirEnchantPlugin.getInstance());
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (connectionSource != null) {
                connectionSource.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
