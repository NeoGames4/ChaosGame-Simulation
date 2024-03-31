package visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.ChaosGame;
import game.ChaosGameSetups;
import game.Dot;
import game.Node;
import visuals.BarChart.NameFloatPair;

/**
 * Paints the visuals of the game.
 * @author Mika Thein
 */
public class Frame extends JFrame {

	private static final long serialVersionUID = 1l;
	
	/**
	 * The frames per second.
	 * @see #sps sps – Game steps per frame.
	 */
	public static final int FPS = 30;
	/**
	 * The game steps per frame. To get the number of game steps per second, multiply FPS and SPS.
	 * @see #FPS FPS – Frames per second.
	 */
	public 				int sps = 6;
	
	/**
	 * Whether the auto mode of the game is currently paused. In auto mode, the game steps are performed FPS times SPS per second.
	 * @see #FPS
	 * @see #sps
	 */
	private boolean paused = true;			// Whether the game is paused
	
	/**
	 * The game state.
	 */
	private ChaosGame game;
	/**
	 * The panel in which the painting is performed.
	 * @see Stage
	 */
	private Stage stage;

	/**
	 * Creates a new window with a standard game.
	 * 
	 * To vary the starting point, setups can be obtained from the ChaosGameSetups class or your own arrangements can be written.
	 * See source code of this constructor to gain more information.
	 */
	public Frame() {
		super();
		this.setTitle("The Chaos Game");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width*4/5, screenSize.height*4/5);
		stage = new Stage(getSize());
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		this.setVisible(true);
		this.setContentPane(stage);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// GAME CREATION
		game = new ChaosGame(ChaosGameSetups.getEqualateralTriangle(400), 100, 100, 2f);
		// GAME RULES
		game.mayRepeat = true;
		
