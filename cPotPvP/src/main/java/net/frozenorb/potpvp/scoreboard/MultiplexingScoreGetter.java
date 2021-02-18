package net.frozenorb.potpvp.scoreboard;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.potpvp.setting.Setting;
import net.frozenorb.potpvp.setting.SettingHandler;
import net.frozenorb.qlib.scoreboard.ScoreGetter;
import net.frozenorb.qlib.util.LinkedList;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

final class MultiplexingScoreGetter implements ScoreGetter {

    private final BiConsumer<Player, LinkedList<String>> matchScoreGetter;
    private final BiConsumer<Player, LinkedList<String>> lobbyScoreGetter;

    MultiplexingScoreGetter(
            BiConsumer<Player, LinkedList<String>> matchScoreGetter,
            BiConsumer<Player, LinkedList<String>> lobbyScoreGetter
    ) {
        this.matchScoreGetter = matchScoreGetter;
        this.lobbyScoreGetter = lobbyScoreGetter;
    }

    @Override
    public void getScores(LinkedList<String> scores, Player player) {
        if (PotPvPSI.getInstance() == null) return;
        MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();
        SettingHandler settingHandler = PotPvPSI.getInstance().getSettingHandler();

        if (settingHandler.getSetting(player, Setting.SHOW_SCOREBOARD)) {
            if (matchHandler.isPlayingOrSpectatingMatch(player)) {
                matchScoreGetter.accept(player, scores);
            } else {

                lobbyScoreGetter.accept(player, scores);
            }
        }

        if (!scores.isEmpty()) {
            scores.addFirst("&a&7&m--------------------");
            scores.add("&f&7&m--------------------");
            scores.add("&7&ocrystalpvp.org");
        }
    }

}