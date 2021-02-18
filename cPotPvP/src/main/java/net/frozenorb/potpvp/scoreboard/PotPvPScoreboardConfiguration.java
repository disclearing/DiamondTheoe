package net.frozenorb.potpvp.scoreboard;

import net.frozenorb.qlib.scoreboard.ScoreboardConfiguration;
import net.frozenorb.qlib.scoreboard.TitleGetter;

import org.apache.commons.lang.StringEscapeUtils;

public final class PotPvPScoreboardConfiguration {

    public static ScoreboardConfiguration create() {
        ScoreboardConfiguration configuration = new ScoreboardConfiguration();

        configuration.setTitleGetter(TitleGetter.forStaticString("&b&lCrystal &7┃ &fPractice"));
        configuration.setScoreGetter(new MultiplexingScoreGetter(
            new MatchScoreGetter(),
            new LobbyScoreGetter()
        ));

        return configuration;
    }

}
