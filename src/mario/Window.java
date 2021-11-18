package mario;

import javax.swing.*;

public class Window {

    static JFrame windowGame = new JFrame("Mario Pixel");//create JFrame and set title
    static GamePanel gamePanel;
    public static Mario game;
    public static Help help;

    public static void main(String[] args) {
        gamePanel = new GamePanel(); //obj GamePanel
        windowGame.setSize(1980, 1080); //set size of window
        windowGame.setResizable(false); //can't reset size
        windowGame.setUndecorated(true); 
        windowGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close and stop process
        windowGame.setLocationRelativeTo(null); //location window
        windowGame.add(gamePanel); //add game to Object JFrame
        gamePanel.start();
        windowGame.setVisible(true); //set window is visible 
    }

    public static void start_game() {
        gamePanel.stopLoop();
        gamePanel.running = false;
        game = new Mario();
        game.nextLV = 1;
        windowGame.getContentPane().removeAll();
        windowGame.add(game);
        windowGame.setVisible(true);
        windowGame.getContentPane().repaint();
        game.start();
        game.requestFocus();
    }

    public static void start_help() {
        windowGame.getContentPane().removeAll();
        help = new Help();
        windowGame.add(help);
        windowGame.setVisible(true);
        windowGame.getContentPane().repaint();
        help.requestFocus();
    }

    public static void next_LV(int nextLV) {
        windowGame.getContentPane().removeAll();
        game.running = false;
        game = new Mario();
        game.nextLV = nextLV;
        windowGame.add(game);
        windowGame.setVisible(true);
        windowGame.getContentPane().repaint();
        game.start();
        game.requestFocus();
    }

    public static void start_menu() {
        gamePanel.stopLoop();
        gamePanel.running = false;
        gamePanel = new GamePanel();
        windowGame.getContentPane().removeAll();
        windowGame.add(gamePanel);
        gamePanel.start();
        windowGame.setVisible(true);
        windowGame.getContentPane().repaint();
        gamePanel.requestFocus();
    }
}
