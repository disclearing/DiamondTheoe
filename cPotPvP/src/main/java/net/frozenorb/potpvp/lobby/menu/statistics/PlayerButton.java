package net.frozenorb.potpvp.lobby.menu.statistics;

import com.google.common.collect.Lists;
import xyz.skydevelopment.core.Core;
import xyz.skydevelopment.core.CoreAPI;
import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.elo.EloHandler;
import net.frozenorb.potpvp.kittype.KitType;
import net.frozenorb.qlib.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerButton extends Button {

    private static EloHandler eloHandler = PotPvPSI.getInstance().getEloHandler();

    @Override
    public String getName(Player player) {
        Core.INSTANCE.getProfileManagement().loadProfile(player.getUniqueId());
        return CoreAPI.INSTANCE.getProfile(player.getUniqueId()).getNameWithColor() + ChatColor.GRAY + " ┃ " + ChatColor.WHITE + "Statistics";
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();

        description.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "----------------");

        for (KitType kitType : KitType.getAllTypes()) {
            if (kitType.isSupportsRanked()) {
                description.add(ChatColor.AQUA + kitType.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + eloHandler.getElo(player, kitType));
            }
        }

        description.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "----------------");
        description.add(ChatColor.DARK_AQUA + "Global" + ChatColor.GRAY + ": " + ChatColor.WHITE + eloHandler.getGlobalElo(player.getUniqueId()));
        description.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "----------------");

        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.SKULL_ITEM;
    }

    @Override
    public byte getDamageValue(Player player) {
        return (byte) 3;
    }

    private String getColoredName(Player player) {

        return player.getDisplayName();
    }
}
