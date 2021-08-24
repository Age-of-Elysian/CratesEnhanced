package me.linoxgh.cratesenhanced.data.rewards;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemReward implements Reward<ItemStack> {
    private ItemStack reward;
    private int weight;

    public ItemReward(@NotNull ItemStack reward, int weight) {
        this.reward = reward;
        this.weight = weight;
    }

    public ItemReward(@NotNull Map<String, Object> data) {
        this.reward = (ItemStack) data.get("reward");
        this.weight = (int) data.get("weight");
    }

    @Override
    public int getWeight() {
        return weight;
    }
    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean giveReward(@NotNull Player p, @NotNull Location crateLocation) {
        Location topLoc = crateLocation.set(crateLocation.getX(), crateLocation.getY() + 1D, crateLocation.getZ()).toCenterLocation();
        crateLocation.getWorld().spawnEntity(
                topLoc,
                EntityType.DROPPED_ITEM,
                CreatureSpawnEvent.SpawnReason.CUSTOM,
                (entity) -> {
                    Item item = (Item) entity;
                    item.setOwner(p.getUniqueId());
                    item.setCanMobPickup(false);
                    item.setCanPlayerPickup(true);
                    item.setWillAge(true);
                    item.setPickupDelay(20);
                    item.setItemStack(reward);
                }
        );
        return true;
    }

    @Override
    public @NotNull ItemStack getReward() {
        return reward.clone();
    }

    @Override
    public void setReward(@NotNull ItemStack reward) {
        this.reward = reward.clone();
    }

    @Override
    public @NotNull RewardType getRewardType() {
        return RewardType.ITEM;
    }
}