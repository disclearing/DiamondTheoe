
package net.frozenorb.potpvp.scoreboard;

import xyz.skydevelopment.core.Core;
import xyz.skydevelopment.core.CoreAPI;
import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.elo.EloHandler;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.potpvp.party.Party;
import net.frozenorb.potpvp.party.PartyHandler;
import net.frozenorb.potpvp.queue.MatchQueue;
import net.frozenorb.potpvp.queue.MatchQueueEntry;
import net.frozenorb.potpvp.queue.QueueHandler;
import net.frozenorb.potpvp.tournament.Tournament;
import net.frozenorb.potpvp.tournament.Tournament.TournamentStage;
import net.frozenorb.qlib.autoreboot.AutoRebootHandler;
import net.frozenorb.qlib.util.LinkedList;
import net.frozenorb.qlib.util.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

final class LobbyScoreGetter implements BiConsumer<Player, LinkedList<String>> {

    private int LAST_ONLINE_COUNT = 0;
    private int LAST_IN_FIGHTS_COUNT = 0;
    private int LAST_IN_QUEUES_COUNT = 0;

    private long lastUpdated = System.currentTimeMillis();

    @Override
    public void accept(Player player, LinkedList<String> scores) {
        Optional<UUID> followingOpt = PotPvPSI.getInstance().getFollowHandler().getFollowing(player);
        MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();
        PartyHandler partyHandler = PotPvPSI.getInstance().getPartyHandler();
        QueueHandler queueHandler = PotPvPSI.getInstance().getQueueHandler();
        EloHandler eloHandler = PotPvPSI.getInstance().getEloHandler();

        scores.add("&aOnline: &f" + LAST_ONLINE_COUNT);
        scores.add("&dIn Queue: &f" + LAST_IN_QUEUES_COUNT);
        scores.add("&eIn Fights: &f" + LAST_IN_FIGHTS_COUNT);

        Party playerParty = partyHandler.getParty(player);
        if (playerParty != null) {
            int size = playerParty.getMembers().size();
            scores.add("&9Your Party: &f" + size);
        }

        if (2500 <= System.currentTimeMillis() - lastUpdated) {
            lastUpdated = System.currentTimeMillis();
            LAST_ONLINE_COUNT = Bukkit.getOnlinePlayers().size();
            LAST_IN_FIGHTS_COUNT = matchHandler.countPlayersPlayingInProgressMatches();
            LAST_IN_QUEUES_COUNT = queueHandler.getQueuedCount();
        }

        // this definitely can be a .ifPresent, however creating the new lambda that often
        // was causing some performance issues, so we do this less pretty (but more efficent)
        // check (we can't define the lambda up top and reference because we reference the
        // scores variable)
        if (followingOpt.isPresent()) {
            Player following = Bukkit.getPlayer(followingOpt.get());
            Core.INSTANCE.getProfileManagement().loadProfile(following.getUniqueId());
            scores.add("&bFollowing: *&f" + CoreAPI.INSTANCE.getProfile(following.getUniqueId()).getNameWithColor());

            if (player.hasPermission("core.staff")) {
                MatchQueueEntry targetEntry = getQueueEntry(following);

                if (targetEntry != null) {
                    MatchQueue queue = targetEntry.getQueue();

                    scores.add("&bTarget queue:");
                    scores.add("&f" + (queue.isRanked() ? "Ranked" : "Unranked") + " " + queue.getKitType().getDisplayName());
                }
            }
        }

        MatchQueueEntry entry = getQueueEntry(player);


        if (entry != null) {
            String waitTimeFormatted = TimeUtils.formatIntoMMSS(entry.getWaitSeconds());
            MatchQueue queue = entry.getQueue();

            scores.add("&7");
            scores.add(ChatColor.DARK_AQUA + (queue.isRanked() ? "Ranked" : "Unranked") + " " + queue.getKitType().getColoredDisplayName());
            scores.add("&bDuration: &f" + waitTimeFormatted);

            if (queue.isRanked()) {
                int elo = eloHandler.getElo(entry.getMembers(), queue.getKitType());
                int window = entry.getWaitSeconds() * QueueHandler.RANKED_WINDOW_GROWTH_PER_SECOND;

                scores.add("&bSearch range: &f" + Math.max(0, elo - window) + " - " + (elo + window));
            }
        }

        if (AutoRebootHandler.isRebooting()) {
            String secondsStr = TimeUtils.formatIntoMMSS(AutoRebootHandler.getRebootSecondsRemaining());
            scores.add("&c&lRebooting: " + secondsStr);
        }

        if (player.hasMetadata("ModMode")) {
            scores.add("");
            scores.add(ChatColor.GRAY.toString() + ChatColor.BOLD + "In Silent Mode");
        }

        Tournament tournament = PotPvPSI.getInstance().getTournamentHandler().getTournament();
        if (tournament != null) {
            scores.add("&7&m--------------------");
            scores.add("&3&lTournament");

            if (tournament.getStage() == TournamentStage.WAITING_FOR_TEAMS) {
                int teamSize = tournament.getRequiredPartySize();
                scores.add("&bLadder: &f" + tournament.getType().getDisplayName());
                scores.add("&bTeam Size: &f" + teamSize + "v" + teamSize);
                int multiplier = teamSize < 3 ? teamSize : 1;
                scores.add("&b" + (teamSize < 3 ? "Players"  : "Teams") + ": &f" + (tournament.getActiveParties().size() * multiplier + "/" + tournament.getRequiredPartiesToStart() * multiplier));
            } else if (tournament.getStage() == TournamentStage.COUNTDOWN) {
                if (tournament.getCurrentRound() == 0) {
                    scores.add("&b");
                    scores.add(" &bStarts in &f" + tournament.getBeginNextRoundIn());
                } else {
                    scores.add("&b");
                    scores.add("&bRound " + (tournament.getCurrentRound() + 1));
                    scores.add(" &bStarts in &f" + tournament.getBeginNextRoundIn());
                }
            } else if (tournament.getStage() == TournamentStage.IN_PROGRESS) {
                scores.add("&bRound: &f" + tournament.getCurrentRound());

                int teamSize = tournament.getRequiredPartySize();
                int multiplier = teamSize < 3 ? teamSize : 1;
                scores.add("&bDuration: &f" + TimeUtils.formatIntoMMSS((int) (System.currentTimeMillis() - tournament.getRoundStartedAt()) / 1000));
                scores.add("&b" + (teamSize < 3 ? "Players" : "Teams") + ": &f" + tournament.getActiveParties().size() * multiplier + "/" + tournament.getRequiredPartiesToStart() * multiplier);
            }
        }

    }

    private MatchQueueEntry getQueueEntry(Player player) {
        PartyHandler partyHandler = PotPvPSI.getInstance().getPartyHandler();
        QueueHandler queueHandler = PotPvPSI.getInstance().getQueueHandler();

        Party playerParty = partyHandler.getParty(player);
        if (playerParty != null) {
            return queueHandler.getQueueEntry(playerParty);
        } else {
            return queueHandler.getQueueEntry(player.getUniqueId());
        }
    }

}