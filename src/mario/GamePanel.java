package mario;

import Loader.MusicLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    // dimensions
    public static final int WIDTH = 1980;
    public static final int HEIGHT = 1080;

    // game thread
    private Thread thread;
    public boolean running;

    // image
    private BufferedImage image;
    private Graphics2D g;

    

    Background background = new Background();
    MusicLoader musicloader;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
    }

    public void run() {
        init();
        long start;
        long elapsed;
        long wait;
        // game loop
        while (running) {
            update();
            try {
                draw();
                drawToScreen();
            } catch (Exception e) {}                   
        }
    }

    private void update() {
        background.update();
    }

    private void draw() {
        background.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH + 150, HEIGHT, null); // BG size
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        background.keyPressed(key);
    }

    public synchronized void start() {
        musicloader = new MusicLoader();
        musicloader.load();
        musicloader.loop(MusicLoader.title);
    }

    public void stopLoop() {
        musicloader.stopLoop();
    }
}
