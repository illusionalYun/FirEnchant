package top.catnies.firenchantkt.database.dao.sqlite;

import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.dao.AbstractDao;
import top.catnies.firenchantkt.database.dao.EnchantingHistoryData;

public class SQLiteEnchantingHistoryData extends AbstractDao<EnchantingHistoryData, Integer> implements EnchantingHistoryData {

    private static SQLiteEnchantingHistoryData Instance;

    private SQLiteEnchantingHistoryData(){}
    public static SQLiteEnchantingHistoryData getInstance() {
        if (Instance == null) {
            Instance = new SQLiteEnchantingHistoryData();
            ServiceContainer.INSTANCE.register(EnchantingHistoryData.class, Instance);
        }
        return Instance;
    }

}
