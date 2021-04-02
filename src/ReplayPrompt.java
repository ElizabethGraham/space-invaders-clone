package src;

import java.awt.*;

public class ReplayPrompt {

    public static void promptScreen(DrawingPanel p, Graphics g) {
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
                System.out.println("X: " + p.getClickX() + "   Y: " + p.getClickY());
                // If the mouse Y coordinates are within the Yes/No buttons bounds
                if (p.getClickY() > 150 && p.getClickY() < 250) {
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

                        // Reset
                        System.out.println("Resetting...");
                        Main.reset();
                    } 
                    
                    else if (p.getClickX() >= 150) {  // If the click X position is greater than 150, quit the game
                        System.out.println("Quitting...");
                        System.exit(0);
                    }
                }
            }
        }
    }
}
