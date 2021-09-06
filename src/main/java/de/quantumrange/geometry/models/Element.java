package de.quantumrange.geometry.models;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.Math.abs;

public class Element {

	private final int[] indices;
	private final Material mat;

	public Element(Volume area, Block block) {
		this.indices = new Point[8];
		this.mat = block.getType();

		Location temp = block.getLocation().clone().subtract(area.getFrom());
		Location pos = new Location(temp.getWorld(), abs(temp.getX()), abs(temp.getY()), abs(temp.getZ()));

//		this.indices[0] = new Point(pos.getX(), pos.getY(), pos.getZ());
//		this.indices[1] = new Point(pos.getX() + 1d, pos.getY(), pos.getZ());
//		this.indices[2] = new Point(pos.getX() + 1d, pos.getY(), pos.getZ() + 1d);
//		this.indices[3] = new Point(pos.getX(), pos.getY(), pos.getZ() + 1d);
//
//		this.indices[4] = new Point(pos.getX(), pos.getY() + 1d, pos.getZ());
//		this.indices[5] = new Point(pos.getX() + 1d, pos.getY() + 1d, pos.getZ());
//		this.indices[6] = new Point(pos.getX() + 1d, pos.getY() + 1d, pos.getZ() + 1d);
//		this.indices[7] = new Point(pos.getX(), pos.getY() + 1d, pos.getZ() + 1d);
	}

	public Point[] getIndices() {
		return indices;
	}

	public Material getMat() {
		return mat;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Element node = (Element) o;
		return Arrays.equals(getIndices(), node.getIndices()) && getMat() == node.getMat();
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(getMat());
		result = 31 * result + Arrays.hashCode(getIndices());
		return result;
	}

	@Override
	public String toString() {
		return "Node{" +
				"indices=" + Arrays.toString(indices) +
				", material=" + mat +
				'}';
	}
}
