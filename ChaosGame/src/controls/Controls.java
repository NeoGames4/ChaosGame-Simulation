package controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A class to manage controls.
 * @author Mika Thein
 */
public class Controls implements KeyListener {
	
	/**
	 * Whether a key is currently pressed down.
	 */
	public static boolean W_DOWN = false,		// Movement
			A_DOWN = false,
			S_DOWN = false,
			D_DOWN = false,
			X_DOWN = false,						// Zoom
			Y_DOWN = false,
			SHIFT_DOWN = false,					// Movement and zoom acceleration
			R_DOWN = false,						// Position reset
			ARROW_UP_DOWN = false,				// Game speed						// ALSO MODIFIED FROM Frame.java
			ARROW_DOWN_DOWN = false,												// ALSO MODIFIED FROM Frame.java
			E_DOWN = false,						// Next game step if game			// ALSO MODIFIED FROM Frame.java
			SPACE_DOWN = false,					// Pause							// ALSO MODIFIED FROM Frame.java
			I_DOWN = false,						// Dot size							// ALSO MODIFIED FROM Frame.java
			O_DOWN = false,															// ALSO MODIFIED FROM Frame.java
			F1_DOWN = false,					// Toggle UI						// ALSO MODIFIED FROM Frame.java
			F2_DOWN = false,					// Toggle connecting line			// ALSO MODIFIED FROM Frame.java
			H_DOWN = false;						// Help menu
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				W_DOWN = true;
				break;
			case KeyEvent.VK_A:
				A_DOWN = true;
				break;
			case KeyEvent.VK_S:
				S_DOWN = true;
				break;
			case KeyEvent.VK_D:
				D_DOWN = true;
				break;
			case KeyEvent.VK_X:
				X_DOWN = true;
				break;
			case KeyEvent.VK_Y:
				Y_DOWN = true;
				break;
			case KeyEvent.VK_SHIFT:
				SHIFT_DOWN = true;
				break;
			case KeyEvent.VK_R:
				R_DOWN = true;
				break;
			case KeyEvent.VK_UP:
				ARROW_UP_DOWN = true;
				break;
			case KeyEvent.VK_DOWN:
				ARROW_DOWN_DOWN = true;
				break;
			case KeyEvent.VK_E:
				E_DOWN = true;
				break;
			case KeyEvent.VK_SPACE:
				SPACE_DOWN = true;
				break;
			case KeyEvent.VK_I:
				I_DOWN = true;
				break;
			case KeyEvent.VK_O:
				O_DOWN = true;
				break;
			case KeyEvent.VK_F1:
				F1_DOWN = true;
				break;
			case KeyEvent.VK_F2:
				F2_DOWN = true;
				break;
			case KeyEvent.VK_H:
				H_DOWN = true;
				break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				W_DOWN = false;
				break;
			case KeyEvent.VK_A:
				A_DOWN = false;
				break;
			case KeyEvent.VK_S:
				S_DOWN = false;
				break;
			case KeyEvent.VK_D:
				D_DOWN = false;
				break;
			case KeyEvent.VK_X:
				X_DOWN = false;
				break;
			case KeyEvent.VK_Y:
				Y_DOWN = false;
				break;
			case KeyEvent.VK_SHIFT:
				SHIFT_DOWN = false;
				break;
			case KeyEvent.VK_R:
				R_DOWN = false;
				break;
			case KeyEvent.VK_UP:
				ARROW_UP_DOWN = false;
				break;
			case KeyEvent.VK_DOWN:
				ARROW_DOWN_DOWN = false;
				break;
			case KeyEvent.VK_E:
				E_DOWN = false;
				break;
			case KeyEvent.VK_SPACE:
				SPACE_DOWN = false;
				break;
			case KeyEvent.VK_I:
				I_DOWN = false;
				break;
			case KeyEvent.VK_O:
				O_DOWN = false;
				break;
			case KeyEvent.VK_F1:
				F1_DOWN = false;
				break;
			case KeyEvent.VK_F2:
				F2_DOWN = false;
				break;
			case KeyEvent.VK_H:
				H_DOWN = false;
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
