package top.catnies.firenchantkt.database.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "firenchant_repair")
public class ItemRepairTable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, index = true)
    private UUID playerId;

    @DatabaseField(canBeNull = false)
    private String itemData; // 序列化的物品数据

    @DatabaseField(canBeNull = false)
    private long startTime; // 开始修复的时间

    @DatabaseField(canBeNull = false)
    private long duration; // 修复所需时间（毫秒）

    @DatabaseField(canBeNull = false)
    private long completeTime; // 修复完成的时间

}
