package net.frozenorb.potpvp.queue;

import net.frozenorb.qlib.util.ItemUtils;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;

import static net.frozenorb.potpvp.PotPvPLang.LEFT_ARROW;
import static net.frozenorb.potpvp.PotPvPLang.RIGHT_ARROW;
import static org.bukkit.ChatColor.*;

@UtilityClass
public final class QueueItems {

    public static final ItemStack JOIN_SOLO_UNRANKED_QUEUE_ITEM = new ItemStack(Material.IRON_SWORD);
    public static final ItemStack LEAVE_SOLO_UNRANKED_QUEUE_ITEM = new ItemStack(Material.INK_SACK, 1, (byte) DyeColor.RED.getDyeData());

    public static final ItemStack JOIN_SOLO_RANKED_QUEUE_ITEM = new ItemStack(Material.DIAMOND_SWORD);
    public static final ItemStack LEAVE_SOLO_RANKED_QUEUE_ITEM = new ItemStack(Material.INK_SACK, 1, (byte) DyeColor.RED.getDyeData());

    public static final ItemStack JOIN_SOLO_UNPROTECTED_QUEUE_ITEM = new ItemStack(Material.WOOD_SWORD);

    public static final ItemStack JOIN_PARTY_UNRANKED_QUEUE_ITEM = new ItemStack(Material.IRON_SWORD);
    public static final ItemStack LEAVE_PARTY_UNRANKED_QUEUE_ITEM = new ItemStack(Material.ARROW);

    public static final ItemStack JOIN_PARTY_RANKED_QUEUE_ITEM = new ItemStack(Material.DIAMOND_SWORD);
    public static final ItemStack LEAVE_PARTY_RANKED_QUEUE_ITEM = new ItemStack(Material.ARROW);

    static {
    ItemUtils.setDisplayName(JOIN_SOLO_UNRANKED_QUEUE_ITEM, AQUA.toString() + "Unranked Queue" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(LEAVE_SOLO_UNRANKED_QUEUE_ITEM, RED + "Leave Queue" + GRAY + " (Right Click)");

        ItemUtils.setDisplayName(JOIN_SOLO_RANKED_QUEUE_ITEM, DARK_AQUA.toString() + "Ranked Queue" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(LEAVE_SOLO_RANKED_QUEUE_ITEM, RED + "Leave Queue" + GRAY + " (Right Click)");

        ItemUtils.setDisplayName(JOIN_SOLO_UNPROTECTED_QUEUE_ITEM, ChatColor.RED.toString() + "Unprotected Queue" + GRAY + " (Right Click)");

        ItemUtils.setDisplayName(JOIN_PARTY_UNRANKED_QUEUE_ITEM, AQUA + "Play 2v2 Unranked" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(LEAVE_PARTY_UNRANKED_QUEUE_ITEM, RED + "Leave Queue" + GRAY + " (Right Click)");

        ItemUtils.setDisplayName(JOIN_PARTY_RANKED_QUEUE_ITEM, AQUA + "Join 2v2 Ranked" + GRAY + " (Right Click)");
        ItemUtils.setDisplayName(LEAVE_PARTY_RANKED_QUEUE_ITEM, RED + "Leave Queue" + GRAY + " (Right Click)");
    }

}