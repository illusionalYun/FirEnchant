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
    
    @DatabaseField(canBeNull = false, dataType = DataType.BYTE_ARRAY)
    private byte[] inputItemData; // 物品数据
    
    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    private int seed; // 附魔种子

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    private int bookShelfCount; // 书架数量

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    private int enchantable; // 物品附魔能力值

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String enchantment; // 附魔书的魔咒

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    private int enchantmentLevel; // 附魔书的魔咒等级

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    private int enchantmentFailure; // 附魔书的失败率

    @DatabaseField(canBeNull = false, dataType = DataType.LONG)
    private long timestamp;
}

