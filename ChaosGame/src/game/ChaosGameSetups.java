package game;

import java.awt.Color;

/**
 * This class contains a couple of basic setups for the Chaos Game.
 * @author Mika Thein
 * @see #getEqualateralTriangle(int)
 * @see #getRightAngledTriangle(int)
 * @see #getRectangle(int)
 * @see #getCircle(int, float)
 * @see ChaosGame
 */
public class ChaosGameSetups {
	
	/**
	 * @param height The height in pixels.
	 * @return Nodes whose connecting lines correspond to an equilateral triangle (with equal side lengths).
	 */
	public final static Node[] getEqualateralTriangle(int height) {
		int centerDistance = (int) (height / (1 + Math.sin(Math.toRadians(30))));
		int yOffset = centerDistance - height/2;
		return new Node[] {
			new Node("A", new Color(100, 100, 255), 0, -centerDistance+yOffset),
			new Node("B", new Color(235, 100, 255), (float) (-Math.cos(Math.toRadians(30))*centerDistance), (float) (Math.sin(Math.toRadians(30))*centerDistance)+yOffset),
			new Node("C", new Color(100, 235, 255), (float) (Math.cos(Math.toRadians(30))*centerDistance), (float) (Math.sin(Math.toRadians(30))*centerDistance)+yOffset)
		};
	}
	
	/**
	 * @param height The height in pixels.
	 * @return Nodes whose connecting lines correspond to a right-angled triangle.
	 */
	public final static Node[] getRightAngledTriangle(int height) {
		int centerDistance = (int) (height / (1 + Math.sin(Math.toRadians(30))));
		int yOffset = centerDistance - height/2;
		return new Node[] {
			new Node("A", new Color(100, 100, 255), (float) (Math.cos(Math.toRadians(30))*centerDistance), -centerDistance+yOffset),
			new Node("B", new Color(235, 100, 255), (float) (-Math.cos(Math.toRadians(30))*centerDistance), (float) (Math.sin(Math.toRadians(30))*centerDistance)+yOffset),
			new Node("C", new Color(100, 235, 255), (float) (Math.cos(Math.toRadians(30))*centerDistance), (float) (Math.sin(Math.toRadians(30))*centerDistance)+yOffset)
		};
	}
	
	/**
	 * @param height The height in pixels.
	 * @return Nodes whose connecting lines correspond to a square.
	 */
	public final static Node[] getRectangle(int width, int height) {
		return new Node[] {
			new Node("A", new Color(0, 255, 255),	-width/2, -height/2),
			new Node("B", new Color(100, 255, 200),	-width/2, +height/2),
			new Node("C", new Color(200, 255, 100),	+width/2, +height/2),
			new Node("D", new Color(255, 255, 0),	+width/2, -height/2)
		};
	}
	
	/**
	 * For three nodes the figure corresponds to an equilateral triangle, for four nodes to a square, for five nodes to an equilateral pentagon, ….
	 * @param nodeAmount The amount of nodes that should be placed on a circle.
	 * @param diameter The diameter of the circle.
	 * @return Nodes that lie on a circle.
	 */
	public final static Node[] getCircle(int nodeAmount, float diameter) {
		if(nodeAmount < 1) throw new RuntimeException("`nodes` has to be larger than 0.");
		Node[] nodes = new Node[nodeAmount];
		
		for(int i = 0; i<nodeAmount; i++) {
			float sine = (float) Math.sin(Math.toRadians((360f/nodeAmount)*i));
			float cosine = (float) Math.cos(Math.toRadians((360f/nodeAmount)*i));
			float mid = (float) Math.sin(Math.toRadians((360f/nodeAmount)*i+120));
			nodes[i] = new Node(getLetter(i), new Color((int) (sine*100+150), (int) (mid*100+150), (int) (cosine*100+150)), sine * (diameter/2f), -cosine * (diameter/2f));
		}
		
		return nodes;
	}
	
	/**
	 * @param i The place of a node.
	 * @return An equivalent place in the alphabet. If <code>i</code> is greater than the alphabet, additional characters are included.
	 */
	private final static String getLetter(int i) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$&#@+*!?<=>";
		if(i > chars.length() || i < 0) throw new RuntimeException("Cannot generate a letter for " + i + " nodes.");
		return chars.charAt(i) + "";
	}

}
