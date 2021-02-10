package de.mottekmalocher.sneaknoportalcooldown;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PlayerAbilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

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
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    setInvulnerable(p, false);
                }
            },3);
        }
    }

    public void setInvulnerable(Player player, boolean bool) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        try {
            PlayerAbilities playerAbilities = entityPlayer.abilities;
            Field field = playerAbilities.getClass().getDeclaredField("isInvulnerable");

            field.setAccessible(true);
            field.setBoolean(playerAbilities, bool);
            field.setAccessible(false);

            entityPlayer.updateAbilities();
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            System.err.println(e);
        }
    }

}
