package top.catnies.firenchantkt.database.dao.mysql;

import com.j256.ormlite.table.TableUtils;
import org.bukkit.Bukkit;
import top.catnies.firenchantkt.FirEnchantPlugin;
import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.FirConnectionManager;
import top.catnies.firenchantkt.database.dao.AbstractDao;
import top.catnies.firenchantkt.database.dao.ItemRepairData;
import top.catnies.firenchantkt.database.entity.ItemRepairTable;
import top.catnies.firenchantkt.util.MessageUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static top.catnies.firenchantkt.language.MessageConstants.DATABASE_TABLE_CREATE_ERROR;

public class MySQLItemRepairData extends AbstractDao<ItemRepairData, Integer> implements ItemRepairData {

    private static MySQLItemRepairData instance;
    private static final int CURRENT_VERSION = 1;

    private MySQLItemRepairData(){}
    public static MySQLItemRepairData getInstance() {
        if (instance == null) {
            instance = new MySQLItemRepairData();
            instance.createTable();
            ServiceContainer.INSTANCE.register(ItemRepairData.class, instance);
        }
        return instance;
    }

    private void createTable() {
        try {
            TableUtils.createTableIfNotExists(FirConnectionManager.getInstance().getConnectionSource(), ItemRepairTable.class);
        } catch (SQLException e) {
            // ORMLite 中如果表存在还是会重复创建 index 索引,所以需要忽略这个报错
            // if (e.getCause() != null && e.getCause().toString().contains("Duplicate key name")) return;
            MessageUtils.INSTANCE.sendTranslatableComponent(Bukkit.getConsoleSender(), DATABASE_TABLE_CREATE_ERROR, ItemRepairTable.class.getSimpleName());
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(FirEnchantPlugin.getInstance());
        }
    }

    @Override
    public void insert(ItemRepairTable repairData) {

    }

    @Override
    public void markAsReceived(int id) {

    }

    @Override
    public List<ItemRepairTable> getAllList() {
        return List.of();
    }

    @Override
    public List<ItemRepairTable> getAllActiveList() {
        return List.of();
    }

    @Override
    public List<ItemRepairTable> getAllCompletedList() {
        return List.of();
    }

    @Override
    public List<ItemRepairTable> getAllReceivedList() {
        return List.of();
    }

    @Override
    public List<ItemRepairTable> getAllActiveAndCompletedList() {
        return List.of();
    }

    @Override
    public List<ItemRepairTable> getByPlayer(UUID playerId) {
        return List.of();
    }

    @Override
    public List<ItemRepairTable> getByPlayerActive(UUID playerId) {
        return List.of();
    }

    @Override
    public List<ItemRepairTable> getByPlayerCompleted(UUID playerId) {
        return List.of();
    }

    @Override
    public List<ItemRepairTable> getByPlayerReceived(UUID playerId) {
        return List.of();
    }

    @Override
    public List<ItemRepairTable> getByPlayerActiveAndCompletedList(UUID playerId) {
        return List.of();
    }
}
