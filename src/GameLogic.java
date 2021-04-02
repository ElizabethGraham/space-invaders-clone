package src;

import java.awt.*;


public class GameLogic {
    public void startGame(Graphics g, DrawingPanel p, Player play,  Enemy e, PlayerMissile pm) {
        /**
         * Initiate all game objects and start the action
         *
         * Houses the main game loop
         */
        

        // Set the background to black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 300, 300);

        // Init the player
        play.drawPatrolShip(g, play.PATROL_COLOR);

        for (int time = 0; time <= 3000; time++) {
            // If the missile is in the fired state (anything greater than 0)...
            if (pm.patrolMissileY > 0) {
                // Update the missile
                pm.updatePatrolMissile(g);
            }

            if ((pm.patrolMissileX >=  e.enemyX && pm.patrolMissileX <=  e.enemyX +  e.ENEMY_SIZE) && (pm.patrolMissileY <=  e.enemyY +  e.ENEMY_SIZE && pm.patrolMissileY >=  e.enemyY)) {
                ReplayPrompt.promptScreen(p, g);
                break;
            }

            if (e.enemyY == 230 && e.enemyX <= play.patrolX+play.PATROL_SIZE) {
                ReplayPrompt.promptScreen(p, g);
                break;
            }

            PlayerActions.inputListener(p, g, play, pm);
            p.sleep(40);
            e.updateEnemyShip(g, e.ENEMY_COLOR);
        }
    }
}
