package org.morazzer.serversystem.spigot.impl.api.types.dataclas;

import com.destroystokyo.paper.ClientOption;
import com.destroystokyo.paper.Title;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import com.destroystokyo.paper.profile.PlayerProfile;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.morazzer.serversystem.spigot.api.RankApi;
import org.morazzer.serversystem.spigot.api.types.interfaces.OfflineUser;
import org.morazzer.serversystem.spigot.api.types.interfaces.Rank;
import org.morazzer.serversystem.spigot.api.types.interfaces.User;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author Morazzer
 * @since Date 17.10.2020 15:28:42
 */
public class UserImpl implements User {

    private final Player player;
    private final Rank rank;

    public UserImpl(OfflineUser offlineUser) {
        player = Bukkit.getPlayer(offlineUser.getUniqueId());
        rank = RankApi.getInstance().getRank(offlineUser.getRankUUID());
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "player={" +
                "name=" + player.getName() +
                ", uuid=" + player.getUniqueId() +
                "}" +
                ", rank={" +
                "name=" + rank.getName() +
                ", UUID=" + rank.getUUID() +
                ", inheritUUID=" + rank.getInheritUUID() +
                "}" +
                '}';
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }

    @Override
    public void setDisplayName(@Nullable String name) {
        player.setDisplayName(name);
    }

    @NotNull
    @Override
    public String getPlayerListName() {
        return player.getPlayerListName();
    }

    @Override
    public void setPlayerListName(@Nullable String name) {
        player.setPlayerListName(name);
    }

    @Nullable
    @Override
    public String getPlayerListHeader() {
        return player.getPlayerListHeader();
    }

    @Nullable
    @Override
    public String getPlayerListFooter() {
        return player.getPlayerListFooter();
    }

    @Override
    public void setPlayerListHeader(@Nullable String header) {
        player.setPlayerListHeader(header);
    }

    @Override
    public void setPlayerListFooter(@Nullable String footer) {
        player.setPlayerListFooter(footer);
    }

    @Override
    public void setPlayerListHeaderFooter(@Nullable String header, @Nullable String footer) {
        player.setPlayerListHeaderFooter(header, footer);
    }

    @Override
    public void setCompassTarget(@NotNull Location loc) {
        player.setCompassTarget(loc);
    }

    @NotNull
    @Override
    public Location getCompassTarget() {
        return player.getCompassTarget();
    }

    @Nullable
    @Override
    public InetSocketAddress getAddress() {
        return player.getAddress();
    }

    @Override
    public int getProtocolVersion() {
        return player.getProtocolVersion();
    }

    @Nullable
    @Override
    public InetSocketAddress getVirtualHost() {
        return player.getVirtualHost();
    }

    @Override
    public boolean isConversing() {
        return player.isConversing();
    }

    @Override
    public void acceptConversationInput(@NotNull String input) {
        player.acceptConversationInput(input);
    }

    @Override
    public boolean beginConversation(@NotNull Conversation conversation) {
        return player.beginConversation(conversation);
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation) {
        player.abandonConversation(conversation);
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent details) {
        player.abandonConversation(conversation, details);
    }

    @Override
    public void sendRawMessage(@NotNull String message) {
        player.sendRawMessage(message);
    }

    @Override
    public void kickPlayer(@Nullable String message) {
        player.kickPlayer(message);
    }

    @Override
    public void chat(@NotNull String msg) {
        player.chat(msg);
    }

    @Override
    public boolean performCommand(@NotNull String command) {
        return player.performCommand(command);
    }

    @NotNull
    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Nullable
    @Override
    public Location getLocation(@Nullable Location loc) {
        return player.getLocation(loc);
    }

    @Override
    public void setVelocity(@NotNull Vector velocity) {
        player.setVelocity(velocity);
    }

    @NotNull
    @Override
    public Vector getVelocity() {
        return player.getVelocity();
    }

    @Override
    public double getHeight() {
        return player.getHeight();
    }

    @Override
    public double getWidth() {
        return player.getWidth();
    }

    @NotNull
    @Override
    public BoundingBox getBoundingBox() {
        return player.getBoundingBox();
    }

    @Override
    public boolean isOnGround() {
        return player.getLocation().subtract(0,1,0).getBlock().getType() == Material.AIR;
    }

    @NotNull
    @Override
    public World getWorld() {
        return player.getWorld();
    }

    @Override
    public void setRotation(float yaw, float pitch) {
        player.setRotation(yaw, pitch);
    }

    @Override
    public boolean teleport(@NotNull Location location) {
        return player.teleport(location);
    }

    @Override
    public boolean teleport(@NotNull Location location, @NotNull PlayerTeleportEvent.TeleportCause cause) {
        return player.teleport(location, cause);
    }

    @Override
    public boolean teleport(@NotNull Entity destination) {
        return player.teleport(destination);
    }

    @Override
    public boolean teleport(@NotNull Entity destination, @NotNull PlayerTeleportEvent.TeleportCause cause) {
        return player.teleport(destination, cause);
    }

    @NotNull
    @Override
    public List<Entity> getNearbyEntities(double x, double y, double z) {
        return player.getNearbyEntities(x, y, z);
    }

    @Override
    public int getEntityId() {
        return player.getEntityId();
    }

    @Override
    public int getFireTicks() {
        return player.getFireTicks();
    }

    @Override
    public int getMaxFireTicks() {
        return player.getMaxFireTicks();
    }

    @Override
    public void setFireTicks(int ticks) {
        player.setFireTicks(ticks);
    }

    @Override
    public void remove() {
        player.remove();
    }

    @Override
    public boolean isDead() {
        return player.isDead();
    }

