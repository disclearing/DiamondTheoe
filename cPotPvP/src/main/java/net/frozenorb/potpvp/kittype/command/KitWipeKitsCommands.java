package net.frozenorb.potpvp.kittype.command;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.kittype.KitType;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import net.frozenorb.qlib.util.UUIDUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.skydevelopment.core.Core;
import xyz.skydevelopment.core.CoreAPI;

import java.util.UUID;

public final class KitWipeKitsCommands {

    @Command(names = "kit wipeKits Type", permission = "op")
    public static void kitWipeKitsType(Player sender, @Param(name="kit type") KitType kitType) {
        int modified = PotPvPSI.getInstance().getKitHandler().wipeKitsWithType(kitType);
        sender.sendMessage(ChatColor.GREEN + "Wiped " + modified + " " + kitType.getDisplayName() + " kits.");
        sender.sendMessage(ChatColor.GRAY + "^ We would have a proper count here if we ran recent versions of MongoDB");
    }

    @Command(names = "kit wipeKits Player", permission = "op")
    public static void kitWipeKitsPlayer(Player sender, @Param(name="target") UUID target) {
        PotPvPSI.getInstance().getKitHandler().wipeKitsForPlayer(target);
        Core.INSTANCE.getProfileManagement().loadProfile(target);
        sender.sendMessage(ChatColor.GREEN + "Wiped " + CoreAPI.INSTANCE.getProfile(target).getNameWithColor() + ChatColor.GREEN + "'s kits.");
    }

}