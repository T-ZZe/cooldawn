package fr.exemple.cooldown;

import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CooldownPlugin extends JavaPlugin implements Listener {

    private static final int PEARL_COOLDOWN_TICKS = 5 * 20; // 5 secondes
    private static final int MACE_COOLDOWN_TICKS = 8 * 20;  // 8 secondes

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    // Perle de l'Ender : cooldown de 5s après le lancer
    @EventHandler
    public void onPearlLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof EnderPearl pearl)) return;
        if (!(pearl.getShooter() instanceof Player player)) return;

        player.setCooldown(Material.ENDER_PEARL, PEARL_COOLDOWN_TICKS);
    }

    // Mace : cooldown de 8s après un coup
    @EventHandler
    public void onMaceHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (player.getInventory().getItemInMainHand().getType() != Material.MACE) return;

        if (player.hasCooldown(Material.MACE)) {
            event.setCancelled(true);
            return;
        }

        player.setCooldown(Material.MACE, MACE_COOLDOWN_TICKS);
    }
}
