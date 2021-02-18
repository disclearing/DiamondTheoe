package net.frozenorb.potpvp.tab;

import java.util.function.BiConsumer;

import net.frozenorb.potpvp.match.Match;
import net.frozenorb.qlib.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.qlib.tab.TabLayout;

final class HeaderLayoutProvider implements BiConsumer<Player, TabLayout> {

    @Override
    public void accept(Player player, TabLayout tabLayout) {
        header: {
            tabLayout.set(1, 0, "&b&lCrystalPvP Practice");
            tabLayout.set(0, 0, "&7&ocrystalpvp.org");
            tabLayout.set(2, 0, "&7&ocrystalpvp.org");
        }

        status: {
            tabLayout.set(1, 1, ChatColor.GRAY + "Your Connection", Math.max(((PlayerUtils.getPing(player) + 5) / 10) * 10, 1));
            tabLayout.set(0, 1, ChatColor.GRAY + "Online: " + Bukkit.getOnlinePlayers().size());
            tabLayout.set(2, 1, ChatColor.GRAY + "In Fights: " + PotPvPSI.getInstance().getMatchHandler().countPlayersPlayingInProgressMatches());
        }
    }

}
