package net.frozenorb.potpvp.command;

import xyz.skydevelopment.core.Core;
import xyz.skydevelopment.core.CoreAPI;
import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.util.VisibilityUtils;
import net.frozenorb.qlib.command.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import xyz.skydevelopment.core.plugin.utils.chat.Color;

public final class SilentCommand {

    @Command(names = {"silent"}, permission = "core.staff")
    public static void silent(Player sender) {
        if (sender.hasMetadata("ModMode")) {
            sender.removeMetadata("ModMode", PotPvPSI.getInstance());
            sender.removeMetadata("invisible", PotPvPSI.getInstance());

            sender.sendMessage(ChatColor.RED + "Silent mode disabled.");
            Core.INSTANCE.getProfileManagement().loadProfile(sender.getUniqueId());
            Bukkit.broadcast(Color.translate("&7&o[" + CoreAPI.INSTANCE.getProfile(sender.getUniqueId()).getNameWithColor() + " &7&owent out of silent mode]"), "core.admin");
        } else {
            sender.setMetadata("ModMode", new FixedMetadataValue(PotPvPSI.getInstance(), true));
            sender.setMetadata("invisible", new FixedMetadataValue(PotPvPSI.getInstance(), true));
            
            sender.sendMessage(ChatColor.GREEN + "Silent mode enabled.");
            Core.INSTANCE.getProfileManagement().loadProfile(sender.getUniqueId());
            Bukkit.broadcast(Color.translate("&7&o[" + CoreAPI.INSTANCE.getProfile(sender.getUniqueId()).getNameWithColor() + " &7&owent into silent mode]"), "core.admin");
        }

        VisibilityUtils.updateVisibility(sender);
    }

}