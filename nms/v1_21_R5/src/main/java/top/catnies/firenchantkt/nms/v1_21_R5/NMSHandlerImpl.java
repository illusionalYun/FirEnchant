package top.catnies.firenchantkt.nms.v1_21_R5;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.catnies.firenchantkt.nms.NMSHandler;

import java.util.*;

public class NMSHandlerImpl implements NMSHandler {
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
    public Set<Enchantment> getEnchantmentTableEnchantmentList(World world) {
        Level nmsWorld = ((CraftWorld) world).getHandle();

        Optional<HolderSet.Named<net.minecraft.world.item.enchantment.Enchantment>> optional = nmsWorld.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentTags.IN_ENCHANTING_TABLE);
        if (optional.isEmpty()) {
            return new LinkedHashSet<>();
        }

        Set<Enchantment> enchantmentList = new LinkedHashSet<>();
        for (int i = 0; i < optional.get().size(); i++) {
            enchantmentList.add(CraftEnchantment.minecraftHolderToBukkit(optional.get().get(i)));
        }

        return enchantmentList;
    }

    @Override
    public List<Map<Enchantment, Integer>> getPlayerNextEnchantmentTableResult(Player player, int bookShelfCount, ItemStack itemStack, Set<Enchantment> enchantmentList) {
        net.minecraft.world.entity.player.Player nmsPlayer = ((CraftPlayer) player).getHandle();
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);

        RandomSource random = RandomSource.create(nmsPlayer.enchantmentSeed);

        int[] costs = new int[3];
        for (int slot = 0; slot < 3; slot++) {
            costs[slot] = this.getEnchantmentCost(random, slot, bookShelfCount);
            if (costs[slot] < slot + 1) {
                costs[slot] = 0;
            }
        }

        List<Map<org.bukkit.enchantments.Enchantment, Integer>> results = new ArrayList<>();
        for (int slot = 0; slot < costs.length; slot++) {
            int cost = costs[slot];

            List<EnchantmentInstance> enchantmentInstanceList = this.getEnchantmentList(nmsPlayer.enchantmentSeed, nmsItem, slot, cost, enchantmentList);
            if (enchantmentInstanceList.isEmpty()) {
                continue;
            }

            EnchantmentInstance enchantmentInstance = enchantmentInstanceList.get(random.nextInt(enchantmentInstanceList.size()));

            Map<org.bukkit.enchantments.Enchantment, Integer> optionMap = new HashMap<>();
            org.bukkit.enchantments.Enchantment enchant = CraftEnchantment.minecraftHolderToBukkit(enchantmentInstance.enchantment());
            optionMap.put(enchant, enchantmentInstance.level());

            results.add(optionMap);
        }

        return results;
    }

    /**
     * 获取附魔花费
     *
     * @param random         随机数源
     * @param slot           附魔等级ID
     * @param bookShelfCount 书架数量
     * @return 附魔等级花费
     */
    private int getEnchantmentCost(RandomSource random, int slot, int bookShelfCount) {
        if (bookShelfCount > 15) {
            bookShelfCount = 15;
        }

        int i = random.nextInt(8) + 1 + (bookShelfCount >> 1) + random.nextInt(bookShelfCount + 1);
        if (slot == 0) {
            return Math.max(i / 3, 1);
        }

        return slot == 1 ? i * 2 / 3 + 1 : Math.max(i, bookShelfCount * 2);
    }

    /**
     * 获取附魔列表
     *
     * @param randomSeed 随机种子
     * @param itemStack  物品
     * @param slot       附魔等级ID
     * @param cost       附魔等级花费
     * @return 附魔列表
     */
    private List<EnchantmentInstance> getEnchantmentList(int randomSeed, net.minecraft.world.item.ItemStack itemStack, int slot, int cost, Set<Enchantment> enchantmentList) {
        if (enchantmentList.isEmpty()) {
            return new ArrayList<>();
        }

        RandomSource random = RandomSource.create(randomSeed + slot);
        List<EnchantmentInstance> list = EnchantmentHelper.selectEnchantment(random, itemStack, cost, enchantmentList.stream()
                .map(CraftEnchantment::bukkitToMinecraftHolder)
        );
        if (itemStack.is(Items.BOOK) && list.size() > 1) {
            list.remove(random.nextInt(list.size()));
        }

        return list;
    }
}
