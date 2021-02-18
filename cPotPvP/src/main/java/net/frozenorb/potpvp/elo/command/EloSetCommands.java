package net.frozenorb.potpvp.elo.command;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.elo.EloHandler;
import net.frozenorb.potpvp.kittype.KitType;
import net.frozenorb.potpvp.party.Party;
import net.frozenorb.potpvp.party.PartyHandler;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import net.frozenorb.qlib.util.UUIDUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.skydevelopment.core.Core;
import xyz.skydevelopment.core.CoreAPI;

public final class EloSetCommands {

    @Command(names = {"elo setSolo"}, permission = "op")
    public static void eloSetSolo(Player sender, @Param(name="target") Player target, @Param(name="kit type") KitType kitType, @Param(name="new elo") int newElo) {
        EloHandler eloHandler = PotPvPSI.getInstance().getEloHandler();
        eloHandler.setElo(target, kitType, newElo);
        sender.sendMessage(ChatColor.GREEN + "Set " + CoreAPI.INSTANCE.getProfile(target.getUniqueId()).getNameWithColor() + ChatColor.GREEN + "'s " + kitType.getDisplayName() + " elo to " + newElo + ".");
    }

    @Command(names = {"elo setTeam"}, permission = "op")
    public static void eloSetTeam(Player sender, @Param(name="target") Player target, @Param(name="kit type") KitType kitType, @Param(name="new elo") int newElo) {
        PartyHandler partyHandler = PotPvPSI.getInstance().getPartyHandler();
        EloHandler eloHandler = PotPvPSI.getInstance().getEloHandler();

        Party targetParty = partyHandler.getParty(target);

        if (targetParty == null) {
            Core.INSTANCE.getProfileManagement().loadProfile(target.getUniqueId());
            sender.sendMessage(ChatColor.RED + CoreAPI.INSTANCE.getProfile(target.getUniqueId()).getNameWithColor() + ChatColor.RED + " is not in a party.");
            return;
        }

        eloHandler.setElo(targetParty.getMembers(), kitType, newElo);
        Core.INSTANCE.getProfileManagement().loadProfile(targetParty.getLeader());
        sender.sendMessage(ChatColor.GREEN + "Set " + kitType.getDisplayName() + " elo of " + CoreAPI.INSTANCE.getProfile(targetParty.getLeader()).getNameWithColor() + ChatColor.GREEN + "'s party to " + newElo + ".");
    }

}