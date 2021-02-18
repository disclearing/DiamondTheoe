package net.frozenorb.potpvp.rematch;

import lombok.experimental.UtilityClass;
import net.frozenorb.qlib.util.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public final class RematchItems {

    public static final ItemStack REQUEST_REMATCH_ITEM = new ItemStack(Material.DIAMOND);
    public static final ItemStack SENT_REMATCH_ITEM = new ItemStack(Material.DIAMOND);
    public static final ItemStack ACCEPT_REMATCH_ITEM = new ItemStack(Material.DIAMOND);

    static {
        ItemUtils.setDisplayName(REQUEST_REMATCH_ITEM, ChatColor.AQUA + "Request Rematch");
        ItemUtils.setDisplayName(SENT_REMATCH_ITEM, ChatColor.DARK_GREEN + "Sent Rematch");
        ItemUtils.setDisplayName(ACCEPT_REMATCH_ITEM, ChatColor.AQUA + "Accept Rematch");
    }

}