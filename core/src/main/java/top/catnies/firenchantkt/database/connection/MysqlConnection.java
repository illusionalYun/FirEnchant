package top.catnies.firenchantkt.database.connection;

import com.j256.ormlite.support.ConnectionSource;
import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import top.catnies.firenchantkt.FirEnchantPlugin;
import top.catnies.firenchantkt.database.Connection;
import top.catnies.firenchantkt.language.MessageConstants;
import top.catnies.firenchantkt.util.MessageUtils;

/**
 * MySQL数据库实现
 */
public class MysqlConnection implements Connection {

    private HikariConfig config;
    @Getter
    private ConnectionSource connectionSource;

    public MysqlConnection(HikariConfig config) {
        this.config = config;
    }

    @Override
    public void connect() {
        try {
            String jdbcUrl = config.getJdbcUrl();

            // 创建ORMLite连接源

            // 设置用户名和密码
            
            // 测试连接

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
