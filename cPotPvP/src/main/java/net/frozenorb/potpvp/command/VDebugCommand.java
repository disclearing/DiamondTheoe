package net.frozenorb.potpvp.command;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import net.minecraft.server.v1_7_R4.EntityTracker;
import net.minecraft.server.v1_7_R4.EntityTrackerEntry;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class VDebugCommand {

    @Command(names = {"vdebug"}, permission = "op")
    public static void vdebug(Player sender, @Param(name="a") Player a, @Param(name="b") Player b, @Param(name="modify", defaultValue = "0") int modify) {
        CraftPlayer aCraft = (CraftPlayer) a;
        CraftPlayer bCraft = (CraftPlayer) b;
        EntityTracker tracker = ((WorldServer) aCraft.getHandle().world).tracker;
        EntityTrackerEntry aTracker = (EntityTrackerEntry) tracker.trackedEntities.get(aCraft.getHandle().getId());
        EntityTrackerEntry bTracker = (EntityTrackerEntry) tracker.trackedEntities.get(bCraft.getHandle().getId());

        if (modify == 1) {
            a.showPlayer(b);
            b.showPlayer(a);

            sender.sendMessage(ChatColor.RED + "Performed soft modify.");
            return;
        } else if (modify == 2) {
            aCraft.getHandle().playerConnection.sendPacket(PacketPlayOutPlayerInfo.addPlayer(bCraft.getHandle()));
            bCraft.getHandle().playerConnection.sendPacket(PacketPlayOutPlayerInfo.addPlayer(aCraft.getHandle()));

            sender.sendMessage(ChatColor.RED + "Performed hard modify.");
            return;
        } else if (modify == 3) {
            a.hidePlayer(b);
            b.hidePlayer(a);

            Bukkit.getScheduler().runTaskLater(PotPvPSI.getInstance(), () -> {
                a.showPlayer(b);
                b.showPlayer(a);
            }, 10L);

            sender.sendMessage(ChatColor.RED + "Performed flicker modify.");
            return;
        }

        sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.UNDERLINE + a.getName() + " <-> " + b.getName() + ":");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.AQUA + "a Validation: " + ChatColor.WHITE + (aCraft.getHandle().playerConnection != null && !a.equals(b)));
        sender.sendMessage(ChatColor.AQUA + "b Validation: " + ChatColor.WHITE + (bCraft.getHandle().playerConnection != null && !b.equals(a)));

        sender.sendMessage(ChatColor.AQUA + "a.canSee(b): " + ChatColor.WHITE + a.canSee(b));
        sender.sendMessage(ChatColor.AQUA + "b.canSee(a): " + ChatColor.WHITE + b.canSee(a));

        sender.sendMessage(ChatColor.AQUA + "a Tracker Entry: " + ChatColor.WHITE + aTracker);
        sender.sendMessage(ChatColor.AQUA + "b Tracker Entry: " + ChatColor.WHITE + bTracker);

        sender.sendMessage(ChatColor.AQUA + "aTracker.trackedPlayers.contains(b): " + ChatColor.WHITE + aTracker.trackedPlayers.contains(bCraft.getHandle()));
        sender.sendMessage(ChatColor.AQUA + "bTracker.trackedPlayers.contains(a): " + ChatColor.WHITE + bTracker.trackedPlayers.contains(aCraft.getHandle()));
    }

}