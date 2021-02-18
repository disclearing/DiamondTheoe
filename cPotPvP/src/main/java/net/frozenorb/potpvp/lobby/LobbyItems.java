package net.frozenorb.potpvp.lobby;

import net.frozenorb.qlib.util.ItemUtils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;

import static net.frozenorb.potpvp.PotPvPLang.LEFT_ARROW;
import static net.frozenorb.potpvp.PotPvPLang.RIGHT_ARROW;
import static org.bukkit.ChatColor.*;

import org.bukkit.ChatColor;

@UtilityClass
public final class LobbyItems {

    public static final ItemStack SPECTATE_RANDOM_ITEM = new ItemStack(Material.COMPASS);
    public static final ItemStack SPECTATE_MENU_ITEM = new ItemStack(Material.PAPER);
    public static final ItemStack ENABLE_SPEC_MODE_ITEM = new ItemStack(Material.REDSTONE_TORCH_ON);
    public static final ItemStack DISABLE_SPEC_MODE_ITEM = new ItemStack(Material.LEVER);
    public static final ItemStack MANAGE_ITEM = new ItemStack(Material.ANVIL);
    public static final ItemStack EVENTS_ITEM = new ItemStack(Material.EMERALD);
    public static final ItemStack PARTY_CREATE = new ItemStack(Material.NETHER_STAR);
    public static final ItemStack UNFOLLOW_ITEM = new ItemStack(Material.INK_SACK, 1, DyeColor.RED.getDyeData());
    public static final ItemStack PLAYER_STATISTICS = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);

    static {
        ItemUtils.setDisplayName(SPECTATE_RANDOM_ITEM, AQUA + "Spectate Random Match" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(SPECTATE_MENU_ITEM, AQUA + "Spectate Menu" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(ENABLE_SPEC_MODE_ITEM, ChatColor.GREEN.toString() + " Enable Spectator Mode" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(DISABLE_SPEC_MODE_ITEM, ChatColor.GREEN.toString() + " Disable Spectator Mode" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(MANAGE_ITEM, AQUA + "Manage Practice" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(UNFOLLOW_ITEM, AQUA + "Stop Following" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(PLAYER_STATISTICS, AQUA + "Statistics" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(EVENTS_ITEM, AQUA.toString() + "Events" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(PLAYER_STATISTICS, DARK_AQUA + "Statistics" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(PARTY_CREATE, DARK_PURPLE + "Create Party" + GRAY + " (Right Click)");

    }

}