package top.catnies.firenchantkt.nms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NMSHandler {
    /**
     * 获取附魔台书架数量
     *
     * @param location 附魔台位置实例
     * @return 书架数量
     */
    int getEnchantmentTableBookShelf(Location location);

    List<Map<Enchantment, Integer>> getPlayerNextEnchantmentTableResult(Player player, int level, int bookShelfCount, Material type, Set<Enchantment> enchantmentList);
}
