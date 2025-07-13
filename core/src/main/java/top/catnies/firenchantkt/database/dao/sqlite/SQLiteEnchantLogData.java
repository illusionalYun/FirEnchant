package top.catnies.firenchantkt.database.dao.sqlite;

import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.dao.AbstractDao;
import top.catnies.firenchantkt.database.dao.EnchantLogData;

public class SQLiteEnchantLogData extends AbstractDao<EnchantLogData, Integer> implements EnchantLogData{

    private static SQLiteEnchantLogData Instance;

    private SQLiteEnchantLogData(){}
    public static SQLiteEnchantLogData getInstance() {
        if (Instance == null) {
            Instance = new SQLiteEnchantLogData();
            ServiceContainer.INSTANCE.register(EnchantLogData.class, Instance);
        }
        return Instance;
    }

}
