package net.frozenorb.potpvp.command;

import com.google.common.collect.ImmutableList;

import net.frozenorb.potpvp.PotPvPLang;
import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.qlib.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Generic /help command, changes message sent based on if sender is playing in
 * or spectating a match.
 */
public final class HelpCommand {

    private static final List<String> HELP_MESSAGE_HEADER = ImmutableList.of(
        ChatColor.DARK_GRAY + PotPvPLang.LONG_LINE,
        "§b§lCrystal Practice Help",
        ""
    );

    private static final List<String> HELP_MESSAGE_LOBBY = ImmutableList.of(
        "§3Common Commands:",
        "§b/duel <player> §7- Challenge a player to a duel",
        "§b/party invite <player> §7- Invite a player to a party",
        "§b/spectate <player> §7- Spectate a player in a match",
        "",
        "§3Other Commands:",
        "§b/party help §7- Information on party commands",
        "§b/report <player> <reason> §7- Report a player for violating the rules",
        "§b/request <message> §7- Request assistance from a staff member"
    );

    private static final List<String> HELP_MESSAGE_MATCH = ImmutableList.of(
        "§3Common Commands:",
        "§b/spectate <player> §7- Spectate a player in a match",
        "§b/report <player> <reason> §7- Report a player for violating the rules",
        "§b/request <message> §7- Request assistance from a staff member"
    );

    private static final List<String> HELP_MESSAGE_FOOTER = ImmutableList.of(
        "",
        "§3Server Information:",
        PotPvPSI.getInstance().getDominantColor() == ChatColor.LIGHT_PURPLE ? "§3Official Discord §7- §bdiscord.crystalpvp.org" : "§3Official Discord §7- §bdiscord.crystalpvp.org",
        PotPvPSI.getInstance().getDominantColor() == ChatColor.LIGHT_PURPLE ? "§3Official Rules §7- §bcrystalpvp.org" : "§3Official Rules §7- §bwww.crystalpvp.org",
        PotPvPSI.getInstance().getDominantColor() == ChatColor.LIGHT_PURPLE ? "§3Store §7- §bstore.crystalpvp.org" : "§3Store §7- §bstore.crystalpvp.org",
     // "§ePractice Leaderboards §7- §dwww.minehq.com/stats/potpvp",
        ChatColor.DARK_GRAY + PotPvPLang.LONG_LINE
    );

    @Command(names = {"help", "?", "halp", "helpme"}, permission = "")
    public static void help(Player sender) {
        MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();

        HELP_MESSAGE_HEADER.forEach(sender::sendMessage);

        if (matchHandler.isPlayingOrSpectatingMatch(sender)) {
            HELP_MESSAGE_MATCH.forEach(sender::sendMessage);
        } else {
            HELP_MESSAGE_LOBBY.forEach(sender::sendMessage);
        }

        HELP_MESSAGE_FOOTER.forEach(sender::sendMessage);
    }

}
