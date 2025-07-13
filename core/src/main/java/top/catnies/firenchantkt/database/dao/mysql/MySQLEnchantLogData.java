package top.catnies.firenchantkt.database.dao.mysql;

import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.dao.AbstractDao;
import top.catnies.firenchantkt.database.dao.EnchantLogData;

public class MySQLEnchantLogData extends AbstractDao<EnchantLogData, Integer> implements EnchantLogData {

    private static MySQLEnchantLogData Instance;

    private MySQLEnchantLogData(){}
    public static MySQLEnchantLogData getInstance() {
        if (Instance == null) {
            Instance = new MySQLEnchantLogData();
            ServiceContainer.INSTANCE.register(EnchantLogData.class, Instance);
        }
        return Instance;
    }

}
