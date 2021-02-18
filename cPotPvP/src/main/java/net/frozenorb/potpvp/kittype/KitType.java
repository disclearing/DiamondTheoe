package net.frozenorb.potpvp.kittype;

import com.mongodb.client.MongoCollection;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.util.MongoUtils;
import net.frozenorb.qlib.qLib;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.com.google.gson.annotations.SerializedName;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Denotes a type of Kit, under which players can queue, edit kits,
 * have elo, etc.
 */
// This class purposely uses qLib Gson (as we want to actualy serialize
// the fields within a KitType instead of pretending it's an enum) instead of ours.
public final class KitType {

    private static final String MONGO_COLLECTION_NAME = "kitTypes";
    @Getter private static final List<KitType> allTypes = new ArrayList<>();
    public static KitType teamFight = new KitType();

    static {
        MongoCollection<Document> collection = MongoUtils.getCollection(MONGO_COLLECTION_NAME);

        collection.find().iterator().forEachRemaining(doc -> {
            allTypes.add(qLib.PLAIN_GSON.fromJson(doc.toJson(), KitType.class));
        });

        teamFight.icon = new MaterialData(Material.BEACON);
        teamFight.id = "alex is a god xd";
        teamFight.displayName = "HCF Team Fight";
        teamFight.displayColor = ChatColor.AQUA;

        if (byId("BARD_HCF") == null) {
            KitType kitType = new KitType();
            kitType.id = "BARD_HCF";
            kitType.setDisplayName("Bard");
            kitType.setDisplayColor(ChatColor.AQUA);
            kitType.setIcon(new MaterialData(Material.GOLD_CHESTPLATE));
            kitType.hidden = true;

            allTypes.add(kitType);
        }

        if (byId("DIAMOND_HCF") == null) {
            KitType kitType = new KitType();
            kitType.id = "DIAMOND_HCF";
            kitType.setDisplayName("Diamond");
            kitType.setDisplayColor(ChatColor.AQUA);
            kitType.setIcon(new MaterialData(Material.DIAMOND_CHESTPLATE));
            kitType.hidden = true;

            allTypes.add(kitType);
        }

        if (byId("ARCHER_HCF") == null) {
            KitType kitType = new KitType();
            kitType.id = "ARCHER_HCF";
            kitType.setDisplayName("Archer");
            kitType.setDisplayColor(ChatColor.AQUA);
            kitType.setIcon(new MaterialData(Material.LEATHER_CHESTPLATE));
            kitType.hidden = true;

            allTypes.add(kitType);
        }

        if (byId("nodebuff") == null) {
            KitType kitType = new KitType();
            kitType.id = "nodebuff";
            kitType.setDisplayName("NoDebuff");
            kitType.setSupportsRanked(true);
            kitType.setIcon(new MaterialData(Material.DIAMOND_SWORD));
            kitType.setEditorSpawnAllowed(true);
            kitType.setHealingMethod(HealingMethod.POTIONS);
            kitType.setDisplayColor(ChatColor.AQUA);

            allTypes.add(kitType);
        }

        if (byId("debuff") == null) {
            KitType kitType = new KitType();
            kitType.id = "debuff";
            kitType.setDisplayName("Debuff");
            kitType.setSupportsRanked(true);
            kitType.setIcon(new MaterialData(Material.POTION));
            kitType.setEditorSpawnAllowed(true);
            kitType.setHealingMethod(HealingMethod.POTIONS);
            kitType.setDisplayColor(ChatColor.AQUA);

            allTypes.add(kitType);
        }

        if (byId("archer") == null) {
            KitType kitType = new KitType();
            kitType.id = "archer";
            kitType.setDisplayName("Archer");
            kitType.setSupportsRanked(true);
            kitType.setIcon(new MaterialData(Material.BOW));
            kitType.setEditorSpawnAllowed(true);
            kitType.setDisplayColor(ChatColor.DARK_GREEN);

            allTypes.add(kitType);
        }

        if (byId("sumo") == null) {
            KitType kitType = new KitType();
            kitType.id = "sumo";
            kitType.setDisplayName("Sumo");
            kitType.setSupportsRanked(true);
            kitType.setIcon(new MaterialData(Material.LEASH));
            kitType.setEditorSpawnAllowed(false);
            kitType.setDisplayColor(ChatColor.DARK_PURPLE);

            allTypes.add(kitType);
        }

        if (byId("spleef") == null) {
            KitType kitType = new KitType();
            kitType.id = "spleef";
            kitType.setDisplayName("Spleef");
            kitType.setSupportsRanked(true);
            kitType.setIcon(new MaterialData(Material.DIAMOND_AXE));
            kitType.setEditorSpawnAllowed(false);
            kitType.setDisplayColor(ChatColor.WHITE);

            allTypes.add(kitType);
        }

        if (byId("noenchants") == null) {
            KitType kitType = new KitType();
            kitType.id = "noenchants";
            kitType.setDisplayName("No Enchants");
            kitType.setSupportsRanked(true);
            kitType.setIcon(new MaterialData(Material.DIAMOND_CHESTPLATE));
            kitType.setEditorSpawnAllowed(true);
            kitType.setDisplayColor(ChatColor.AQUA);

            allTypes.add(kitType);
        }


        if (byId("gapple") == null) {
            KitType kitType = new KitType();
            kitType.id = "gapple";
            kitType.setDisplayName("Gapple");
            kitType.setSupportsRanked(true);
            kitType.setIcon(new MaterialData(Material.GOLDEN_APPLE));
            kitType.setEditorSpawnAllowed(true);
            kitType.setHealingMethod(HealingMethod.GOLDEN_APPLE);
            kitType.setDisplayColor(ChatColor.GOLD);

            allTypes.add(kitType);
        }

        if (byId("combo") == null) {
            KitType kitType = new KitType();
            kitType.id = "combo";
            kitType.setDisplayName("Combo");
            kitType.setSupportsRanked(true);
            kitType.setIcon(new MaterialData(Material.SNOW_BALL));
            kitType.setEditorSpawnAllowed(true);
            kitType.setHealingMethod(HealingMethod.GOLDEN_APPLE);
            kitType.setDisplayColor(ChatColor.DARK_GREEN);

            allTypes.add(kitType);
        }

        if (byId("builduhc") == null) {
            KitType kitType = new KitType();
            kitType.id = "builduhc";
            kitType.setDisplayName("Build UHC");
            kitType.setSupportsRanked(true);
            kitType.setIcon(new MaterialData(Material.LAVA_BUCKET));
            kitType.setEditorSpawnAllowed(true);
            kitType.setHardcoreHealing(true);
            kitType.setBuildingAllowed(true);
            kitType.setHealingMethod(HealingMethod.GOLDEN_APPLE);
            kitType.setDisplayColor(ChatColor.GOLD);

            allTypes.add(kitType);
        }
        allTypes.sort(Comparator.comparing(KitType::getSort));
    }

