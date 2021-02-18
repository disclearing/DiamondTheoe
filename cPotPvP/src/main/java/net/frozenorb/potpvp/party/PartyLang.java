package net.frozenorb.potpvp.party;

import com.qrakn.morpheus.game.Game;
import net.frozenorb.qlib.util.UUIDUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.experimental.UtilityClass;
import xyz.skydevelopment.core.CoreAPI;

@UtilityClass
public final class PartyLang {

    private static final TextComponent INVITE_PREFIX = new TextComponent("Invite ");
    private static final TextComponent INVITE_THING = new TextComponent("» ");

    private static final TextComponent INVITED_YOU_TO_JOIN = new TextComponent(" invited you to join their party. ");

    private static final TextComponent ACCEPT_BUTTON = new TextComponent("(Accept)");
    private static final TextComponent INFO_BUTTON = new TextComponent("(Info)");

    static {
        INVITE_PREFIX.setColor(ChatColor.AQUA);
        INVITE_PREFIX.setBold(true);

        INVITE_THING.setColor(ChatColor.DARK_GRAY);
        INVITE_THING.setBold(true);

        INVITED_YOU_TO_JOIN.setColor(ChatColor.AQUA);

        HoverEvent.Action showText = HoverEvent.Action.SHOW_TEXT; // readability
        BaseComponent[] acceptTooltip = new ComponentBuilder("Click to join party").color(ChatColor.GREEN).create();

        ACCEPT_BUTTON.setColor(ChatColor.GREEN);
        ACCEPT_BUTTON.setHoverEvent(new HoverEvent(showText, acceptTooltip));

        INFO_BUTTON.setColor(ChatColor.AQUA);
    }

    public static TextComponent inviteAcceptPrompt(Party party) {
        ClickEvent.Action runCommand = ClickEvent.Action.RUN_COMMAND;
        String partyLeader = UUIDUtils.name(party.getLeader());

        // create copies via constructor (we're going to update their click event)
        TextComponent acceptButton = new TextComponent(ACCEPT_BUTTON);
        TextComponent infoButton = new TextComponent(INFO_BUTTON);

        acceptButton.setClickEvent(new ClickEvent(runCommand, "/p join " + partyLeader));

        infoButton.setHoverEvent(hoverablePreviewTooltip(party));
        infoButton.setClickEvent(new ClickEvent(runCommand, "/p info " + partyLeader));

        TextComponent builder = new TextComponent("");

        builder.addExtra(INVITE_PREFIX);
        builder.addExtra(INVITE_THING);
        builder.addExtra(hoverablePartyName(party));
        builder.addExtra(INVITED_YOU_TO_JOIN);
        builder.addExtra(acceptButton);
        builder.addExtra(new TextComponent(" "));
        builder.addExtra(infoButton);

        return builder;
    }

    public static TextComponent hoverablePartyName(Party party) {
        TextComponent previewComponent = new TextComponent();
        String leaderName = CoreAPI.INSTANCE.getProfile(party.getLeader()).getNameWithColor();

        // only show an actual tooltip for parties with >= 2 members,
        // parties that (to the user) don't exist yet just show up as a name
        if (party.getMembers().size() > 1) {
            HoverEvent hoverEvent = hoverablePreviewTooltip(party);

            previewComponent.setText(ChatColor.GRAY + "(" + leaderName + ChatColor.GRAY + "'s Party)");
            previewComponent.setHoverEvent(hoverEvent);
        } else {
            previewComponent.setText(leaderName);
        }

        previewComponent.setColor(ChatColor.AQUA);
        return previewComponent;
    }

    public static HoverEvent hoverablePreviewTooltip(Party party) {
        ComponentBuilder builder = new ComponentBuilder("Members (").color(ChatColor.AQUA);
        String size = "" + party.getMembers().size();

        builder.append(size).color(ChatColor.DARK_AQUA);
        builder.append("):").color(ChatColor.AQUA);

        for (String member : getMemberPreviewNames(party)) {
            builder.append("\n");
            builder.append(member);
        }

        HoverEvent.Action action = HoverEvent.Action.SHOW_TEXT;
        return new HoverEvent(action, builder.create());
    }

    // this method is probably named badly;
    // it puts min(partySize, 6) member display names into a set,
    // with a String indicating how many more members are present (if there are any)
    private static List<String> getMemberPreviewNames(Party party) {
        List<UUID> members = new ArrayList<>(party.getMembers());
        int partySize = members.size();
        List<String> displayNames = new ArrayList<>();

        for (int i = 0; i < Math.min(partySize, 6); i++) {
            UUID member = members.remove(0);
            String suffix = party.isLeader(member) ? "*" : "";

            displayNames.add(ChatColor.AQUA + CoreAPI.INSTANCE.getProfile(member).getNameWithColor() + suffix);
        }

        if (!members.isEmpty()) {
            displayNames.add(ChatColor.GRAY + "+ " + members.size() + " more");
        }

        return displayNames;
    }

}