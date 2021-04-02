package src;

import java.awt.*;
import java.awt.event.*;


public class SpaceInvaders {
    // Window Class Variables
    public static final int PANEL_SIZE = 300; // Constant panel size var, width and height
    public static DrawingPanel p = new DrawingPanel(PANEL_SIZE, PANEL_SIZE); // Init Panel with proper dimensions
    public static Graphics g = p.getGraphics(); // Init graphics instance
    // public static JLabel label = new JLabel("TEST THIS IS A TEST");
    // Player Variables
    public static final int PATROL_Y = 250;
    public static int patrolX = 270;
    public static final int PATROL_SIZE = 20;
    public static final Color PATROL_COLOR = Color.GREEN;
    // Enemy Variables
    public static int enemyY = 30;
    public static int enemyX = 11;
    public static boolean enemyDirection = true; // True == Right, False == Left
    public static final int ENEMY_SIZE = 30;
    public static final Color ENEMY_COLOR = Color.RED;
    // Input Variables
    // The KeyCodes were being a bit wonky, so I switched to the KeyEvent ids
    public static final int RIGHT_ARROW = KeyEvent.VK_RIGHT;
    public static final int LEFT_ARROW = KeyEvent.VK_LEFT;
    public static final int UP_ARROW = KeyEvent.VK_UP;
    // Player Missile Variables
    public static int patrolMissileX;
    public static int patrolMissileY = 0;
    public static final int PATROL_MISSILE_LENGTH = 10;

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        /**
         * Initiate all game objects and start the action
         *
         * Also houses the main game loop *
         */

        // Set the background to black
        g.fillRect(0, 0, 300, 300);
        g.setColor(Color.BLACK);

        // Init the player
        drawPatrolShip(g, PATROL_COLOR);

        for (int time = 0; time <= 3000; time++) {
            // If the missile is in the fired state (anything greater than 0)...
            if (patrolMissileY > 0) {
                // Update the missile
                updatePatrolMissile(g);
            }

            if (hitDetection()) {
                replayPrompt();
                break;
            }

            if (enemyY == 230 && enemyX <= patrolX+PATROL_SIZE) {
                replayPrompt();
                break;
            }

            inputListener(p, g);
            p.sleep(10);
            updateEnemyShip(g, ENEMY_COLOR);
        }
    }

    public static void erasePatrolShip(Graphics g) {
        // Erase the patrol ship at it's previous location
        Rectangle r = new Rectangle(patrolX, PATROL_Y, PATROL_SIZE, PATROL_SIZE);
        g.setColor(Color.BLACK);
        g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
    }

    public static void drawPatrolShip(Graphics g, Color c) {
        // Redraw the patrol ship with updated coordinates
        Rectangle newRec = new Rectangle(patrolX, PATROL_Y, PATROL_SIZE, PATROL_SIZE);
        g.setColor(c);
        g.fillRect((int) newRec.getX(), (int) newRec.getY(), (int) newRec.getWidth(), (int) newRec.getHeight());
    }

    interface enemyInterface {
        // Submethods used in updateEnemyShip
        void erasePreviousInstance();
        void redrawEnemy();
    }

    public static void updateEnemyShip(Graphics g, Color c) {
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

    interface missileInterface {
        void erasePreviousInstance();

        void redrawMissile();
    }

    public static void updatePatrolMissile(Graphics g) {
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

    public static void inputListener(DrawingPanel panel, Graphics g) {
        /**
         * This function listens for player movement and action keys. It's called every
         */

        int keyCode = panel.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                // If the missile is not currently on the screen...
                if (patrolMissileY == 0) {
                    patrolMissileX = patrolX + 9;
                    // This will trigger the missile to begin the firing logic in the main for loop
                    patrolMissileY = PANEL_SIZE - (PATROL_MISSILE_LENGTH * 2) - (PATROL_SIZE * 2);
                }
                break;
            case KeyEvent.VK_RIGHT:
                // If adding 3 to patrolX won't take it out of bounds...
                if (patrolX < 270) {
                    erasePatrolShip(g);
                    patrolX += 3;
                    drawPatrolShip(g, PATROL_COLOR);
                }
                break;
            case KeyEvent.VK_LEFT:
                // If subtracting 3 from patrolX won't take it out of bounds...
                if (patrolX > 7) {
                    erasePatrolShip(g);
                    patrolX -= 3;
                    drawPatrolShip(g, PATROL_COLOR);
                }
                break;
        }
    }

    public static boolean hitDetection() {
        /**
         * Detect if the enemy ship has been hit by the player's missile
         *
         * Called on every update in the startGame() for loop
         *
         * If the missile's X coordinate is greater than or equal the enemy's X and less than or equal to the enemy's X coordinate + the enemy size. And if the missile's Y is less than or equal to enemy's Y + the enemy's size, and the missile's Y is greater than the enemyY
         */
        return (patrolMissileX >= enemyX && patrolMissileX <= enemyX + ENEMY_SIZE) && (patrolMissileY <= enemyY + ENEMY_SIZE && patrolMissileY >= enemyY);
    }


    public static void replayPrompt() {
        /**
         * Clear the window and prompt the user to choose to play again or not
         */
        // Erase the window by painting it black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 300, 300);

        // Print prompt on the screen
        g.setColor(Color.RED);
        g.drawString("Continue?", 120, 75);
        // Just for aesthetics
        g.setColor(Color.ORANGE);
        g.drawString("INSERT COIN", 110, 120);

        // Print the Yes button (Green box, Red word)
        g.setColor(Color.GREEN);
        g.fillRect(0, 150, 150, 100);
        g.setColor(Color.RED);
        g.drawString("YES", 60, 205);

        // Print the No button (Red box, green word)
        g.setColor(Color.RED);
        g.fillRect(150, 150, 150, 100);
        g.setColor(Color.GREEN);
        g.drawString("NO", 220, 205);

        // Loop until the user makes a selection
        while (true) {
            if (p.mousePressed()) {
                // If the mouse Y coordinates are within the Yes/No buttons bounds
                if (p.getClickY() >= 150 && p.getClickY() <= 250) {
                    // If the mouse coordinates are within the Yes button X bounds
                    if (p.getClickX() < 150) {
                        // Erase everything
                        g.setColor(Color.BLACK);
                        g.drawString("Continue?", 120, 75);
                        g.drawString("INSERT COIN", 110, 120);

                        g.drawString("YES", 60, 205);
                        g.drawString("NO", 220, 205);

                        g.fillRect(0, 150, 150, 100);
                        g.fillRect(150, 150, 150, 100);

                        // Call to replay here
                        /**The way this was made for the assignment (needing it
                         * all in a single file) doesn't allow for me to
                         * reinstantiate the variables and fully restart the
                         * game (at least I don't think I can, the way it's set
                         * up), so I'll have to make things more object oriented
                         * to fix it. 
                         */

                    } else {  // If the click X position is greater than 150, quit the game
                        System.exit(0);
                    }
                }
            }
        }
    }
}

