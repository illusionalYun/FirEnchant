package top.catnies.firenchantkt.database.dao.sqlite;

import top.catnies.firenchantkt.api.ServiceContainer;
import top.catnies.firenchantkt.database.dao.AbstractDao;
import top.catnies.firenchantkt.database.dao.ItemRepairData;

public class SQLiteItemRepairData extends AbstractDao<ItemRepairData, Integer> implements ItemRepairData {

    private static SQLiteItemRepairData Instance;

    private SQLiteItemRepairData(){}
    public static SQLiteItemRepairData getInstance() {
        if (Instance == null) {
            Instance = new SQLiteItemRepairData();
            ServiceContainer.INSTANCE.register(ItemRepairData.class, Instance);
        }
        return Instance;
    }

}
