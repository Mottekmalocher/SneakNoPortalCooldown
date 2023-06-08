package de.mottekmalocher.sneaknoportalcooldown;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Abilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        super.onEnable();

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        final Player p = event.getPlayer();
        if(p.getGameMode() != GameMode.CREATIVE && p.getLocation().getBlock().getType() == Material.NETHER_PORTAL) {
            setInvulnerable(p, true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> setInvulnerable(p, false),3);
        }
    }

    public void setInvulnerable(Player player, boolean bool) {
        ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        Abilities playerAbilities = entityPlayer.getAbilities();
        playerAbilities.invulnerable = bool;
    }

}
