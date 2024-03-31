package game;

import java.awt.Color;

/**
 * Dots are generated while the Chaos Game is played.
 * @author Mika Thein
 * @see #Dot(float, float)
 * @see ChaosGame
 * @see Node
 */
public class Dot {
	
	/**
	 * The position of the dot.
	 */
	public final float x, y;
	/**
	 * The color in which the dot is going to be drawn.
	 * Can be set by <code>setColor(Color)</code> and may be <code>null</code>.
	 * In this case the color is set to <code>DOT_COLOR</code>.<br>
	 * The color is overwritten if the dot was the starting dot or if it has recently been added.
	 * @see #setColor(Color)
	 * @see ChaosGame#DOT_COLOR
	 * @see ChaosGame#LATEST_DOT_COLOR
	 * @see ChaosGame#START_DOT_COLOR
	 */
	public Color color = null;

	/**
	 * Creates a new dot.
	 * @param x The x position.
	 * @param y The y position.
	 */
	public Dot(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Overwrites <code>color</color> of a Dot.
	 * @param c The color.
	 * @return The dot itself.
	 * @see #color
	 */
	public Dot setColor(Color c) {
		color = c;
		return this;
	}

}
