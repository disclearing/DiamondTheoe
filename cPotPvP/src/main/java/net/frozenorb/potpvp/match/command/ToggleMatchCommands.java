package net.frozenorb.potpvp.match.command;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.qlib.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class ToggleMatchCommands {

    @Command(names = { "toggleMatches unranked" }, permission = "op")
    public static void toggleMatchesUnranked(Player sender) {
        MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();

        boolean newState = !matchHandler.isUnrankedMatchesDisabled();
        matchHandler.setUnrankedMatchesDisabled(newState);

        sender.sendMessage(ChatColor.AQUA + "Unranked matches are now " + ChatColor.UNDERLINE + (newState ? "disabled" : "enabled") + ChatColor.AQUA + ".");
    }

    @Command(names = { "toggleMatches ranked" }, permission = "op")
    public static void toggleMatchesRanked(Player sender) {
        MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();

        boolean newState = !matchHandler.isRankedMatchesDisabled();
        matchHandler.setRankedMatchesDisabled(newState);

        sender.sendMessage(ChatColor.AQUA + "Ranked matches are now " + ChatColor.UNDERLINE + (newState ? "disabled" : "enabled") + ChatColor.AQUA + ".");
    }

}