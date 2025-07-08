package top.catnies.firenchantkt.nms.v1_21_R2;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.catnies.firenchantkt.nms.NMSHandler;

public class NMSHandlerImpl implements NMSHandler {
    @Override
    public int getAnvilLevel(Player player, ItemStack itemStack, ItemStack enchantBookItemStack) {
        net.minecraft.world.entity.player.Player nmsPlayer = ((CraftPlayer) player).getHandle();
        net.minecraft.world.item.ItemStack nmsItem = ((CraftItemStack) itemStack).handle;
        net.minecraft.world.item.ItemStack nmsItem2 = ((CraftItemStack) enchantBookItemStack).handle;

        if (!EnchantmentHelper.canStoreEnchantments(nmsItem)) {
            return -1;
        }

        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(nmsItem.copy()));
        int i = 0;

        boolean hasStoredEnchantments = nmsItem2.has(DataComponents.STORED_ENCHANTMENTS);
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(nmsItem2);
        boolean validEnchant = false;
        boolean invalidEnchant = false;

        for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
            Holder<Enchantment> holder = entry.getKey();
            int level = mutable.getLevel(holder);
            int newLevel = level == entry.getIntValue() ? entry.getIntValue() + 1 : Math.max(entry.getIntValue(), level);
            Enchantment enchantment = holder.value();

            boolean canEnchant = nmsPlayer.hasInfiniteMaterials() || nmsItem.is(Items.ENCHANTED_BOOK) || enchantment.canEnchant(nmsItem);

            for (Holder<Enchantment> h : mutable.keySet()) {
                if (!h.equals(holder) && !Enchantment.areCompatible(holder, h)) {
                    canEnchant = false;
                    i++;
                }
            }

            if (!canEnchant) {
                invalidEnchant = true;
            } else {
                validEnchant = true;
                mutable.set(holder, Math.min(newLevel, enchantment.getMaxLevel()));

                int cost = hasStoredEnchantments
                        ? Math.max(1, enchantment.getAnvilCost() / 2)
                        : enchantment.getAnvilCost();

                i += cost * newLevel;
                if (nmsItem.getCount() > 1) i = 40;
            }
        }
        if (invalidEnchant && !validEnchant) {
            return -1;
        }

        long repairCost = (long) nmsItem.getOrDefault(DataComponents.REPAIR_COST, 0)
                + nmsItem2.getOrDefault(DataComponents.REPAIR_COST, 0);
        return (int) Mth.clamp(repairCost + i, 0L, 2147483647L);
    }
}