    /**
     * Id of this KitType, will be used when serializing the KitType for
     * database storage. Ex: "WIZARD", "NO_ENCHANTS", "SOUP"
     */
    @Getter @SerializedName("_id") private String id;

    /**
     * Display name of this KitType, will be used when communicating a KitType
     * to playerrs. Ex: "Wizard", "No Enchants", "Soup"
     */
    @Setter private String displayName;

    /**
     * Display color for this KitType, will be used in messages
     * or scoreboards sent to players.
     */
    @Getter @Setter private ChatColor displayColor;

    /**
     * Material info which will be used when rendering this
     * kit in selection menus and such.
     */
    @Setter private MaterialData icon;

    /**
     * Items which will be available for players to grab in the kit
     * editor, when making kits for this kit type.
     */
    @Getter @Setter private ItemStack[] editorItems = new ItemStack[0];

    /**
     * The armor that will be applied to players for this kit type.
     * Currently players are not allowed to edit their armor, they are
     * always given this armor.
     */
    @Setter private ItemStack[] defaultArmor = new ItemStack[0];

    /**
     * The default inventory that will be applied to players for this kit type.
     * Players are always allowed to rearange this inventory, so this only serves
     * as a default (in contrast to defaultArmor)
     */
    @Setter private ItemStack[] defaultInventory = new ItemStack[0];

    /**
     * Determines if players are allowed to spawn in items while editing their kits.
     * For some kit types (ex archer and axe) players can only rearange items in kits,
     * whereas some kit types (ex HCTeams and soup) allow spawning in items as well.
     */
    @Getter @Setter private boolean editorSpawnAllowed = true;

    /**
     * Determines if normal, non-admin players should be able to see this KitType.
     */
    @Getter @Setter private boolean hidden = false;

    /**
     * Determines how players regain health in matches using this KitType.
     * This is used primarily for applying logic for souping + rendering
     * heals remaining in the post match inventory
     */
    @Getter @Setter private HealingMethod healingMethod = HealingMethod.POTIONS;

    /**
     * Determines if players are allowed to build in matches using this KitType.
     */
    @Getter @Setter private boolean buildingAllowed = false;

    /**
     * Determines if health is shown below the player's name-tags in matches using this KitType.
     */
    @Getter @Setter private boolean healthShown = false;

    /**
     * Determines if natural health regeneration should happen in matches using this KitType.
     */
    @Getter @Setter private boolean hardcoreHealing = false;

    /**
     * Determines if players playing a match using this KitType should take damage when their ender pearl lands.
     */
    @Getter @Setter private boolean pearlDamage = true;

    /**
     * Determines the order used when displaying lists of KitTypes to players.
     * (Lowest to highest)
     */
    @Getter @Setter private int sort = 0;

    @Getter @Setter private boolean supportsRanked = false;

    public static KitType byId(String id) {
        for (KitType kitType : allTypes) {
            if (kitType.getId().equalsIgnoreCase(id)) {
                return kitType;
            }
        }

        return null;
    }

    public String getColoredDisplayName() {
        return displayColor + displayName;
    }

    public void saveAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(PotPvPSI.getInstance(), () -> {
            MongoCollection<Document> collection = MongoUtils.getCollection(MONGO_COLLECTION_NAME);
            Document kitTypeDoc = Document.parse(qLib.PLAIN_GSON.toJson(this));
            kitTypeDoc.remove("_id"); // upserts with an _id field is weird.

            Document query = new Document("_id", id);
            Document kitUpdate = new Document("$set", kitTypeDoc);

            collection.updateOne(query, kitUpdate, MongoUtils.UPSERT_OPTIONS);
        });
    }

    @Override
    public String toString() {
        return displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public MaterialData getIcon() {
        return icon;
    }

    public ItemStack[] getDefaultArmor() {
        return defaultArmor;
    }

    public ItemStack[] getDefaultInventory() {
        return defaultInventory;
    }

}