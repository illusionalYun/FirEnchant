package top.catnies.firenchantkt.nms;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    /**
     * 获取注册表中附魔台附魔列表
     *
     * @return 附魔列表
     */
    Set<Enchantment> getEnchantmentTableEnchantmentList(World world);

    /**
     * 获取指定玩家下次附魔指定物品的附魔结果列表
     *
     * @param player 玩家实例
     * @param bookShelfCount 书架数量
     * @param itemStack 物品实例
     * @param enchantmentList 附魔列表
     * @return 附魔结果列表
     */
    List<Map<Enchantment, Integer>> getPlayerNextEnchantmentTableResult(Player player, int bookShelfCount, ItemStack itemStack, Set<Enchantment> enchantmentList);
}
