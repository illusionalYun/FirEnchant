package top.catnies.firenchantkt.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NMSHandler {
    int getAnvilLevel(Player player, ItemStack itemStack, ItemStack enchantBookItemStack);
}
