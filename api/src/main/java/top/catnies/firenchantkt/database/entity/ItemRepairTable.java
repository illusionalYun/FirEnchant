package top.catnies.firenchantkt.database.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * 道具修复记录表
 * 用于记录玩家正在修复的道具信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "firenchant_item_repair")
public class ItemRepairTable {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(canBeNull = false, index = true)
    private UUID playerId;
    
    @DatabaseField(canBeNull = false, columnDefinition = "TEXT")
    private String itemData; // 序列化的物品数据
    
    @DatabaseField(canBeNull = false)
    private long startTime;
    
    @DatabaseField(canBeNull = false)
    private long duration; // 修复所需时间（毫秒）
    
    @DatabaseField(canBeNull = false, defaultValue = "false")
    private boolean received = false;
    
    /**
     * 检查修复是否已经完成
     * @return 如果当前时间已超过修复完成时间则返回true
     */
    public boolean isCompleted() {
        return System.currentTimeMillis() >= (startTime + duration);
    }
    
    /**
     * 获取剩余时间（毫秒）
     * @return 剩余修复时间，如果已完成则返回0
     */
    public long getRemainingTime() {
        long remaining = (startTime + duration) - System.currentTimeMillis();
        return Math.max(0, remaining);
    }
}
