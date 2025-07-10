package top.catnies.firenchantkt.nms.v1_21_R1;

import com.google.common.collect.Lists;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.EnchantingTableBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import top.catnies.firenchantkt.nms.NMSHandler;

import java.util.*;

public class NMSHandlerImpl implements NMSHandler {
    /**
     * 获取附魔花费
     *
     * @param random         随机数源
     * @param slot           附魔等级ID
     * @param bookShelfCount 书架数量
     * @return 附魔等级花费
     */
    public static int getEnchantmentCost(RandomSource random, int slot, int bookShelfCount) {
        if (bookShelfCount > 15) {
            bookShelfCount = 15;
        }

        int i = random.nextInt(8) + 1 + (bookShelfCount >> 1) + random.nextInt(bookShelfCount + 1);
        if (slot == 0) {
            return Math.max(i / 3, 1);
        }

        return slot == 1 ? i * 2 / 3 + 1 : Math.max(i, bookShelfCount * 2);
    }

    @Override
    public int getEnchantmentTableBookShelf(Location location) {
        net.minecraft.world.level.Level nmsWorld = ((CraftWorld) location.getWorld()).getHandle();

        BlockPos tablePos = new BlockPos(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );

        int bookshelfCount = 0;
        for (BlockPos blockPos1 : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
            if (EnchantingTableBlock.isValidBookShelf(nmsWorld, tablePos, blockPos1)) {
                bookshelfCount++;
            }
        }

        return bookshelfCount;
    }

    @Override
    public List<Map<Enchantment, Integer>> getPlayerNextEnchantmentTableResult(Player player, int level, int bookShelfCount, Material type, Set<Enchantment> enchantmentList) {
        net.minecraft.world.entity.player.Player nmsPlayer = ((CraftPlayer) player).getHandle();

        RandomSource random = RandomSource.create(nmsPlayer.enchantmentSeed);

        int[] costs = new int[3];
        for (int slot = 0; slot < 3; slot++) {
            costs[slot] = getEnchantmentCost(random, slot, bookShelfCount);
            if (costs[slot] < slot + 1) {
                costs[slot] = 0;
            }
        }

        List<Map<Enchantment, Integer>> results = new ArrayList<>();
        for (int slot = 0; slot < costs.length; slot++) {
            int cost = costs[slot];

            List<EnchantmentInstance> enchantmentInstanceList = getEnchantmentList(nmsPlayer.enchantmentSeed, type, slot, level, cost, enchantmentList);
            if (enchantmentInstanceList.isEmpty()) {
                continue;
            }

            EnchantmentInstance enchantmentInstance = enchantmentInstanceList.get(random.nextInt(enchantmentInstanceList.size()));

            Map<Enchantment, Integer> optionMap = new HashMap<>();
            Enchantment enchant = CraftEnchantment.minecraftHolderToBukkit(enchantmentInstance.enchantment);
            optionMap.put(enchant, enchantmentInstance.level);

            results.add(optionMap);
        }

        return results;
    }

    /**
     * 获取附魔
     *
     * @param random          随机数源
     * @param itemStack       物品实例
     * @param level           附魔级别
     * @param cost            花费等级
     * @param enchantmentList 附魔列表
     * @return 附魔实例列表
     */
    private List<EnchantmentInstance> selectEnchantment(RandomSource random, org.bukkit.inventory.ItemStack itemStack, int level, int cost, Set<Enchantment> enchantmentList) {
        ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);;

        List<EnchantmentInstance> list = Lists.newArrayList();

        cost += 1 + random.nextInt(level / 4 + 1) + random.nextInt(level / 4 + 1);
        float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
        cost = Mth.clamp(Math.round((float) cost + (float) cost * f), 1, Integer.MAX_VALUE);

        List<EnchantmentInstance> availableEnchantmentResults = EnchantmentHelper.getAvailableEnchantmentResults(cost, nmsItem, enchantmentList.stream()
                .map(e -> (CraftEnchantment) e)
                .map(CraftEnchantment::bukkitToMinecraftHolder)
        );
        if (!availableEnchantmentResults.isEmpty()) {
            WeightedRandom.getRandomItem(random, availableEnchantmentResults).ifPresent(list::add);

            while (random.nextInt(50) <= level) {
                if (!list.isEmpty()) {
                    EnchantmentHelper.filterCompatibleEnchantments(availableEnchantmentResults, Util.lastOf(list));
                }

                if (availableEnchantmentResults.isEmpty()) {
                    break;
                }

                WeightedRandom.getRandomItem(random, availableEnchantmentResults).ifPresent(list::add);
                level /= 2;
            }
        }

        return list;
    }

    /**
     * 获取附魔列表
     *
     * @param randomSeed 随机种子
     * @param type       物品类型
     * @param slot       附魔等级ID
     * @param cost       附魔花费经验等级
     * @return 附魔列表
     */
    private List<EnchantmentInstance> getEnchantmentList(int randomSeed, Material type, int slot, int level, int cost, Set<Enchantment> enchantmentList) {
        if (enchantmentList.isEmpty()) {
            return new ArrayList<>();
        }

        RandomSource random = RandomSource.create(randomSeed + slot);
        List<EnchantmentInstance> list = this.selectEnchantment(random, new org.bukkit.inventory.ItemStack(type), level, cost, enchantmentList);
        if (type == Material.BOOK && list.size() > 1) {
            list.remove(random.nextInt(list.size()));
        }

        return list;
    }
}
