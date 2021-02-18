package net.frozenorb.potpvp.command;

import xyz.skydevelopment.core.Core;
import xyz.skydevelopment.core.CoreAPI;
import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.follow.FollowHandler;
import net.frozenorb.potpvp.match.Match;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.potpvp.match.MatchTeam;
import net.frozenorb.potpvp.party.PartyHandler;
import net.frozenorb.potpvp.queue.QueueHandler;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PStatusCommand {

    @Command(names = {"pstatus"}, permission = "op")
    public static void pStatus(Player sender, @Param(name="target", defaultValue = "self") Player target) {
        MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();
        QueueHandler queueHandler = PotPvPSI.getInstance().getQueueHandler();
        PartyHandler partyHandler = PotPvPSI.getInstance().getPartyHandler();
        FollowHandler followHandler = PotPvPSI.getInstance().getFollowHandler();

        Core.INSTANCE.getProfileManagement().loadProfile(target.getUniqueId());
        sender.sendMessage(ChatColor.DARK_AQUA + CoreAPI.INSTANCE.getProfile(target.getUniqueId()).getNameWithColor() + ChatColor.DARK_AQUA + ":");
        sender.sendMessage(ChatColor.AQUA + "In match: " + matchHandler.isPlayingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "In match (NC): " + noCacheIsPlayingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "Spectating match: " + matchHandler.isSpectatingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "Spectating match (NC): " + noCacheIsSpectatingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "In or spectating match: " + matchHandler.isPlayingOrSpectatingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "In or spectating match (NC): " + noCacheIsPlayingOrSpectatingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "In queue: " + queueHandler.isQueued(target.getUniqueId()));
        sender.sendMessage(ChatColor.AQUA + "In party: " + partyHandler.hasParty(target));
        sender.sendMessage(ChatColor.AQUA + "Following: " + followHandler.getFollowing(target).isPresent());
    }

    private static boolean noCacheIsPlayingMatch(Player target) {
        for (Match match : PotPvPSI.getInstance().getMatchHandler().getHostedMatches()) {
            for (MatchTeam team : match.getTeams()) {
                if (team.isAlive(target.getUniqueId())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean noCacheIsSpectatingMatch(Player target) {
        for (Match match : PotPvPSI.getInstance().getMatchHandler().getHostedMatches()) {
            if (match.isSpectator(target.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    private static boolean noCacheIsPlayingOrSpectatingMatch(Player target) {
        return noCacheIsPlayingMatch(target) || noCacheIsSpectatingMatch(target);
    }

}