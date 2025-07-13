package top.catnies.firenchantkt.database.dao.mysql;

import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.dao.AbstractDao;
import top.catnies.firenchantkt.database.dao.EnchantingHistoryData;

public class MySQLEnchantingHistoryData extends AbstractDao<EnchantingHistoryData, Integer> implements EnchantingHistoryData {

    private static MySQLEnchantingHistoryData Instance;

    private MySQLEnchantingHistoryData(){}
    public static MySQLEnchantingHistoryData getInstance() {
        if (Instance == null) {
            Instance = new MySQLEnchantingHistoryData();
            ServiceContainer.INSTANCE.register(EnchantingHistoryData.class, Instance);
        }
        return Instance;
    }

}
