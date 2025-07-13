package top.catnies.firenchantkt.database.dao.mysql;

import com.j256.ormlite.table.TableUtils;
import org.bukkit.Bukkit;
import top.catnies.firenchantkt.FirEnchantPlugin;
import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.FirConnectionManager;
import top.catnies.firenchantkt.database.dao.AbstractDao;
import top.catnies.firenchantkt.database.dao.EnchantingHistoryData;
import top.catnies.firenchantkt.database.entity.EnchantingHistoryTable;
import top.catnies.firenchantkt.util.MessageUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static top.catnies.firenchantkt.language.MessageConstants.DATABASE_TABLE_CREATE_ERROR;

public class MySQLEnchantingHistoryData extends AbstractDao<EnchantingHistoryData, Integer> implements EnchantingHistoryData {

    private static MySQLEnchantingHistoryData Instance;

    private MySQLEnchantingHistoryData(){}
    public static MySQLEnchantingHistoryData getInstance() {
        if (Instance == null) {
            Instance = new MySQLEnchantingHistoryData();
            Instance.createTable();
            ServiceContainer.INSTANCE.register(EnchantingHistoryData.class, Instance);
        }
        return Instance;
    }

    private void createTable() {
        try {
            TableUtils.createTable(FirConnectionManager.getInstance().getConnectionSource(), EnchantingHistoryTable.class);
        } catch (SQLException e) {
            MessageUtils.INSTANCE.sendTranslatableComponent(Bukkit.getConsoleSender(), DATABASE_TABLE_CREATE_ERROR, EnchantingHistoryTable.class.getSimpleName());
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(FirEnchantPlugin.getInstance());
        }
    }


    @Override
    public void create(EnchantingHistoryTable historyData) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void cleanupOldRecords(int daysToKeep) {

    }

    @Override
    public List<EnchantingHistoryTable> getByPlayer(UUID playerId) {
        return List.of();
    }

    @Override
    public List<EnchantingHistoryTable> getByPlayerRecent(UUID playerId, int limit) {
        return List.of();
    }

    @Override
    public List<EnchantingHistoryTable> getByTimeRange(long startTime, long endTime, int limit) {
        return List.of();
    }

    @Override
    public List<EnchantingHistoryTable> getRecent(int limit) {
        return List.of();
    }
}
