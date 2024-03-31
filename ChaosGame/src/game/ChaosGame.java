package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The chaos game.<br>
 * The game consists of nodes and dots.
 * Nodes refer to the objects that can be fixed in advance on the playing area.
 * Their number remains unchanged.
 * The dots are generated continuously during the game and follow the nodes.
 * @author Mika Thein
 * @see #ChaosGame(Node[], int, int, float)
 * @see #play()
 * @see Node
 * @see Dot
 */
public class ChaosGame {
	
	/**
	 * Preferences regarding the color of the dots.
	 * @see Dot
	 * @see Dot#color
	 */
	public static final Color DOT_COLOR = Color.WHITE,
			START_DOT_COLOR = Color.YELLOW,
			LATEST_DOT_COLOR = Color.MAGENTA,
			CONNECTING_LINE_COLOR = Color.GRAY,
			BACKGROUND_COLOR = Color.BLACK;
	
	/**
	 * The divider by which the distance from the last dot to a random node is to be divided.
	 */
	public final float divisor;
	/**
	 * Whether the same node can be selected as a target node in immediate succession.
	 */
	public boolean mayRepeat = true;
	
	/**
	 * The set of nodes.
	 * @see Node
	 * @see #latestNode
	 * @see #occurrences
	 * @see #dots
	 * @see ChaosGame
	 */
	public final Node[] nodes;
	/**
	 * The list of dots.
	 * @see Dot
	 * @see #nodes
	 * @see ChaosGame
	 */
	public final ArrayList<Dot> dots = new ArrayList<>();
	
	/**
	 * Saves the amount of occurrences of each node. (How often it has been picked as a target.)
	 */
	private HashMap<String, Integer> occurrences = new HashMap<>();
	/**
	 * The node that has recently been selected as a target node.
	 */
	public Node latestNode = null;

	/**
	 * Initialises a new game.
	 * @param nodes A list of the nodes. May not be empty or null. Each node has to own an unique name within this array.
	 * @param startX The x position of the first dot.
	 * @param startY The y position of the first dot.
	 * @param divisor The divisor by which the distance from the last dot to a random node is divided.
	 * @see #play()
	 * @see #nodes
	 * @see #dots
	 * @see #divisor
	 * @see ChaosGame
	 */
	public ChaosGame(Node[] nodes, int startX, int startY, float divisor) {
		if(nodes == null || nodes.length < 1) {
			throw new RuntimeException("`Node[] nodes` is required to contain more than 0 elements.");
		} for(Node n : nodes) {
			if(n.name == null || occurrences.containsKey(n.name)) {
				throw new RuntimeException("Each node name must not be null and has to be unique.");
			}
			occurrences.put(n.name, 0);
		}
		if(divisor <= 0) throw new RuntimeException("`divisor` has to be larger than 0.");
		
		this.divisor = divisor;
		this.nodes = nodes;
		
		dots.add(new Dot(startX, startY).setColor(START_DOT_COLOR));		
	}
	
	/**
	 * @param name The name of a node.
	 * @return How often a node has been selected as a target node.
	 * @see #occurrences
	 * @see #nodes
	 * @see Node
	 */
	public int getNodeOccurrence(String name) {
		return occurrences.get(name);
	}
	
	/**
	 * Selects a random node of <code>nodes</code> and adds a new dot to <code>dots</code> at the distance to said node times 1/<code>divisor</code>.
	 * @see #nodes
	 * @see #dots
	 * @see #divisor
	 * @see #mayRepeat
	 */
	public void play() {
		// SELECT TARGET NODE
		int targetIndex = ThreadLocalRandom.current().nextInt(0, nodes.length);
		while(!mayRepeat && nodes[targetIndex].equals(latestNode)) targetIndex = ThreadLocalRandom.current().nextInt(0, nodes.length);
		
		latestNode = nodes[targetIndex];
		occurrences.put(latestNode.name, occurrences.get(latestNode.name)+1);
		
		// SELECT THE DOT MOST RECENTLY ADDED
		Dot latestDot = dots.get(dots.size()-1);
		
		// ADDS THE NEW DOT
		float newX = latestDot.x+(latestNode.x-latestDot.x)/divisor;
		float newY = latestDot.y+(latestNode.y-latestDot.y)/divisor;
		dots.add(new Dot(newX, newY));
	}

}
