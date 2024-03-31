package game;

import java.awt.Color;

/**
 * Nodes are determined in advance before the Chaos Game starts.
 * @author Mika Thein
 * @see #Node(String, Color, float, float)
 * @see ChaosGame
 * @see Dot
 */
public class Node {
	
	/**
	 * The position of the node.
	 */
	public float x, y;
	
	/**
	 * The name of the node. Each node has to own a unique name within a game.
	 */
	public String name;
	/**
	 * The color in which the node is going painted.
	 */
	public Color color;

	/**
	 * Creates a new node.
	 * @param name The name of the node. Each node has to own a unique name within a game.
	 * @param color The color of the node.
	 * @param x The x position of the node.
	 * @param y The y position of the node.
	 */
	public Node(String name, Color color, float x, float y) {
		this.name = name;
		this.color = color;
		this.x = x;
		this.y = y;
	}

}
