package visuals;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import game.ChaosGame;

/**
 * A graphical centered text box component.
 * @author Mika Thein
 * @see #CenteredTextBox(String, int, int)
 * @see #paint(Graphics2D)
 * @see ChaosGame
 */
public class CenteredTextBox {
	
	/**
	 * The background and foreground colors.
	 */
	public final static Color BACKGROUND = Color.BLACK,
			FOREGROUND = Color.WHITE;
	/**
	 * The padding inside of the box. (Approximate distance from the edge to the text.)
	 */
	public final static int PADDING = 10;
	
	/**
	 * The font used to draw the text inside this text box.
	 */
	public final static Font font = new Font("Arial", Font.PLAIN, 13);
	
	/**
	 * The text of this text box.
	 * @see #lines
	 */
	public final String text;
	/**
	 * The text of this text box separated into lines.
	 * @see #text
	 */
	public final String[] lines;
	
	/**
	 * The center position of this text box.
	 */
	public int centerX, centerY;
	/**
	 * Width and height of this text box.
	 */
	public final int width, height;

	/**
	 * Creates a new centered text box.
	 * @param text The text.
	 * @param centerX The x position of the text box center.
	 * @param centerY The y position of the text box center.
	 */
	public CenteredTextBox(String text, int centerX, int centerY) {
		this.text = text;
		this.centerX = centerX;
		this.centerY = centerY;
		lines = text.split("\n");
		
		String longestLine = "";
		for(String l : lines) {
			if(l.length() > longestLine.length()) longestLine = l;
		}
		
		FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
		width = (int) font.getStringBounds(longestLine, frc).getWidth() + PADDING*2;
		height = (int) font.getSize()*lines.length + PADDING*2;
	}
	
	/**
	 * Paints this component
	 * @param g2 The Graphics2D instance.
	 */
	public void paint(Graphics2D g2) {
		Font tFont = g2.getFont();
		Color tColor = g2.getColor();
		g2.setFont(font);
		
		g2.setColor(BACKGROUND);
		g2.fillRect(centerX-width/2, centerY-height/2, width, height);
		g2.setColor(FOREGROUND);
		g2.drawRect(centerX-width/2, centerY-height/2, width, height);
		
		int i = 1;
		for(String l : lines) {
			g2.drawString(l, centerX-width/2+PADDING, centerY-height/2+(height/(float) lines.length)*(i++) - PADDING/2);
		}
		
		g2.setFont(tFont);
		g2.setColor(tColor);
	}

}