		// RUN
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
			if(this.isVisible()) {
				if(!paused) for(int i = 0; i<sps; i++) game.play();
				repaint();
			}
		}, 0, 1000/FPS, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * The panel in which the painting is performed. Stores information about visuals.
	 * @author Mika Thein
	 */
	public class Stage extends JPanel {
		
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new stage.
		 * @param size the screen size of the game in pixels.
		 * @see Stage
		 */
		public Stage(Dimension size) {
			setLocation(0, 0);
			setSize(size);
			setLayout(null);
			setVisible(true);
			
			setFocusable(true);
			setFocusTraversalKeysEnabled(true);
			requestFocusInWindow();
			
			requestFocus();

			addKeyListener(new Controls());
		}
		
		public final int NODE_SIZE = 6, CHART_WIDTH = 200, CHART_HEIGHT = 150, MARGIN = 15;
		private int dotSize = 2;
		
		/**
		 * The last time {@code paintComponent(Graphics)} has been called in epoch milliseconds.
		 * @see #lastFpsUpdate
		 */
		private long lastUpdate = 0;
		
		/**
		 * Current frames per second rate, calculated in {@code paintComponent(Graphics)}.
		 * @see #lastFpsUpdate
		 */
		private int currentFps = 0;
		/**
		 * The last time {@code currentFps} has been updated.<br>
		 * <b>Caution.</b> The FPS number is not calculated for every frame in order to avoid rapid fluctuations, which is why {@code lastFpsUpdate} may differ from {@code lastUpdate}.
		 * @see #currentFps
		 * @see #lastUpdate
		 */
		private long lastFpsUpdate = 0;
		
		/**
		 * The offset of the viewer relative to the window center.
		 */
		private float viewOffsetX = 0, viewOffsetY = 0;
		/**
		 * The zoom factor is multiplied with dot positions to achieve zooming.
		 */
		private float zoomFactor = 1;
		/**
		 * The velocity in pixels per second at which the observer can vary {@code viewOffsetX} or {@code viewOffsetY}.
		 * @see #viewOffsetX
		 * @see #viewOffsetY
		 * @see #zoomVelocity
		 */
		private final float movementVelocity = 70;				// Pixel per second
		/**
		 * The velocity in "zoom factor" per second at which the observer can vary {@code zoomFactor}.
		 * @see #zoomFactor
		 * @see #movementVelocity
		 */
		private final float zoomVelocity = 0.07f;				// Per second
		
		/**
		 * The help menu.
		 * @see CenteredTextBox
		 */
		private final CenteredTextBox helpMenu = new CenteredTextBox(
				"Chaos Game Java Simulation – © 2024 Mika Thein.\n"
				+ "Controls.\n"
				+ "W/A/S/D		– Move\n"
				+ "X/Y			– Zoom in/out\n"
				+ "Shift		– Hold to accelerate navigation"
				+ "R			– Reset position and zoom\n"
				+ "Space		– Pause or continue\n"
				+ "E			– Continue one step\n"
				+ "Arrow Up		– Increase steps per frame\n"
				+ "Arrow Down	– Decrease steps per frame\n"
				+ "I/O			– Decrease/increase dot size\n"
				+ "H			– Hold to show help menu",
				getWidth()/2, getHeight()/2);
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			int offsetX = getWidth()/2 + (int) viewOffsetX;
			int offsetY = getHeight()/2 + (int) viewOffsetY;
			
			// MOVEMENT
			if(Controls.W_DOWN) viewOffsetY += (movementVelocity/FPS) * (Controls.SHIFT_DOWN ? 3 : 1);
			if(Controls.S_DOWN) viewOffsetY -= (movementVelocity/FPS) * (Controls.SHIFT_DOWN ? 3 : 1);
			if(Controls.A_DOWN) viewOffsetX += (movementVelocity/FPS) * (Controls.SHIFT_DOWN ? 3 : 1);
			if(Controls.D_DOWN) viewOffsetX -= (movementVelocity/FPS) * (Controls.SHIFT_DOWN ? 3 : 1);
			
			// ZOOM
			if(Controls.X_DOWN) zoomFactor += (zoomVelocity*zoomFactor/FPS) * (Controls.SHIFT_DOWN ? 4 : 1);
			if(Controls.Y_DOWN) zoomFactor -= (zoomVelocity*zoomFactor/FPS) * (Controls.SHIFT_DOWN ? 4 : 1);
			if(zoomFactor < 0.1f) zoomFactor = 0.1f;
			
			// POSITION RESET
			if(Controls.R_DOWN) {
				viewOffsetX = viewOffsetY = 0;
				zoomFactor = 1;
			}
			
			// GAME SPEED
			if(Controls.ARROW_UP_DOWN) {
				sps++;
				Controls.ARROW_UP_DOWN = false;
			}
			if(Controls.ARROW_DOWN_DOWN) {
				sps--;
				Controls.ARROW_DOWN_DOWN = false;
			}
			if(sps < 0) sps = 0;
			
			// PAUSE
			if(Controls.SPACE_DOWN) {
				paused = !paused;
				Controls.SPACE_DOWN = false;
			}
			
			// NEXT STEP
			if(Controls.E_DOWN) {
				game.play();
				Controls.E_DOWN = false;
			}
			
			// DOT SIZE
			if(Controls.O_DOWN) {
				dotSize++;
				Controls.O_DOWN = false;
			}
			if(Controls.I_DOWN) {
				dotSize--;
				Controls.I_DOWN = false;
			}
			if(dotSize < 1) dotSize = 1;
			
			// BACKGROUND
			g2.setColor(ChaosGame.BACKGROUND_COLOR);
			g2.fillRect(0, 0, getWidth(), getHeight());
			
			// NODES
			for(Node n : game.nodes) {
				g2.setColor(n.color);
				
				g2.fillOval((int) ((n.x-NODE_SIZE/2)*zoomFactor) + offsetX, (int) ((n.y-NODE_SIZE/2)*zoomFactor) + offsetY, (int) (NODE_SIZE*zoomFactor), (int) (NODE_SIZE*zoomFactor));
			}
			
			// DOTS
			ArrayList<Dot> dots = new ArrayList<>();
			dots.addAll(game.dots);
			for(Dot d : dots) {
				if(d.color != null) g2.setColor(d.color);
				else if(game.dots.get(game.dots.size()-1).equals(d)) g2.setColor(ChaosGame.LATEST_DOT_COLOR);
				else g2.setColor(ChaosGame.DOT_COLOR);
				
				g2.fillOval((int) ((d.x-dotSize/2)*zoomFactor) + offsetX, (int) ((d.y-dotSize/2)*zoomFactor) + offsetY, dotSize, dotSize);
			}
			
			// FPS
			long deltaT = System.currentTimeMillis()-lastUpdate;
			lastUpdate += deltaT;
			if(System.currentTimeMillis()-lastFpsUpdate > 500l) {
				currentFps = (int) (1/(deltaT/1000f));
				lastFpsUpdate = System.currentTimeMillis();
			}
			
			// OVERLAY
			g2.setColor(Color.WHITE);
			// TOP LEFT GENERAL INFO PANEL
			int textYOffset = 0, textHeight = g2.getFontMetrics().getHeight()+5;
			g2.drawString("Nodes: " + game.nodes.length, MARGIN, MARGIN+5+((textYOffset++)*textHeight));
			g2.drawString("Dots: " + game.dots.size(), MARGIN, MARGIN+5+((textYOffset++)*textHeight));
			g2.drawString("FPS: " + currentFps + " of " + FPS, MARGIN, MARGIN+5+((textYOffset++)*textHeight));
			g2.drawString("SPS: " + sps + ", Dots Per Second: " + (paused ? "0" : (currentFps*sps)), MARGIN, MARGIN+5+((textYOffset++)*textHeight));
			g2.drawString("Dot size: " + dotSize, MARGIN, MARGIN+5+((textYOffset++)*textHeight));
			
			// TOP CENTER LAST NODE AND PAUSED INFO PANEL
			String latestNodeText = "Latest node: " + (game.latestNode != null ? game.latestNode.name : "/");
			g2.drawString(latestNodeText, getWidth()/2 - g2.getFontMetrics().stringWidth(latestNodeText)/2, MARGIN+textHeight);
			if(paused) {
				g2.setColor(new Color(255, 100, 100));
				g2.drawString("Paused", getWidth()/2-g2.getFontMetrics().stringWidth("Paused")/2, MARGIN+textHeight*3);
			}
			
			// TOP RIGHT NODE OCCURRENCE CHART
			if(game.dots.size() > 1) {
				NameFloatPair[] nodeData = new NameFloatPair[game.nodes.length];
				for(int i = 0; i<nodeData.length; i++) {
					String name = game.nodes[i].name;
					nodeData[i] = new NameFloatPair(name, game.getNodeOccurrence(name), game.nodes[i].color);
				}
				BarChart chart = new BarChart(getWidth()-CHART_WIDTH-MARGIN, MARGIN, CHART_WIDTH, CHART_HEIGHT, nodeData, game.dots.size()-1);
				chart.paint(g2);
			}
			
			// BOTTOM CENTER MOVEMENT INFO PANEL
			g2.setFont(new Font("Monospaced", Font.BOLD, 13));
			g2.setColor(Color.GRAY);
			String positionText = "x: " + viewOffsetX + ", y: " + viewOffsetY;
			String zoomText = "Zoom: " + zoomFactor;
			String helpText = "(Press and hold 'H' to show the help menu.)";
			textYOffset = 1; textHeight = g2.getFontMetrics().getHeight();
			g2.drawString(helpText, getWidth()/2-g2.getFontMetrics().stringWidth(helpText)/2, getHeight()-MARGIN*2-((textYOffset++)*textHeight));
			g2.drawString(zoomText, getWidth()/2-g2.getFontMetrics().stringWidth(zoomText)/2, getHeight()-MARGIN*2-((textYOffset++)*textHeight));
			g2.drawString(positionText, getWidth()/2-g2.getFontMetrics().stringWidth(positionText)/2, getHeight()-MARGIN*2-((textYOffset++)*textHeight));
			
			// HELP INFO PANEL
			if(Controls.H_DOWN) {
				helpMenu.centerX = getWidth()-MARGIN-helpMenu.width/2;
				helpMenu.centerY = getHeight()-MARGIN*2-helpMenu.height/2;
				helpMenu.paint(g2);
			}
		}
		
	}

}
