package mario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Help extends JPanel implements KeyListener {

    private BufferedImage image;

    public Help() {

        try {
            image = ImageIO.read(new File("src\\Graphics\\BG_help.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, 1540, 870, null);
        g.setFont(new Font("New Super Mario Font U", Font.PLAIN, 50));
        g.setColor(new Color(224, 224, 224));
        g.drawString("Press", 55, 760);
        g.drawString("to the main menu", 380, 760);
        g.setColor(new Color(255, 71, 71));
        g.drawString("Enter", 220, 760);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            Window.start_menu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
