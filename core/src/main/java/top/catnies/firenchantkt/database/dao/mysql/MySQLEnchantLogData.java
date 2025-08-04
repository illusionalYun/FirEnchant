package top.catnies.firenchantkt.database.dao.mysql;

import com.j256.ormlite.table.TableUtils;
import org.bukkit.Bukkit;
import top.catnies.firenchantkt.FirEnchantPlugin;
import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.FirConnectionManager;
import top.catnies.firenchantkt.database.dao.AbstractDao;
import top.catnies.firenchantkt.database.dao.EnchantLogData;
import top.catnies.firenchantkt.database.entity.AnvilEnchantLogTable;
import top.catnies.firenchantkt.util.MessageUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static top.catnies.firenchantkt.language.MessageConstants.DATABASE_TABLE_CREATE_ERROR;

public class MySQLEnchantLogData extends AbstractDao<EnchantLogData, Integer> implements EnchantLogData {

    private static MySQLEnchantLogData instance;
    private static final int CURRENT_VERSION = 1;

    private MySQLEnchantLogData(){}
    public static MySQLEnchantLogData getInstance() {
        if (instance == null) {
            instance = new MySQLEnchantLogData();
            instance.createTable();
            ServiceContainer.INSTANCE.register(EnchantLogData.class, instance);
        }
        return instance;
    }

    private void createTable() {
        try {
            TableUtils.createTableIfNotExists(FirConnectionManager.getInstance().getConnectionSource(), AnvilEnchantLogTable.class);
        } catch (SQLException e) {
            // ORMLite 中如果表存在还是会重复创建 index 索引,所以需要忽略这个报错
            // if (e.getCause() != null && e.getCause().toString().contains("Duplicate key name")) return;
            MessageUtils.INSTANCE.sendTranslatableComponent(Bukkit.getConsoleSender(), DATABASE_TABLE_CREATE_ERROR, AnvilEnchantLogTable.class.getSimpleName());
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(FirEnchantPlugin.getInstance());
        }
    }

    @Override
    public void insert(AnvilEnchantLogTable anvilEnchantLogTable) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void cleanupOldRecords(int daysToKeep) {

    }

    @Override
    public List<AnvilEnchantLogTable> getAllList() {
        return List.of();
    }

    @Override
    public List<AnvilEnchantLogTable> getCountList(int max) {
        return List.of();
    }

    @Override
    public List<AnvilEnchantLogTable> getByPlayer(UUID uuid) {
        return List.of();
    }

    @Override
    public List<AnvilEnchantLogTable> getByPlayerRecent(UUID uuid, int max) {
        return List.of();
    }

    @Override
    public List<AnvilEnchantLogTable> getByPlayerAndEnchantment(UUID uuid, String enchantment) {
        return List.of();
    }

    @Override
    public List<AnvilEnchantLogTable> getByPlayerAndEnchantmentRecent(UUID uuid, String enchantment, int max) {
        return List.of();
    }
}