    @Override
    public boolean isValid() {
        return player.isValid();
    }

    @Override
    public void sendMessage(@NotNull String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull String[] messages) {
        player.sendMessage(messages);
    }

    @NotNull
    @Override
    public Server getServer() {
        return player.getServer();
    }

    @Override
    public boolean isPersistent() {
        return player.isPersistent();
    }

    @Override
    public void setPersistent(boolean persistent) {
        player.setPersistent(persistent);
    }

    @Nullable
    @Override
    @Deprecated
    public Entity getPassenger() {
        return player.getPassenger();
    }

    @Override
    @Deprecated
    public boolean setPassenger(@NotNull Entity passenger) {
        return player.setPassenger(passenger);
    }

    @NotNull
    @Override
    public List<Entity> getPassengers() {
        return player.getPassengers();
    }

    @Override
    public boolean addPassenger(@NotNull Entity passenger) {
        return player.addPassenger(passenger);
    }

    @Override
    public boolean removePassenger(@NotNull Entity passenger) {
        return player.removePassenger(passenger);
    }

    @Override
    public boolean isEmpty() {
        return player.isEmpty();
    }

    @Override
    public boolean eject() {
        return player.eject();
    }

    @Override
    public float getFallDistance() {
        return player.getFallDistance();
    }

    @Override
    public void setFallDistance(float distance) {
        player.setFallDistance(distance);
    }

    @Override
    public void setLastDamageCause(@Nullable EntityDamageEvent event) {
        player.setLastDamageCause(event);
    }

    @Nullable
    @Override
    public EntityDamageEvent getLastDamageCause() {
        return player.getLastDamageCause();
    }

    @NotNull
    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public int getTicksLived() {
        return player.getTicksLived();
    }

    @Override
    public void setTicksLived(int value) {
        player.setTicksLived(value);
    }

    @Override
    public void playEffect(@NotNull EntityEffect type) {
        player.playEffect(type);
    }

    @NotNull
    @Override
    public EntityType getType() {
        return player.getType();
    }

    @Override
    public boolean isInsideVehicle() {
        return player.isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle() {
        return player.leaveVehicle();
    }

    @Nullable
    @Override
    public Entity getVehicle() {
        return player.getVehicle();
    }

    @Override
    public void setCustomNameVisible(boolean flag) {
        player.setCustomNameVisible(flag);
    }

    @Override
    public boolean isCustomNameVisible() {
        return player.isCustomNameVisible();
    }

    @Override
    public void setGlowing(boolean flag) {
        player.setGlowing(flag);
    }

    @Override
    public boolean isGlowing() {
        return player.isGlowing();
    }

    @Override
    public void setInvulnerable(boolean flag) {
        player.setInvulnerable(flag);
    }

    @Override
    public boolean isInvulnerable() {
        return player.isInvulnerable();
    }

    @Override
    public boolean isSilent() {
        return player.isSilent();
    }

    @Override
    public void setSilent(boolean flag) {
        player.setSilent(flag);
    }

    @Override
    public boolean hasGravity() {
        return player.hasGravity();
    }

    @Override
    public void setGravity(boolean gravity) {
        player.setGravity(gravity);
    }

    @Override
    public int getPortalCooldown() {
        return player.getPortalCooldown();
    }

    @Override
    public void setPortalCooldown(int cooldown) {
        player.setPortalCooldown(cooldown);
    }

    @NotNull
    @Override
    public Set<String> getScoreboardTags() {
        return player.getScoreboardTags();
    }

    @Override
    public boolean addScoreboardTag(@NotNull String tag) {
        return player.addScoreboardTag(tag);
    }

    @Override
    public boolean removeScoreboardTag(@NotNull String tag) {
        return player.removeScoreboardTag(tag);
    }

    @NotNull
    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return player.getPistonMoveReaction();
    }

    @NotNull
    @Override
    public BlockFace getFacing() {
        return player.getFacing();
    }

