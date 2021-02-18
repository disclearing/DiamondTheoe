package net.frozenorb.potpvp;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.qrakn.morpheus.Morpheus;
import com.qrakn.morpheus.game.event.GameEvent;
import lombok.Getter;
import mkremins.fanciful.FancyMessage;
import net.frozenorb.chunksnapshot.ChunkSnapshot;
import net.frozenorb.potpvp.arena.ArenaHandler;
import net.frozenorb.potpvp.duel.DuelHandler;
import net.frozenorb.potpvp.elo.EloHandler;
import net.frozenorb.potpvp.follow.FollowHandler;
import net.frozenorb.potpvp.kit.KitHandler;
import net.frozenorb.potpvp.kittype.KitType;
import net.frozenorb.potpvp.kittype.KitTypeJsonAdapter;
import net.frozenorb.potpvp.kittype.KitTypeParameterType;
import net.frozenorb.potpvp.listener.*;
import net.frozenorb.potpvp.lobby.LobbyHandler;
import net.frozenorb.potpvp.match.Match;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.potpvp.morpheus.EventTask;
import net.frozenorb.potpvp.nametag.PotPvPNametagProvider;
import net.frozenorb.potpvp.party.PartyHandler;
import net.frozenorb.potpvp.postmatchinv.PostMatchInvHandler;
import net.frozenorb.potpvp.pvpclasses.PvPClassHandler;
import net.frozenorb.potpvp.queue.QueueHandler;
import net.frozenorb.potpvp.rematch.RematchHandler;
import net.frozenorb.potpvp.scoreboard.PotPvPScoreboardConfiguration;
import net.frozenorb.potpvp.setting.SettingHandler;
import net.frozenorb.potpvp.statistics.StatisticsHandler;
import net.frozenorb.potpvp.tab.PotPvPLayoutProvider;
import net.frozenorb.potpvp.tournament.TournamentHandler;
import net.frozenorb.qlib.command.FrozenCommandHandler;
import net.frozenorb.qlib.nametag.FrozenNametagHandler;
import net.frozenorb.qlib.scoreboard.FrozenScoreboardHandler;
import net.frozenorb.qlib.serialization.*;
import net.frozenorb.qlib.tab.FrozenTabHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public final class PotPvPSI extends JavaPlugin {

    private static PotPvPSI instance;
    @Getter
    private static Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(PotionEffect.class, new PotionEffectAdapter())
            .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
            .registerTypeHierarchyAdapter(Location.class, new LocationAdapter())
            .registerTypeHierarchyAdapter(Vector.class, new VectorAdapter())
            .registerTypeAdapter(BlockVector.class, new BlockVectorAdapter())
            .registerTypeHierarchyAdapter(KitType.class, new KitTypeJsonAdapter()) // custom KitType serializer
            .registerTypeAdapter(ChunkSnapshot.class, new ChunkSnapshotAdapter())
            .serializeNulls()
            .create();

    private MongoClient mongoClient;
    @Getter
    private MongoDatabase mongoDatabase;

    @Getter
    private SettingHandler settingHandler;
    @Getter
    private DuelHandler duelHandler;
    @Getter
    private KitHandler kitHandler;
    @Getter
    private LobbyHandler lobbyHandler;
    private ArenaHandler arenaHandler;
    @Getter
    private MatchHandler matchHandler;
    @Getter
    private PartyHandler partyHandler;
    @Getter
    private QueueHandler queueHandler;
    @Getter
    private RematchHandler rematchHandler;
    @Getter
    private PostMatchInvHandler postMatchInvHandler;
    @Getter
    private FollowHandler followHandler;
    @Getter
    private EloHandler eloHandler;
    @Getter
    private TournamentHandler tournamentHandler;
    @Getter
    private PvPClassHandler pvpClassHandler;

    @Getter
    private ChatColor dominantColor = ChatColor.DARK_AQUA;

    @Override
    public void onEnable() {
        //SpigotConfig.onlyCustomTab = true; // because we'll definitely forget
        this.dominantColor = ChatColor.DARK_AQUA;
        instance = this;
        saveDefaultConfig();

        setupMongo();

        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doMobSpawning", "false");
            world.setTime(6_000L);
        }

        settingHandler = new SettingHandler();
        duelHandler = new DuelHandler();
        kitHandler = new KitHandler();
        lobbyHandler = new LobbyHandler();
        arenaHandler = new ArenaHandler();
        matchHandler = new MatchHandler();
        partyHandler = new PartyHandler();
        queueHandler = new QueueHandler();
        rematchHandler = new RematchHandler();
        postMatchInvHandler = new PostMatchInvHandler();
        followHandler = new FollowHandler();
        eloHandler = new EloHandler();
        pvpClassHandler = new PvPClassHandler();
        tournamentHandler = new TournamentHandler();

        new Morpheus(this); // qrakn game events
        new EventTask().runTaskTimerAsynchronously(this, 1L, 1L);

        for (GameEvent event : GameEvent.getEvents()) {
            for (Listener listener : event.getListeners()) {
                getServer().getPluginManager().registerEvents(listener, this);
            }
        }

        getServer().getPluginManager().registerEvents(new BasicPreventionListener(), this);
        getServer().getPluginManager().registerEvents(new BowHealthListener(), this);
        getServer().getPluginManager().registerEvents(new ChatFormatListener(), this);
        getServer().getPluginManager().registerEvents(new ChatToggleListener(), this);
        getServer().getPluginManager().registerEvents(new NightModeListener(), this);
        getServer().getPluginManager().registerEvents(new PearlCooldownListener(), this);
        getServer().getPluginManager().registerEvents(new RankedMatchQualificationListener(), this);
        getServer().getPluginManager().registerEvents(new TabCompleteListener(), this);
        getServer().getPluginManager().registerEvents(new StatisticsHandler(), this);

        FrozenCommandHandler.registerAll(this);
        FrozenCommandHandler.registerParameterType(KitType.class, new KitTypeParameterType());
        FrozenTabHandler.setLayoutProvider(new PotPvPLayoutProvider());
        FrozenNametagHandler.registerProvider(new PotPvPNametagProvider());
        FrozenScoreboardHandler.setConfiguration(PotPvPScoreboardConfiguration.create());
        FrozenScoreboardHandler.setUpdateInterval(0);
    }

    @Override
    public void onDisable() {
        for (Match match : this.matchHandler.getHostedMatches()) {
            if (match.getKitType().isBuildingAllowed()) match.getArena().restore();
        }

        try {
            arenaHandler.saveSchematics();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String playerName : PvPClassHandler.getEquippedKits().keySet()) {
            PvPClassHandler.getEquippedKits().get(playerName).remove(getServer().getPlayerExact(playerName));
        }

        instance = null;
    }

    private void setupMongo() {
        if (getConfig().getBoolean("MONGO.AUTHENTICATION.ENABLED")) {
            mongoDatabase = new MongoClient(
                    new ServerAddress(
                            getConfig().getString("MONGO.HOST"),
                            getConfig().getInt("MONGO.PORT")
                    ),
                    MongoCredential.createCredential(
                            getConfig().getString("MONGO.AUTHENTICATION.USERNAME"),
                            "bbyjcanpntglimc", getConfig().getString("MONGO.AUTHENTICATION.PASSWORD").toCharArray()
                    ),
                    MongoClientOptions.builder().build()
            ).getDatabase("bbyjcanpntglimc");
        } else {
            mongoDatabase = new MongoClient(getConfig().getString("MONGO.HOST"), getConfig().getInt("MONGO.PORT"))
                    .getDatabase("bbyjcanpntglimc");
        }
    }

    // This is here because chunk snapshots are (still) being deserialized, and serialized sometimes.
    private static class ChunkSnapshotAdapter extends TypeAdapter<ChunkSnapshot> {

        @Override
        public ChunkSnapshot read(JsonReader arg0) throws IOException {
            return null;
        }

        @Override
        public void write(JsonWriter arg0, ChunkSnapshot arg1) throws IOException {

        }

    }

    public ArenaHandler getArenaHandler() {
        return arenaHandler;
    }

    public static PotPvPSI getInstance() {
        return instance;
    }
}