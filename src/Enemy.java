package src;

import java.awt.*;


public class Enemy {
    // Enemy Variables
    public int enemyY = 30;
    public int enemyX = 11;
    public boolean enemyDirection = true; // True == Right, False == Left
    public final int ENEMY_SIZE = 30;
    public final Color ENEMY_COLOR = Color.RED;


    interface enemyInterface {
        // Submethods used in updateEnemyShip
        void erasePreviousInstance();
        void redrawEnemy();
    }

    public void updateEnemyShip(Graphics g, Color c) {
        /**
         * Draws the enemy ship first in black (to erase a previous instance),
         * then increments enemyX by 1, and then it draws the ship again in
         * red. This method is called every pass in startGame()
         */

        // Define the Enemy Functions
        enemyInterface ei = new enemyInterface() {
            public void erasePreviousInstance() {
                // Erase the previous instance of the enemy
                Rectangle r = new Rectangle(enemyX, enemyY, ENEMY_SIZE, ENEMY_SIZE);
                g.setColor(Color.BLACK);
                g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
            }

            public void redrawEnemy() {
                // Redraw the enemy at the new position
                Rectangle updatedRec = new Rectangle(enemyX, enemyY, ENEMY_SIZE, ENEMY_SIZE);
                g.setColor(c);
                g.fillRect((int) updatedRec.getX(), (int) updatedRec.getY(), (int) updatedRec.getWidth(),
                              (int) updatedRec.getHeight());
            }
        };

        // Begin the actual enemy logic
        // If the X Position is within the bounds
        if (enemyX <= 260 && enemyX >= 10) {

            ei.erasePreviousInstance();

            // If direction is true, add one to the X Position
            if (enemyDirection == true) {
                enemyX++;
            } else {
                enemyX--;
            }

            // Draw the enemy in its new position
            ei.redrawEnemy();
        }
        // If the ship would move out of bounds if it continued in the same direction
        else {
            // Flip the direction
            enemyDirection = !enemyDirection;

            // Erase it
            ei.erasePreviousInstance();

            // Increment the enemyY value
            enemyY += 40;

            // Make sure it's not on the bounds
            if (enemyDirection == true) {
                enemyX++;
            } else {
                enemyX--;
            }

            // Rerun the function
            updateEnemyShip(g, ENEMY_COLOR);
        }
    }
}
