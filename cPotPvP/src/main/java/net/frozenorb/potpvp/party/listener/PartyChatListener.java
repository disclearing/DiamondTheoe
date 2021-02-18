package net.frozenorb.potpvp.party.listener;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.party.Party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.skydevelopment.core.CoreAPI;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PartyChatListener implements Listener {

    private final Map<UUID, Long> canUsePartyChat = new ConcurrentHashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (!event.getMessage().startsWith("!")) {
            return;
        }

        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = event.getMessage().substring(1).trim();
        Party party = PotPvPSI.getInstance().getPartyHandler().getParty(player);

        if (party == null) {
            player.sendMessage(ChatColor.RED + "You aren't in a party!");
            return;
        }

        if (canUsePartyChat.getOrDefault(player.getUniqueId(), 0L) > System.currentTimeMillis()) {
            player.sendMessage(ChatColor.RED + "Wait a bit before sending another message.");
            return;
        }

        ChatColor prefixColor = party.isLeader(player.getUniqueId()) ? ChatColor.AQUA : ChatColor.LIGHT_PURPLE;
        party.message(ChatColor.GRAY + "(" + ChatColor.AQUA.toString() + ChatColor.BOLD + "PC" + ChatColor.GRAY + ") " + CoreAPI.INSTANCE.getProfile(player.getUniqueId()).getNameWithColor() + ChatColor.GRAY + ": " + ChatColor.WHITE + message);

        canUsePartyChat.put(player.getUniqueId(), System.currentTimeMillis() + 2_000);
        PotPvPSI.getInstance().getLogger().info("(Party Chat) " + CoreAPI.INSTANCE.getProfile(player.getUniqueId()).getNameWithColor() + ChatColor.GRAY + ": " + ChatColor.WHITE + message);
    }

}