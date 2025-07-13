package top.catnies.firenchantkt.database.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import top.catnies.firenchantkt.FirEnchantPlugin;
import top.catnies.firenchantkt.database.Database;
import top.catnies.firenchantkt.language.MessageConstants;
import top.catnies.firenchantkt.util.MessageUtils;

public class MysqlDatabase implements Database {

    private HikariConfig config;
    private HikariDataSource source;

    public MysqlDatabase(HikariConfig config) {
        this.config = config;
    }

    @Override
    public void connect() {
        try {
            source = new HikariDataSource(config);
            createTable();
        } catch (Exception e) {
            MessageUtils.INSTANCE.sendTranslatableComponent(Bukkit.getConsoleSender(), MessageConstants.DATABASE_CONNECT_ERROR);
            Bukkit.getPluginManager().disablePlugin(FirEnchantPlugin.getInstance());
            e.printStackTrace();
        }
    }

    private void createTable() {

    }

    @Override
    public void close() {
        source.close();
    }
}
