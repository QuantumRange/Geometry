package de.quantumrange.geometry.listeners;

import de.quantumrange.geometry.Geometry;
import de.quantumrange.geometry.models.Volume;
import de.quantumrange.geometry.models.Element;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	@EventHandler
	public void onBlock(BlockPlaceEvent e) {
		for (Volume area : Geometry.areas) {
			if (area.inArea(e.getBlock().getLocation())) {
				area.getNodes().add(new Element(area, e.getBlock()));
			}

			System.out.println(area.getNodeString());
 		}
	}

}
