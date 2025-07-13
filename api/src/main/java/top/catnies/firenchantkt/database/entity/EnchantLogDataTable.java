package top.catnies.firenchantkt.database.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * 记录玩家在铁砧上操作附魔书的历史记录
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "firenchant_enchartlog")
public class EnchantLogDataTable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(index = true, canBeNull = false, dataType = DataType.UUID)
    private UUID player;

    @DatabaseField(index = true, canBeNull = false, dataType = DataType.LONG_STRING)
    private String enchantment; // Json数据 {enchantment: key, level: 1}

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    private int takeLevel;

    @DatabaseField(canBeNull = false, dataType = DataType.SHORT)
    private short failure;

    @DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN)
    private boolean success;

    @DatabaseField(canBeNull = false, dataType = DataType.LONG)
    private long timestamp;
}
