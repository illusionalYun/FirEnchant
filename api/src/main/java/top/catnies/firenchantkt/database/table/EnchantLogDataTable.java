package top.catnies.firenchantkt.database.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@DatabaseTable(tableName = "firenchant_enchartlog")
public class EnchantLogDataTable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(index = true, canBeNull = false)
    private UUID player;

    @DatabaseField(index = true, canBeNull = false)
    private String enchantment; // Json数据 {enchantment: key, level: 1}

    @DatabaseField(canBeNull = false)
    private int takeLevel;

    @DatabaseField(canBeNull = false)
    private int failure;

    @DatabaseField(canBeNull = false)
    private boolean success;

    @DatabaseField(canBeNull = false)
    private long timestamp;
}
