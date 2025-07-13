package top.catnies.firenchantkt.database.dao.mysql;

import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.dao.AbstractDao;
import top.catnies.firenchantkt.database.dao.ItemRepairData;

public class MySQLItemRepairData extends AbstractDao<ItemRepairData, Integer> implements ItemRepairData {

    private static MySQLItemRepairData Instance;

    private MySQLItemRepairData(){}
    public static MySQLItemRepairData getInstance() {
        if (Instance == null) {
            Instance = new MySQLItemRepairData();
            ServiceContainer.INSTANCE.register(ItemRepairData.class, Instance);
        }
        return Instance;
    }

}
