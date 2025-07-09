package top.catnies.firenchantkt.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@DatabaseTable(tableName = "firenchant_enchartlog")
public class PlayerEnchantLogData {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(index = true, canBeNull = false)
    private UUID player;
    @DatabaseField(index = true, canBeNull = false)
    private String enchantment;
    @DatabaseField(canBeNull = false)
    private int takeLevel;
    @DatabaseField(canBeNull = false)
    private int random;
    @DatabaseField(canBeNull = false)
    private int baseFailure;
    @DatabaseField(canBeNull = false)
    private int actualFailure;
    @DatabaseField(canBeNull = false)
    private boolean success;
}
