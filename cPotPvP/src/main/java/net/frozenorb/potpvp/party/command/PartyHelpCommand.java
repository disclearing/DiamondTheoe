package net.frozenorb.potpvp.party.command;

import com.google.common.collect.ImmutableList;

import net.frozenorb.potpvp.PotPvPLang;
import net.frozenorb.qlib.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public final class PartyHelpCommand {

    private static final List<String> HELP_MESSAGE = ImmutableList.of(
        ChatColor.DARK_GRAY + PotPvPLang.LONG_LINE,
        "§3§lParty Help §7- §fInformation on how to use party commands",
        "§7",
        "§3Party Commands:",
        "§b/party invite §7- §fInvite a player to join your party",
        "§b/party leave §7- §fLeave your current party",
        "§b/party accept [player] §7- §fAccept party invitation",
        "§b/party info [player] §7- §fView the roster of the party",
        "",
        "§3Leader Commands:",
        "§b/party kick <player> §7- §fKick a player from your party",
        "§b/party leader <player> §7- §fTransfer party leadership",
        "§b/party disband §7 - §fDisbands party",
        "§b/party lock §7 - §fLock party from others joining",
        "§b/party open §7 - §fOpen party to others joining",
        "§b/party password <password> §7 - §fSets party password",
        "",
        "§3Other Help:",
        "§fTo use §3party §fchat, prefix your message with §3!§b.",
        ChatColor.DARK_GRAY + PotPvPLang.LONG_LINE
    );

    @Command(names = {"party", "p", "t", "team", "f", "party help", "p help", "t help", "team help", "f help"}, permission = "")
    public static void party(Player sender) {
        HELP_MESSAGE.forEach(sender::sendMessage);
    }

}