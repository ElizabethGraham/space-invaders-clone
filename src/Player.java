package src;

import java.awt.*;


public class Player {
    // Player Variables
    public final int PATROL_Y = 250;
    public int patrolX = 270;
    public final int PATROL_SIZE = 20;
    public final Color PATROL_COLOR = Color.GREEN;


    public void erasePatrolShip(Graphics g) {
        // Erase the patrol ship at it's previous location
        Rectangle r = new Rectangle(patrolX, PATROL_Y, PATROL_SIZE, PATROL_SIZE);
        g.setColor(Color.BLACK);
        g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
    }


    public void drawPatrolShip(Graphics g, Color c) {
        // Redraw the patrol ship with updated coordinates
        Rectangle newRec = new Rectangle(patrolX, PATROL_Y, PATROL_SIZE, PATROL_SIZE);
        g.setColor(c);
        g.fillRect((int) newRec.getX(), (int) newRec.getY(), (int) newRec.getWidth(), (int) newRec.getHeight());
    }
}
