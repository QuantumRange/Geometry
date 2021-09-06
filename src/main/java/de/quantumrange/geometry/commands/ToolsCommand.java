package de.quantumrange.geometry.commands;

import de.quantumrange.geometry.Geometry;
import de.quantumrange.geometry.models.Volume;
import de.quantumrange.geometry.util.Particle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class ToolsCommand implements CommandExecutor, Listener, Runnable {

	public static final HashMap<UUID, Location> TOOL_HOLDER = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			p.setAllowFlight(true);
			p.setFlying(true);

			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 255, false, false));
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f);

			p.sendMessage(" \n".repeat(100));

			ItemStack stack = new ItemStack(Material.STICK);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("§l§7Selector");
			meta.setLore(Arrays.stream("""
					§7When you break a block with the stick it becomes the first position.
					§7
					§7With 2 Positions the program calculates a area from the first point to the second point.
					§7This area will be transformed to a 3D Mash kind of thing.
					§7
					§a§lIt is as simple as hitting the first position block.
					""".split("\n")).toList());
			meta.setUnbreakable(true);
			stack.setItemMeta(meta);

			p.getInventory().clear();
			p.getInventory().setItem(8, stack);

			TOOL_HOLDER.remove(p.getUniqueId());
			TOOL_HOLDER.put(p.getUniqueId(), null);
			return true;
		} else return false;
	}

	@EventHandler
	public void onInventory(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (TOOL_HOLDER.containsKey(uuid)) {
				if ((e.getItem() == null ? Material.AIR : e.getItem().getType()) == Material.STICK) {
					if (TOOL_HOLDER.get(uuid) != null) {
						p.sendMessage("§eSecond Position for the Area is selected!\n§4Generating...");
						Particle.drawBox(TOOL_HOLDER.get(uuid), e.getClickedBlock().getLocation(), p);

						Geometry.areas.add(new Volume(TOOL_HOLDER.get(uuid), e.getClickedBlock().getLocation()));
						TOOL_HOLDER.remove(p.getUniqueId());

						for (int i = 0; i < p.getInventory().getSize(); i++) {
							ItemStack item = p.getInventory().getItem(i);
							if (item != null && item.getItemMeta() != null) {
								if (item.getItemMeta().getDisplayName().contains("Selector") && item.getType() == Material.STICK) {
									p.getInventory().setItem(i, new ItemStack(Material.AIR));
								}
							}
						}
					} else {
						p.sendMessage("§eFirst Position for the Area is selected!");
						TOOL_HOLDER.put(uuid, p.getLocation());
					}

					e.setCancelled(true);
				}
			}
		}
	}

	@Override
	public void run() {
		for (Volume area : Geometry.areas) {
			for (Player p : Geometry.getPlugin().getServer().getOnlinePlayers()) {
				Particle.drawBox(area.getFrom(), area.getTo(), p);
			}
		}
	}
}
