package src;

import java.awt.*;


public class PlayerMissile {
    // Player Missile Variables
    public int patrolMissileX;
    public int patrolMissileY = 0;
    public final int PATROL_MISSILE_LENGTH = 10;


    interface missileInterface {
        void erasePreviousInstance();
        void redrawMissile();
    }

    public void updatePatrolMissile(Graphics g) {
        /**
         * Moves the missile and draws it similar to moveEnemyShipAndDraw: •Do not draw
         * anything if patrolMissileY is 0. •Otherwise, the missile as a vertical line
         * with the stated length and x-position. •PatrolMissileY is the top of the
         * line. •Draw the missile in white, move the missile up 5 pixels, and draw it
         * again in black. •If the patrolMissileY is 0 or negative, do not draw the
         * missile in black, but set patrolMissileY to 0. Call this method before the
         * sleep in the loop. When the up arrow key is pushed and patrolMissileY is 0,
         * set patrolMissileX to be the center of the patrol ship and set the top of the
         * missile so that its bottom is one pixel above the top of the patrol ship
         * (PATROL_Y). You should only be able to fire one missile at a time and the up
         * arrow should not do anything if a missile is still displayed.
         */
        // Missile Functions
        missileInterface mi = new missileInterface() {
            public void erasePreviousInstance() {
                // Erase the missile first
                g.setColor(Color.BLACK);
                g.drawLine(patrolMissileX, patrolMissileY, patrolMissileX, patrolMissileY + PATROL_MISSILE_LENGTH);
            }

            public void redrawMissile() {
                // Draw it in black again
                g.setColor(Color.WHITE);
                g.drawLine(patrolMissileX, patrolMissileY, patrolMissileX, patrolMissileY + PATROL_MISSILE_LENGTH);
            }
        };

        // First and foremost, erase the current missile
        mi.erasePreviousInstance();
        // Missile Logic
        // If the missile is still visible on the screen
        if (patrolMissileY > 5) {
            patrolMissileY -= 5;
            mi.redrawMissile();
        }
        // If patrolMissileY is 0 or less than (off the screen) set it back to 0
        else {
            mi.erasePreviousInstance();
            patrolMissileY = 0;
            mi.erasePreviousInstance();
        }
    }
}
