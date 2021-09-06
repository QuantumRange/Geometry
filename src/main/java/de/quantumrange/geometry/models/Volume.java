package de.quantumrange.geometry.models;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.*;
import java.util.function.Function;

import static java.lang.Math.abs;

public class Volume {

	private final Location from, to;
	private Node[][][] nodes;

	public Volume(Location from, Location to) {
		this.from = from;
		this.to = to;
	}

	public void write(String nodeFile, String elementFile) throws IOException {
		BufferedWriter nodeWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nodeFile)));
		BufferedWriter elementWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(elementFile)));

		nodeWriter.write("");

		nodes = new Node
				[(int) (abs(to.getX() - from.getX()) + 1)]
				[(int) (abs(to.getY() - from.getY()) + 1)]
				[(int) (abs(to.getZ() - from.getZ()) + 1)];

		int xFrom = (int) getFrom(Location::getX), xTo = (int) getTo(Location::getX);
		int yFrom = (int) getFrom(Location::getY), yTo = (int) getTo(Location::getY);
		int zFrom = (int) getFrom(Location::getZ), zTo = (int) getTo(Location::getZ);

		int notNullCounter = 0;
		int[][] increments = new int[][] {
				{ 0, 0, 0 },
				{ 1, 0, 0 },
				{ 1, 1, 0 },
				{ 0, 1, 0 },
				{ 0, 0, 1 },
				{ 1, 0, 1 },
				{ 1, 1, 1 },
				{ 0, 1, 1 }
		};

		for (int x = xFrom; x < xTo; x++) {
			for (int y = yFrom; y < yTo; y++) {
				for (int z = zFrom; z < zTo; z++) {
					Block b = from.getWorld().getBlockAt(x, y, z);

					if (b.getType() != Material.AIR) {

						for (int i = 0; i < 8; i++) {
							int localX = x - xFrom + increments[i][0];
							int localY = y - yFrom + increments[i][1];
							int localZ = z - zFrom + increments[i][2];

							if (nodes[localX][localY][localZ] == null) {
								nodes[localX][localY][localZ] = new Node(new Point(localX, localY, localZ), notNullCounter++);
							}
						}

					}
				}
			}
		}

		elementWriter.close();
		nodeWriter.close();
	}

	public boolean inArea(Location loc) {
		if (!from.getWorld().getName().equalsIgnoreCase(loc.getWorld().getName())) return false;

		return  loc.getX() > getFrom(Location::getX) && loc.getX() < getTo(Location::getX) &&
				loc.getY() > getFrom(Location::getY) && loc.getY() < getTo(Location::getY) &&
				loc.getZ() > getFrom(Location::getZ) && loc.getZ() < getTo(Location::getZ);
	}

	private double getFrom(Function<Location, Double> function) {
		if (function.apply(from) < function.apply(to)) return function.apply(from);
		return function.apply(to);
	}
	private double getTo(Function<Location, Double> function) {
		if (function.apply(from) > function.apply(to)) return function.apply(from);
		return function.apply(to);
	}

	public Location getFrom() {
		return from;
	}

	public Location getTo() {
		return to;
	}

	@Override
	public String toString() {
		return "Area{" +
				"from=" + from +
				", to=" + to +
				", nodes=" + nodes +
				'}';
	}
}
