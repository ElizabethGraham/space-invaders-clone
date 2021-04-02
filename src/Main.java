package src;

import java.awt.*;


public class Main {
    // Window Class Variables
    public static final int PANEL_SIZE = 300; // Constant panel size var, width and height
    public static DrawingPanel p = new DrawingPanel(PANEL_SIZE, PANEL_SIZE); // Init Panel with proper dimensions
    public static Graphics g = p.getGraphics(); // Init graphics instance

    // Create a new main instance 'game'
    public static GameLogic game = new GameLogic();
    public static Enemy enemy = new Enemy();
    public static Player player = new Player();
    public static PlayerMissile pm = new PlayerMissile();

    public static void main(String[] args) {
        game.startGame(g, p, player, enemy, pm);
    }


    public static void reset() {
        game = new GameLogic();
        enemy = new Enemy();
        player = new Player();
        pm = new PlayerMissile();
        game.startGame(g, p, player, enemy, pm);
    }
}
