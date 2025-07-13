package top.catnies.firenchantkt.database.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "firenchant_enchanting_history")
public class EnchantingHistoryTable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, index = true)
    private UUID playerId;

    @DatabaseField(canBeNull = false)
    private String itemData; // 附魔的物品数据

    @DatabaseField(canBeNull = false)
    private int seed;

    @DatabaseField(canBeNull = false)
    private String previewEnchants; // JSON格式的结果 {enchantments: [{enchantment: key, level: 1}, {enchantment: key, level: 1}, {enchantment: key, level: 1}]}

    @DatabaseField(canBeNull = false)
    private long timestamp;

}

