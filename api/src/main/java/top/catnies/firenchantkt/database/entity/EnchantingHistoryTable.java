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
 * 附魔台历史记录表
 * 用于记录玩家在附魔台上的附魔历史
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "firenchant_enchanting_history")
public class EnchantingHistoryTable {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(canBeNull = false, index = true, dataType = DataType.UUID)
    private UUID playerId;
    
    @DatabaseField(canBeNull = false, dataType = DataType.LONG_STRING)
    private String inputItemData; // 物品类型
    
    @DatabaseField(canBeNull = false, dataType = DataType.LONG)
    private int seed; // 附魔种子
    
    @DatabaseField(canBeNull = false, dataType = DataType.LONG_STRING)
    private String enchants; // JSON格式的预览结果 {enchantments: [{enchantment: key, level: 1}, {enchantment: key, level: 1}, {enchantment: key, level: 1}]}

    @DatabaseField(canBeNull = false, dataType = DataType.LONG)
    private long timestamp;
}

