package visuals;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * A graphical bar chart component for visualising statistics about the Chaos Game.
 * @author Mika Thein
 * @see #BarChart(int, int, int, int, NameFloatPair[], float)
 * @see #paint(Graphics2D)
 * @see ChaosGame
 */
public class BarChart {
	
	/**
	 * The foreground color.
	 */
	public static final Color color = Color.WHITE;
	
	/**
	 * The position and size of the bar chart component.
	 */
	public final int x, y, width, height;
	
	/**
	 * The data magnitude that should be considered maximum.
	 * The value does not have to correspond to the actual maximum.
	 * If a data point reaches the <code>max</code>, the bar height reaches the height of the diagram (without padding).
	 * @see #data
	 */
	public final float max;
	/**
	 * The data. Each data point consists of a name, a magnitude and a color.
	 * @see NameFloatPair
	 * @see #max
	 */
	public final NameFloatPair[] data;

	/**
	 * Creates a new bar chart.
	 * @param x The x position.
	 * @param y The y position.
	 * @param width The width in pixels.
	 * @param height The height in pixels.
	 * @param data The data points.
	 * @param max The data value that should be considered maximum.
	 * @see #data
	 * @see #max
	 */
	public BarChart(int x, int y, int width, int height, NameFloatPair[] data, float max) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.data = data;
		this.max = max;
	}
	
	/**
	 * Paints this component
	 * @param g2 The Graphics2D instance.
	 */
	public void paint(Graphics2D g2) {
		
		int barWidth = width/data.length;
		int eHeight = height-g2.getFontMetrics().getHeight();
		
		int i = 0;
		for(NameFloatPair d : data) {
			int barHeight = (int) ((d.magnitude/max) * eHeight);
			if(d.color != null) {
				g2.setColor(d.color);
				g2.fillRect(x+barWidth*i, y+eHeight-barHeight, barWidth, barHeight);
			}
			g2.setColor(color);
			g2.drawRect(x+barWidth*i, y+eHeight-barHeight, barWidth, barHeight);
			g2.drawString(d.name, x+barWidth*i+barWidth/2-g2.getFontMetrics().stringWidth(d.name)/2, y+height);
			i++;
		}
	}
	
	/**
	 * A class storing a name, a magnitude and a color.
	 * @author Mika Thein
	 * @see #name
	 * @see #magnitude
	 * @see #color
	 */
	public static class NameFloatPair {
		
		public final String name;
		public final float magnitude;
		public final Color color;
		
		public NameFloatPair(String name, float magnitude, Color color) {
			this.name = name;
			this.magnitude = magnitude;
			this.color = color;
		}
		
	}

}
