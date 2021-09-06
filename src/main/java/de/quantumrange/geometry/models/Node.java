package de.quantumrange.geometry.models;

import java.util.Objects;

public class Node {

	private Point coords;
	private int id;

	public Node(Point coords, int id) {
		this.coords = coords;
		this.id = id;
	}

	public Point getCoords() {
		return coords;
	}

	public void setCoords(Point coords) {
		this.coords = coords;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