    @NotNull
    @Override
    public Pose getPose() {
        return player.getPose();
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public void setSneaking(boolean sneak) {
        player.setSneaking(sneak);
    }

    @Override
    public boolean isSprinting() {
        return player.isSprinting();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        player.setSprinting(sprinting);
    }

    @Override
    public void saveData() {
        player.saveData();
    }

    @Override
    public void loadData() {
        player.loadData();
    }

    @Override
    public void setSleepingIgnored(boolean isSleeping) {
        player.setSleepingIgnored(isSleeping);
    }

    @Override
    public boolean isSleepingIgnored() {
        return player.isSleepingIgnored();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public boolean isBanned() {
        return player.isBanned();
    }

    @Override
    public boolean isWhitelisted() {
        return player.isWhitelisted();
    }

    @Override
    public void setWhitelisted(boolean value) {
        player.setWhitelisted(value);
    }

    @Nullable
    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public long getFirstPlayed() {
        return player.getFirstPlayed();
    }

    @Override
    @Deprecated
    public long getLastPlayed() {
        return player.getLastPlayed();
    }

    @Override
    public boolean hasPlayedBefore() {
        return player.hasPlayedBefore();
    }

    @Nullable
    @Override
    public Location getBedSpawnLocation() {
        return player.getBedSpawnLocation();
    }

    @Override
    public long getLastLogin() {
        return player.getLastLogin();
    }

    @Override
    public long getLastSeen() {
        return player.getLastSeen();
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        player.incrementStatistic(statistic);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        player.decrementStatistic(statistic);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, int amount) throws IllegalArgumentException {
        player.incrementStatistic(statistic, amount);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, int amount) throws IllegalArgumentException {
        player.decrementStatistic(statistic, amount);
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, int newValue) throws IllegalArgumentException {
        player.setStatistic(statistic, newValue);
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        return player.getStatistic(statistic);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        player.incrementStatistic(statistic, material);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        player.decrementStatistic(statistic, material);
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        return player.getStatistic(statistic, material);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount) throws IllegalArgumentException {
        player.incrementStatistic(statistic, material, amount);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount) throws IllegalArgumentException {
        player.decrementStatistic(statistic, material, amount);
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, @NotNull Material material, int newValue) throws IllegalArgumentException {
        player.setStatistic(statistic, material, newValue);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        player.incrementStatistic(statistic, entityType);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        player.decrementStatistic(statistic, entityType);
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        return player.getStatistic(statistic, entityType);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int amount) throws IllegalArgumentException {
        player.incrementStatistic(statistic, entityType, amount);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int amount) {
        player.decrementStatistic(statistic, entityType, amount);
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int newValue) {
        player.setStatistic(statistic, entityType, newValue);
    }

    @Override
    public void setBedSpawnLocation(@Nullable Location location) {
        player.setBedSpawnLocation(location);
    }

    @Override
    public void setBedSpawnLocation(@Nullable Location location, boolean force) {
        player.setBedSpawnLocation(location, force);
    }

    @Override
    @Deprecated
    public void playNote(@NotNull Location loc, byte instrument, byte note) {
        player.playNote(loc, instrument, note);
    }

    @Override
    public void playNote(@NotNull Location loc, @NotNull Instrument instrument, @NotNull Note note) {
        player.playNote(loc, instrument, note);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull Sound sound, float volume, float pitch) {
        player.playSound(location, sound, volume, pitch);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull String sound, float volume, float pitch) {
        player.playSound(location, sound, volume, pitch);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory category, float volume, float pitch) {
        player.playSound(location, sound, category, volume, pitch);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull String sound, @NotNull SoundCategory category, float volume, float pitch) {
        player.playSound(location, sound, category, volume, pitch);
    }

    @Override
    public void stopSound(@NotNull Sound sound) {
        player.stopSound(sound);
    }

    @Override
    public void stopSound(@NotNull String sound) {
        player.stopSound(sound);
    }

    @Override
    public void stopSound(@NotNull Sound sound, @Nullable SoundCategory category) {
        player.stopSound(sound, category);
    }

    @Override
    public void stopSound(@NotNull String sound, @Nullable SoundCategory category) {
        player.stopSound(sound, category);
    }

    @Override
    @Deprecated
    public void playEffect(@NotNull Location loc, @NotNull Effect effect, int data) {
        player.playEffect(loc, effect, data);
    }

    @Override
    public <T> void playEffect(@NotNull Location loc, @NotNull Effect effect, @Nullable T data) {
        player.playEffect(loc, effect, data);
    }

    @Override
    @Deprecated
    public void sendBlockChange(@NotNull Location loc, @NotNull Material material, byte data) {
        player.sendBlockChange(loc, material, data);
    }

    @Override
    public void sendBlockChange(@NotNull Location loc, @NotNull BlockData block) {
        player.sendBlockChange(loc, block);
    }

    @Override
    @Deprecated
    public boolean sendChunkChange(@NotNull Location loc, int sx, int sy, int sz, byte[] data) {
        return player.sendChunkChange(loc, sx, sy, sz, data);
    }

    @Override
    public void sendSignChange(@NotNull Location loc, @Nullable String[] lines) throws IllegalArgumentException {
        player.sendSignChange(loc, lines);
    }

    @Override
    public void sendSignChange(@NotNull Location loc, @Nullable String[] lines, @NotNull DyeColor dyeColor) throws IllegalArgumentException {
        player.sendSignChange(loc, lines, dyeColor);
    }

    @Override
    public void sendMap(@NotNull MapView map) {
        player.sendMap(map);
    }

    @Override
    public void sendActionBar(@NotNull String message) {
        player.sendActionBar(message);
    }

    @Override
    public void sendActionBar(char alternateChar, @NotNull String message) {
        player.sendActionBar(alternateChar, message);
    }

    @Override
    public void sendActionBar(@NotNull BaseComponent... message) {
        player.sendActionBar(message);
    }

    @Override
    public void setPlayerListHeaderFooter(@Nullable BaseComponent[] header, @Nullable BaseComponent[] footer) {
        player.setPlayerListHeaderFooter(header, footer);
    }

    @Override
    public void setPlayerListHeaderFooter(@Nullable BaseComponent header, @Nullable BaseComponent footer) {
        player.setPlayerListHeaderFooter(header, footer);
    }

    @Override
    @Deprecated
    public void setTitleTimes(int fadeInTicks, int stayTicks, int fadeOutTicks) {
        player.setTitleTimes(fadeInTicks, stayTicks, fadeOutTicks);
    }

    @Override
    @Deprecated
    public void setSubtitle(BaseComponent[] subtitle) {
        player.setSubtitle(subtitle);
    }

    @Override
    @Deprecated
    public void setSubtitle(BaseComponent subtitle) {
        player.setSubtitle(subtitle);
    }

    @Override
    @Deprecated
    public void showTitle(@Nullable BaseComponent[] title) {
        player.showTitle(title);
    }

    @Override
    @Deprecated
    public void showTitle(@Nullable BaseComponent title) {
        player.showTitle(title);
    }

    @Override
    @Deprecated
    public void showTitle(@Nullable BaseComponent[] title, @Nullable BaseComponent[] subtitle, int fadeInTicks, int stayTicks, int fadeOutTicks) {
        player.showTitle(title, subtitle, fadeInTicks, stayTicks, fadeOutTicks);
    }

    @Override
    @Deprecated
    public void showTitle(@Nullable BaseComponent title, @Nullable BaseComponent subtitle, int fadeInTicks, int stayTicks, int fadeOutTicks) {
        player.showTitle(title, subtitle, fadeInTicks, stayTicks, fadeOutTicks);
    }

    @Override
    public void sendTitle(@NotNull Title title) {
        player.sendTitle(title);
    }

    @Override
    public void updateTitle(@NotNull Title title) {
        player.updateTitle(title);
    }

    @Override
    public void hideTitle() {
        player.hideTitle();
    }

    @Override
    public void updateInventory() {
        player.updateInventory();
    }

    @Override
    public void setPlayerTime(long time, boolean relative) {
        player.setPlayerTime(time, relative);
    }

    @Override
    public long getPlayerTime() {
        return player.getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        return player.getPlayerTimeOffset();
    }

    @Override
    public boolean isPlayerTimeRelative() {
        return player.isPlayerTimeRelative();
    }

    @Override
    public void resetPlayerTime() {
        player.resetPlayerTime();
    }

    @Override
    public void setPlayerWeather(@NotNull WeatherType type) {
        player.setPlayerWeather(type);
    }

    @Nullable
    @Override
    public WeatherType getPlayerWeather() {
        return player.getPlayerWeather();
    }

    @Override
    public void resetPlayerWeather() {
        player.resetPlayerWeather();
    }

    @Override
    public void giveExp(int amount, boolean applyMending) {
        player.giveExp(amount, applyMending);
    }

    @Override
    public int applyMending(int amount) {
        return player.applyMending(amount);
    }

    @Override
    public void giveExpLevels(int amount) {
        player.giveExpLevels(amount);
    }

    @Override
    public float getExp() {
        return player.getExp();
    }

    @Override
    public void setExp(float exp) {
        player.setExp(exp);
    }

    @Override
    public int getLevel() {
        return player.getLevel();
    }

    @Override
    public void setLevel(int level) {
        player.setLevel(level);
    }

    @Override
    public int getTotalExperience() {
        return player.getTotalExperience();
    }

    @Override
    public void setTotalExperience(int exp) {
        player.setTotalExperience(exp);
    }

    @Override
    public void sendExperienceChange(float progress) {
        player.sendExperienceChange(progress);
    }

    @Override
    public void sendExperienceChange(float progress, int level) {
        player.sendExperienceChange(progress, level);
    }

    @Override
    public float getExhaustion() {
        return player.getExhaustion();
    }

    @Override
    public void setExhaustion(float value) {
        player.setExhaustion(value);
    }

    @Override
    public float getSaturation() {
        return player.getSaturation();
    }

    @Override
    public void setSaturation(float value) {
        player.setSaturation(value);
    }

    @Override
    public int getFoodLevel() {
        return player.getFoodLevel();
    }

    @Override
    public void setFoodLevel(int value) {
        player.setFoodLevel(value);
    }

    @Override
    public boolean getAllowFlight() {
        return player.getAllowFlight();
    }

    @Override
    public void setAllowFlight(boolean flight) {
        player.setAllowFlight(flight);
    }

    @Override
    @Deprecated
    public void hidePlayer(@NotNull Player player) {
        player.hidePlayer(player);
    }

    @Override
    public void hidePlayer(@NotNull Plugin plugin, @NotNull Player player) {
        player.hidePlayer(plugin, player);
    }

    @Override
    @Deprecated
    public void showPlayer(@NotNull Player player) {
        player.showPlayer(player);
    }

    @Override
    public void showPlayer(@NotNull Plugin plugin, @NotNull Player player) {
        player.showPlayer(plugin, player);
    }

    @Override
    public boolean canSee(@NotNull Player player) {
        return player.canSee(player);
    }

    @Override
    public boolean isFlying() {
        return player.isFlying();
    }

    @Override
    public void setFlying(boolean value) {
        player.setFlying(value);
    }

    @Override
    public void setFlySpeed(float value) throws IllegalArgumentException {
        player.setFlySpeed(value);
    }

    @Override
    public void setWalkSpeed(float value) throws IllegalArgumentException {
        player.setWalkSpeed(value);
    }

    @Override
    public float getFlySpeed() {
        return player.getFlySpeed();
    }

    @Override
    public float getWalkSpeed() {
        return player.getWalkSpeed();
    }

    @Override
    @Deprecated
    public void setTexturePack(@NotNull String url) {
        player.setTexturePack(url);
    }

    @Override
    @Deprecated
    public void setResourcePack(@NotNull String url) {
        player.setResourcePack(url);
    }

    @Override
    public void setResourcePack(@NotNull String url, byte[] hash) {
        player.setResourcePack(url, hash);
    }

    @NotNull
    @Override
    public Scoreboard getScoreboard() {
        return player.getScoreboard();
    }

    @Override
    public void setScoreboard(@NotNull Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException {
        player.setScoreboard(scoreboard);
    }

    @Override
    public boolean isHealthScaled() {
        return player.isHealthScaled();
    }

    @Override
    public void setHealthScaled(boolean scale) {
        player.setHealthScaled(scale);
    }

    @Override
    public void setHealthScale(double scale) throws IllegalArgumentException {
        player.setHealthScale(scale);
    }

    @Override
    public double getHealthScale() {
        return player.getHealthScale();
    }

    @Nullable
    @Override
    public Entity getSpectatorTarget() {
        return player.getSpectatorTarget();
    }

    @Override
    public void setSpectatorTarget(@Nullable Entity entity) {
        player.setSpectatorTarget(entity);
    }

    @Override
    @Deprecated
    public void sendTitle(@Nullable String title, @Nullable String subtitle) {
        player.sendTitle(title, subtitle);
    }

    @Override
    public void sendTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    @Override
    public void resetTitle() {
        player.resetTitle();
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count) {
        player.spawnParticle(particle, location, count);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count) {
        player.spawnParticle(particle, x, y, z, count);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, @Nullable T data) {
        player.spawnParticle(particle, location, count, data);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, @Nullable T data) {
        player.spawnParticle(particle, x, y, z, count, data);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY, double offsetZ) {
        player.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ) {
        player.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY, double offsetZ, @Nullable T data) {
        player.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, data);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, @Nullable T data) {
        player.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, data);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        player.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        player.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, @Nullable T data) {
        player.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra, data);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, @Nullable T data) {
        player.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, data);
    }

    @NotNull
    @Override
    public AdvancementProgress getAdvancementProgress(@NotNull Advancement advancement) {
        return player.getAdvancementProgress(advancement);
    }

    @Override
    public int getClientViewDistance() {
        return player.getClientViewDistance();
    }

    @NotNull
    @Override
    public String getLocale() {
        return player.getLocale();
    }

    @Override
    public boolean getAffectsSpawning() {
        return player.getAffectsSpawning();
    }

    @Override
    public void setAffectsSpawning(boolean affects) {
        player.setAffectsSpawning(affects);
    }

    @Override
    public int getViewDistance() {
        return player.getViewDistance();
    }

    @Override
    public void setViewDistance(int viewDistance) {
        player.setViewDistance(viewDistance);
    }

    @Override
    public void updateCommands() {
        player.updateCommands();
    }

    @Override
    public void openBook(@NotNull ItemStack book) {
        player.openBook(book);
    }

    @Override
    public void setResourcePack(@NotNull String url, @NotNull String hash) {
        player.setResourcePack(url, hash);
    }

    @Nullable
    @Override
    public PlayerResourcePackStatusEvent.Status getResourcePackStatus() {
        return player.getResourcePackStatus();
    }

    @Nullable
    @Override
    @Deprecated
    public String getResourcePackHash() {
        return player.getResourcePackHash();
    }

    @Override
    public boolean hasResourcePack() {
        return player.hasResourcePack();
    }

    @NotNull
    @Override
    public PlayerProfile getPlayerProfile() {
        return player.getPlayerProfile();
    }

    @Override
    public void setPlayerProfile(@NotNull PlayerProfile profile) {
        player.setPlayerProfile(profile);
    }

    @Override
    public float getCooldownPeriod() {
        return player.getCooldownPeriod();
    }

    @Override
    public float getCooledAttackStrength(float adjustTicks) {
        return player.getCooledAttackStrength(adjustTicks);
    }

    @Override
    public void resetCooldown() {
        player.resetCooldown();
    }

    @NotNull
    @Override
    public <T> T getClientOption(@NotNull ClientOption<T> option) {
        return player.getClientOption(option);
    }

    @Nullable
    @Override
    public String getClientBrandName() {
        return player.getClientBrandName();
    }

    @NotNull
    @Override
    public Spigot spigot() {
        return player.spigot();
    }

    @Nullable
    @Override
    public Location getOrigin() {
        return player.getOrigin();
    }

    @Override
    public boolean fromMobSpawner() {
        return player.fromMobSpawner();
    }

    @NotNull
    @Override
    public Chunk getChunk() {
        return player.getChunk();
    }

    @NotNull
    @Override
    public CreatureSpawnEvent.SpawnReason getEntitySpawnReason() {
        return player.getEntitySpawnReason();
    }

    @Override
    public boolean isInWater() {
        return player.isInWater();
    }

    @Override
    public boolean isInRain() {
        return player.isInRain();
    }

    @Override
    public boolean isInBubbleColumn() {
        return player.isInBubbleColumn();
    }

    @Override
    public boolean isInWaterOrRain() {
        return player.isInWaterOrRain();
    }

    @Override
    public boolean isInWaterOrBubbleColumn() {
        return player.isInWaterOrBubbleColumn();
    }

    @Override
    public boolean isInWaterOrRainOrBubbleColumn() {
        return player.isInWaterOrRainOrBubbleColumn();
    }

    @Override
    public boolean isInLava() {
        return player.isInLava();
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return player.serialize();
    }

    @NotNull
    @Override
    public String getName() {
        return player.getName();
    }

    @NotNull
    @Override
    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    @NotNull
    @Override
    public Inventory getEnderChest() {
        return player.getEnderChest();
    }

    @NotNull
    @Override
    public MainHand getMainHand() {
        return player.getMainHand();
    }

    @Override
    public boolean setWindowProperty(@NotNull InventoryView.Property prop, int value) {
        return player.setWindowProperty(prop, value);
    }

    @NotNull
    @Override
    public InventoryView getOpenInventory() {
        return player.getOpenInventory();
    }

    @Nullable
    @Override
    public InventoryView openInventory(@NotNull Inventory inventory) {
        return player.openInventory(inventory);
    }

    @Nullable
    @Override
    public InventoryView openWorkbench(@Nullable Location location, boolean force) {
        return player.openWorkbench(location, force);
    }

    @Nullable
    @Override
    public InventoryView openEnchanting(@Nullable Location location, boolean force) {
        return player.openEnchanting(location, force);
    }

    @Override
    public void openInventory(@NotNull InventoryView inventory) {
        player.openInventory(inventory);
    }

    @Nullable
    @Override
    public InventoryView openMerchant(@NotNull Villager trader, boolean force) {
        return player.openMerchant(trader, force);
    }

    @Nullable
    @Override
    public InventoryView openMerchant(@NotNull Merchant merchant, boolean force) {
        return player.openMerchant(merchant, force);
    }

    @Nullable
    @Override
    public InventoryView openAnvil(@Nullable Location location, boolean force) {
        return player.openAnvil(location, force);
    }

    @Nullable
    @Override
    public InventoryView openCartographyTable(@Nullable Location location, boolean force) {
        return player.openCartographyTable(location, force);
    }

    @Nullable
    @Override
    public InventoryView openGrindstone(@Nullable Location location, boolean force) {
        return player.openGrindstone(location, force);
    }

    @Nullable
    @Override
    public InventoryView openLoom(@Nullable Location location, boolean force) {
        return player.openLoom(location, force);
    }

    @Nullable
    @Override
    public InventoryView openSmithingTable(@Nullable Location location, boolean force) {
        return player.openSmithingTable(location, force);
    }

    @Nullable
    @Override
    public InventoryView openStonecutter(@Nullable Location location, boolean force) {
        return player.openStonecutter(location, force);
    }

    @Override
    public void closeInventory() {
        player.closeInventory();
    }

    @Override
    public void closeInventory(@NotNull InventoryCloseEvent.Reason reason) {
        player.closeInventory();
    }

    @NotNull
    @Override
    @Deprecated
    public ItemStack getItemInHand() {
        return player.getItemInHand();
    }

    @Override
    @Deprecated
    public void setItemInHand(@Nullable ItemStack item) {
        player.setItemInHand(item);
    }

    @NotNull
    @Override
    public ItemStack getItemOnCursor() {
        return player.getItemOnCursor();
    }

    @Override
    public void setItemOnCursor(@Nullable ItemStack item) {
        player.setItemOnCursor(item);
    }

    @Override
    public boolean hasCooldown(@NotNull Material material) {
        return player.hasCooldown(material);
    }

    @Override
    public int getCooldown(@NotNull Material material) {
        return player.getCooldown(material);
    }

    @Override
    public void setCooldown(@NotNull Material material, int ticks) {
        player.setCooldown(material, ticks);
    }

    @Override
    public int getSleepTicks() {
        return player.getSleepTicks();
    }

    @Nullable
    @Override
    public Location getPotentialBedLocation() {
        return player.getPotentialBedLocation();
    }

    @Override
    public boolean sleep(@NotNull Location location, boolean force) {
        return player.sleep(location, force);
    }

    @Override
    public void wakeup(boolean setSpawnLocation) {
        player.wakeup(setSpawnLocation);
    }

    @NotNull
    @Override
    public Location getBedLocation() {
        return player.getBedLocation();
    }

    @NotNull
    @Override
    public GameMode getGameMode() {
        return player.getGameMode();
    }

    @Override
    public void setGameMode(@NotNull GameMode mode) {

    }

    @Override
    public boolean isBlocking() {
        return player.isBlocking();
    }

    @Override
    public double getEyeHeight() {
        return player.getEyeHeight();
    }

    @Override
    public double getEyeHeight(boolean ignorePose) {
        return player.getEyeHeight(ignorePose);
    }

    @NotNull
    @Override
    public Location getEyeLocation() {
        return player.getEyeLocation();
    }

    @NotNull
    @Override
    public List<Block> getLineOfSight(@Nullable Set<Material> transparent, int maxDistance) {
        return player.getLineOfSight(transparent, maxDistance);
    }

    @NotNull
    @Override
    public Block getTargetBlock(@Nullable Set<Material> transparent, int maxDistance) {
        return player.getTargetBlock(transparent, maxDistance);
    }

    @Nullable
    @Override
    public Block getTargetBlock(int maxDistance, @NotNull TargetBlockInfo.FluidMode fluidMode) {
        return player.getTargetBlock(maxDistance, fluidMode);
    }

    @Nullable
    @Override
    public BlockFace getTargetBlockFace(int maxDistance, @NotNull TargetBlockInfo.FluidMode fluidMode) {
        return player.getTargetBlockFace(maxDistance, fluidMode);
    }

    @Nullable
    @Override
    public TargetBlockInfo getTargetBlockInfo(int maxDistance, @NotNull TargetBlockInfo.FluidMode fluidMode) {
        return player.getTargetBlockInfo(maxDistance, fluidMode);
    }

    @Nullable
    @Override
    public Entity getTargetEntity(int maxDistance, boolean ignoreBlocks) {
        return player.getTargetEntity(maxDistance, ignoreBlocks);
    }

    @Nullable
    @Override
    public TargetEntityInfo getTargetEntityInfo(int maxDistance, boolean ignoreBlocks) {
        return player.getTargetEntityInfo(maxDistance, ignoreBlocks);
    }

    @NotNull
    @Override
    public List<Block> getLastTwoTargetBlocks(@Nullable Set<Material> transparent, int maxDistance) {
        return player.getLastTwoTargetBlocks(transparent, maxDistance);
    }

    @Nullable
    @Override
    public Block getTargetBlockExact(int maxDistance) {
        return player.getTargetBlockExact(maxDistance);
    }

    @Nullable
    @Override
    public Block getTargetBlockExact(int maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
        return player.getTargetBlockExact(maxDistance, fluidCollisionMode);
    }

    @Nullable
    @Override
    public RayTraceResult rayTraceBlocks(double maxDistance) {
        return player.rayTraceBlocks(maxDistance);
    }

    @Nullable
    @Override
    public RayTraceResult rayTraceBlocks(double maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
        return player.rayTraceBlocks(maxDistance, fluidCollisionMode);
    }

    @Override
    public int getRemainingAir() {
        return player.getRemainingAir();
    }

    @Override
    public void setRemainingAir(int ticks) {
        player.setRemainingAir(ticks);
    }

    @Override
    public int getMaximumAir() {
        return player.getMaximumAir();
    }

    @Override
    public void setMaximumAir(int ticks) {
        player.setMaximumAir(ticks);
    }

    @Override
    public int getArrowCooldown() {
        return player.getArrowCooldown();
    }

    @Override
    public void setArrowCooldown(int ticks) {
        player.setArrowCooldown(ticks);
    }

    @Override
    public int getArrowsInBody() {
        return player.getArrowsInBody();
    }

    @Override
    public void setArrowsInBody(int count) {
        player.setArrowsInBody(count);
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return player.getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(int ticks) {
        player.setMaximumNoDamageTicks(ticks);
    }

    @Override
    public double getLastDamage() {
        return player.getLastDamage();
    }

    @Override
    public void setLastDamage(double damage) {
        player.setLastDamage(damage);
    }

    @Override
    public int getNoDamageTicks() {
        return player.getNoDamageTicks();
    }

    @Override
    public void setNoDamageTicks(int ticks) {
        player.setNoDamageTicks(ticks);
    }

    @Nullable
    @Override
    public Player getKiller() {
        return player.getKiller();
    }

    @Override
    public void setKiller(@Nullable Player killer) {
        player.setKiller(killer);
    }

    @Override
    public boolean addPotionEffect(@NotNull PotionEffect effect) {
        return player.addPotionEffect(effect);
    }

    @Override
    @Deprecated
    public boolean addPotionEffect(@NotNull PotionEffect effect, boolean force) {
        return player.addPotionEffect(effect, force);
    }

    @Override
    public boolean addPotionEffects(@NotNull Collection<PotionEffect> effects) {
        return player.addPotionEffects(effects);
    }

    @Override
    public boolean hasPotionEffect(@NotNull PotionEffectType type) {
        return player.hasPotionEffect(type);
    }

    @Nullable
    @Override
    public PotionEffect getPotionEffect(@NotNull PotionEffectType type) {
        return player.getPotionEffect(type);
    }

    @Override
    public void removePotionEffect(@NotNull PotionEffectType type) {
        player.removePotionEffect(type);
    }

    @NotNull
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return player.getActivePotionEffects();
    }

    @Override
    public boolean hasLineOfSight(@NotNull Entity other) {
        return player.hasLineOfSight(other);
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return player.getRemoveWhenFarAway();
    }

    @Override
    public void setRemoveWhenFarAway(boolean remove) {
        player.setRemoveWhenFarAway(remove);
    }

    @Nullable
    @Override
    public EntityEquipment getEquipment() {
        return player.getEquipment();
    }

    @Override
    public void setCanPickupItems(boolean pickup) {
        player.setCanPickupItems(pickup);
    }

    @Override
    public boolean getCanPickupItems() {
        return player.getCanPickupItems();
    }

    @Override
    public boolean isLeashed() {
        return player.isLeashed();
    }

    @NotNull
    @Override
    public Entity getLeashHolder() throws IllegalStateException {
        return player.getLeashHolder();
    }

    @Override
    public boolean setLeashHolder(@Nullable Entity holder) {
        return player.setLeashHolder(holder);
    }

    @Override
    public boolean isGliding() {
        return player.isGliding();
    }

    @Override
    public void setGliding(boolean gliding) {
        player.setGliding(gliding);
    }

    @Override
    public boolean isSwimming() {
        return player.isSwimming();
    }

    @Override
    public void setSwimming(boolean swimming) {
        player.setSwimming(swimming);
    }

    @Override
    public boolean isRiptiding() {
        return player.isRiptiding();
    }

    @Override
    public boolean isSleeping() {
        return player.isSleeping();
    }

    @Override
    public void setAI(boolean ai) {
        player.setAI(ai);
    }

    @Override
    public boolean hasAI() {
        return player.hasAI();
    }

    @Override
    public void attack(@NotNull Entity target) {
        player.attack(target);
    }

    @Override
    public void swingMainHand() {
        player.swingMainHand();
    }

    @Override
    public void swingOffHand() {
        player.swingOffHand();
    }

    @Override
    public void setCollidable(boolean collidable) {
        player.setCollidable(collidable);
    }

    @Override
    public boolean isCollidable() {
        return player.isCollidable();
    }

    @NotNull
    @Override
    public Set<UUID> getCollidableExemptions() {
        return player.getCollidableExemptions();
    }

    @Nullable
    @Override
    public <T> T getMemory(@NotNull MemoryKey<T> memoryKey) {
        return player.getMemory(memoryKey);
    }

    @Override
    public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T memoryValue) {
        player.setMemory(memoryKey, memoryValue);
    }

    @NotNull
    @Override
    public EntityCategory getCategory() {
        return player.getCategory();
    }

    @Override
    public int getArrowsStuck() {
        return player.getArrowsStuck();
    }

    @Override
    public void setArrowsStuck(int arrows) {
        player.setArrowsStuck(arrows);
    }

    @Override
    public int getShieldBlockingDelay() {
        return player.getShieldBlockingDelay();
    }

    @Override
    public void setShieldBlockingDelay(int delay) {
        player.setShieldBlockingDelay(delay);
    }

    @Nullable
    @Override
    public ItemStack getActiveItem() {
        return player.getActiveItem();
    }

    @Override
    public int getItemUseRemainingTime() {
        return player.getItemUseRemainingTime();
    }

    @Override
    public int getHandRaisedTime() {
        return player.getHandRaisedTime();
    }

    @Override
    public boolean isHandRaised() {
        return player.isHandRaised();
    }

    @Override
    public boolean isJumping() {
        return player.isJumping();
    }

    @Override
    public void setJumping(boolean jumping) {
        player.setJumping(jumping);
    }

    @Override
    public void playPickupItemAnimation(@NotNull Item item, int quantity) {
        player.playPickupItemAnimation(item, quantity);
    }

    @Override
    public int getExpToLevel() {
        return player.getExpToLevel();
    }

    @Nullable
    @Override
    public Entity releaseLeftShoulderEntity() {
        return player.releaseLeftShoulderEntity();
    }

    @Nullable
    @Override
    public Entity releaseRightShoulderEntity() {
        return player.releaseRightShoulderEntity();
    }

    @Override
    public float getAttackCooldown() {
        return player.getAttackCooldown();
    }

    @Override
    public boolean discoverRecipe(@NotNull NamespacedKey recipe) {
        return player.discoverRecipe(recipe);
    }

    @Override
    public int discoverRecipes(@NotNull Collection<NamespacedKey> recipes) {
        return player.discoverRecipes(recipes);
    }

    @Override
    public boolean undiscoverRecipe(@NotNull NamespacedKey recipe) {
        return player.undiscoverRecipe(recipe);
    }

    @Override
    public int undiscoverRecipes(@NotNull Collection<NamespacedKey> recipes) {
        return player.undiscoverRecipes(recipes);
    }

    @Override
    public boolean hasDiscoveredRecipe(@NotNull NamespacedKey recipe) {
        return player.hasDiscoveredRecipe(recipe);
    }

    @NotNull
    @Override
    public Set<NamespacedKey> getDiscoveredRecipes() {
        return player.getDiscoveredRecipes();
    }

    @Nullable
    @Override
    @Deprecated
    public Entity getShoulderEntityLeft() {
        return player.getShoulderEntityLeft();
    }

    @Override
    @Deprecated
    public void setShoulderEntityLeft(@Nullable Entity entity) {
        player.setShoulderEntityLeft(entity);
    }

    @Nullable
    @Override
    @Deprecated
    public Entity getShoulderEntityRight() {
        return player.getShoulderEntityRight();
    }

    @Override
    @Deprecated
    public void setShoulderEntityRight(@Nullable Entity entity) {
        player.setShoulderEntityRight(entity);
    }

    @Override
    public void openSign(@NotNull Sign sign) {
        player.openSign(sign);
    }

    @Override
    public boolean dropItem(boolean dropAll) {
        return player.dropItem(dropAll);
    }

    @Nullable
    @Override
    public AttributeInstance getAttribute(@NotNull Attribute attribute) {
        return player.getAttribute(attribute);
    }

    @Override
    public void damage(double amount) {
        player.damage(amount);
    }

    @Override
    public void damage(double amount, @Nullable Entity source) {
        player.damage(amount, source);
    }

    @Override
    public double getHealth() {
        return player.getHealth();
    }

    @Override
    public void setHealth(double health) {
        player.setHealth(health);
    }

    @Override
    public double getAbsorptionAmount() {
        return player.getAbsorptionAmount();
    }

    @Override
    public void setAbsorptionAmount(double amount) {
        player.setAbsorptionAmount(amount);
    }

    @Override
    @Deprecated
    public double getMaxHealth() {
        return player.getMaxHealth();
    }

    @Override
    @Deprecated
    public void setMaxHealth(double health) {
        player.setMaxHealth(health);
    }

    @Override
    @Deprecated
    public void resetMaxHealth() {
        player.resetMaxHealth();
    }

    @Nullable
    @Override
    public String getCustomName() {
        return player.getCustomName();
    }

    @Override
    public void setCustomName(@Nullable String name) {
        player.setCustomName(name);
    }

    @Override
    public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue) {
        player.setMetadata(metadataKey, newMetadataValue);
    }

    @NotNull
    @Override
    public List<MetadataValue> getMetadata(@NotNull String metadataKey) {
        return player.getMetadata(metadataKey);
    }

    @Override
    public boolean hasMetadata(@NotNull String metadataKey) {
        return player.hasMetadata(metadataKey);
    }

    @Override
    public void removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin) {
        player.removeMetadata(metadataKey, owningPlugin);
    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return player.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return player.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(@NotNull String name) {
        return player.hasPermission(name);
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return player.hasPermission(perm);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        return player.addAttachment(plugin, name, value);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return player.addAttachment(plugin);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks) {
        return player.addAttachment(plugin, name, value, ticks);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        return player.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {
        player.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        player.recalculatePermissions();
    }

    @NotNull
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return player.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return player.isOp();
    }

    @Override
    public void setOp(boolean value) {
        player.setOp(value);
    }

    @NotNull
    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return player.getPersistentDataContainer();
    }

    @Override
    public void sendPluginMessage(@NotNull Plugin source, @NotNull String channel, byte[] message) {
        player.sendPluginMessage(source, channel, message);
    }

    @NotNull
    @Override
    public Set<String> getListeningPluginChannels() {
        return player.getListeningPluginChannels();
    }

    @NotNull
    @Override
    public <T extends Projectile> T launchProjectile(@NotNull Class<? extends T> projectile) {
        return player.launchProjectile(projectile);
    }

    @NotNull
    @Override
    public <T extends Projectile> T launchProjectile(@NotNull Class<? extends T> projectile, @Nullable Vector velocity) {
        return player.launchProjectile(projectile, velocity);
    }

    @Override
    public Rank getRank() {
        return rank;
    }
}
