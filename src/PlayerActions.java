package src;

import java.awt.*;
import java.awt.event.*;


public class PlayerActions {
    // Input Variables
    // The KeyCodes were being a bit wonky, so I switched to the KeyEvent ids
    public static final int RIGHT_ARROW = KeyEvent.VK_RIGHT;
    public static final int LEFT_ARROW = KeyEvent.VK_LEFT;
    public static final int UP_ARROW = KeyEvent.VK_UP;


    public static void inputListener(DrawingPanel panel, Graphics g, Player p, PlayerMissile pm) {
        /**
         * This function listens for player movement and action keys. It's called every
         */

        int keyCode = panel.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                // If the missile is not currently on the screen...
                if ( pm.patrolMissileY == 0) {
                     pm.patrolMissileX =  p.patrolX + 9;
                    // This will trigger the missile to begin the firing logic in the main for loop
                     pm.patrolMissileY = Main.PANEL_SIZE - ( pm.PATROL_MISSILE_LENGTH * 2) - ( p.PATROL_SIZE * 2);
                }
                break;
                
            case KeyEvent.VK_RIGHT:
                // If adding 3 to patrolX won't take it out of bounds...
                if ( p.patrolX < 270) {
                     p.erasePatrolShip(g);
                     p.patrolX += 3;
                     p.drawPatrolShip(g,  p.PATROL_COLOR);
                }
                break;

            case KeyEvent.VK_LEFT:
                // If subtracting 3 from patrolX won't take it out of bounds...
                if ( p.patrolX > 7) {
                     p.erasePatrolShip(g);
                     p.patrolX -= 3;
                     p.drawPatrolShip(g,  p.PATROL_COLOR);
                }
                break;
        }
    }
}
