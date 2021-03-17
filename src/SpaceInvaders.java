package com.company;

import java.awt.*;
import java.awt.event.*;

public class Lab4 {
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
    public static final int ENEMY_Y = 20;
    public static int enemyX = 0;
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
        // Uncomment to make it look more Space Invaders-esque
        // g.fillRect(0, 0, 300, 300);
        // g.setColor(Color.BLACK);

        startGame();
    }

    public static void startGame() {
        /**
         * Initiate all game objects and start the action
         *
         * Also houses the main game loop *
         */
        // Init the player
        drawPatrolShip(g, PATROL_COLOR);

        for (int time = 0; time <= 1000; time++) {
            // If the missile is in the fired state (anything greater than 0)...
            if (patrolMissileY > 0) {
                // Update the missile
                updatePatrolMissile(g);
            }

            if (hitDetection()) {
                System.out.println("GAME OVER!");
                break;
            }

            inputListener(p, g);
            p.sleep(50);
            updateEnemyShip(g, ENEMY_COLOR);
        }
    }

    public static void erasePatrolShip(Graphics g) {
        // Erase the patrol ship at it's previous location
        Rectangle r = new Rectangle(patrolX, PATROL_Y, PATROL_SIZE, PATROL_SIZE);
        g.setColor(Color.WHITE);
        g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
    }

    public static void drawPatrolShip(Graphics g, Color c) {
        // Redraw the patrol ship with updated coordinates
        Rectangle newRec = new Rectangle(patrolX, PATROL_Y, PATROL_SIZE, PATROL_SIZE);
        g.setColor(c);
        g.fillRect((int) newRec.getX(), (int) newRec.getY(), (int) newRec.getWidth(), (int) newRec.getHeight());
    }

    interface enemyInterface {
        void erasePreviousInstance();

        void redrawEnemy();
    }

    public static void updateEnemyShip(Graphics g, Color c) {
        /**
         * Draws the enemy ship first in white, then increment enemyX by 1, and then
         * Draws the ship again in red. Put a call to this method inside the for loop.
         *
         * Took a liberty and assumed it should move back and forth I didn't understand
         * how it could work otherwise
         */

        // Define the Enemy Functions
        enemyInterface ei = new enemyInterface() {
            public void erasePreviousInstance() {
                // Erase the previous instance of the enemy
                Rectangle r = new Rectangle(enemyX, ENEMY_Y, ENEMY_SIZE, ENEMY_SIZE);
                g.setColor(Color.WHITE);
                g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
            }

            public void redrawEnemy() {
                // Redraw the enemy at the new position
                Rectangle updatedRec = new Rectangle(enemyX, ENEMY_Y, ENEMY_SIZE, ENEMY_SIZE);
                g.setColor(c);
                g.fillRect((int) updatedRec.getX(), (int) updatedRec.getY(), (int) updatedRec.getWidth(),
                        (int) updatedRec.getHeight());
            }
        };

        // Begin the actual enemy logic
        // If the X Position is within the bounds
        if (enemyX <= 270 && enemyX >= 0) {

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
                g.setColor(Color.WHITE);
                g.drawLine(patrolMissileX, patrolMissileY, patrolMissileX, patrolMissileY + PATROL_MISSILE_LENGTH);
            }

            public void redrawMissile() {
                // Draw it in black again
                g.setColor(Color.BLACK);
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
         * This will call panel.getKeyCode() that returns a code for a key. It returns 0
         * if no key has been pushed, and will return one of the above constants if the
         * corresponding arrow key has been pushed. •The method handleKeys should do
         * nothing if the returned key code is 0. •If the key code is RIGHT_ARROW or
         * LEFT_ARROW, move the patrol ship to the right or left by 3 pixels, but do not
         * let any part of the patrol ship move off the screen. To move the patrol ship:
         * •draw the patrol ship in white (to erase the old one) •change patrolX •draw
         * the patrol ship in green Call the method handleKeys in the loop. You should
         * be able to move the patrol ship by pushing the left and right arrows on the
         * keyboard. Before using the arrow keys, make sure your drawing window is the
         * active window by clicking the mouse in this window.
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
                if (patrolX < 273) {
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
         * Returns true if the current missile has hit the enemy ship and false
         * otherwise. There is a hit if the top of the missile is inside the enemy ship.
         * For this to happen two thing must be true: •the x value of the top of the
         * missile must be between the left and right sides of the enemy •the y value of
         * the top of the missile must be between the top and bottom of the enemy. At
         * the end of the loop in startGame, set hit to true if detectHit() returns
         * true. Modify moveEnemyShipAndDraw so that if hit is true, the enemy ship is
         * drawn in black and does not move. In this case, display the message: Enemy
         * ship hit! in green on a line below the patrol ship. If the enemy ship moves
         * off the screen or time runs out, Display the message: Enemy ship got away! in
         * red on a line below the patrol ship.
         */
        /*
         * if patrolMissileX is between the left and right sides of enemyBounds && pMX
         * is between top and bottom: return true;
         *
         */
        if ((patrolMissileX >= enemyX && patrolMissileX <= enemyX + ENEMY_SIZE)
                && (patrolMissileY <= 50 && patrolMissileY >= 20)) {
            return true;
        }
        return false;
    }

    public static void replayPrompt() {
        int[] yesBounds = new int[4];
        g.fillRect(0, 0, 300, 300);
        g.setColor(Color.BLACK);

        // Print prompt on the screen
        g.setColor(Color.RED);
        g.drawString("Continue?", 110, 100);
        g.setColor(Color.ORANGE);
        g.drawString("INSERT COIN", 100, 150);
        g.setColor(Color.RED);
        g.drawString("YES", 110, 200);
        g.drawString("NO", 200, 200);
     }
}
