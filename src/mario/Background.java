package mario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Background {

    private BufferedImage image;
    private Background bg;
    
    private double x;
    private double y;
    private double dx;

    private int currentChoice = 0;
    private String[] options = {"Start", " Help", " Quit"};

    private Color titleColor[] = new Color[10];
    private Font titleFont[] = new Font[12];
    private Font font;

    public Background() {
        try {
            setVector(-0.2);
            try {
                image = ImageIO.read(new File("src\\Graphics\\manu_BG.jpg"));
                
                
            } catch (Exception e) {}

            for (int i = 0; i < 12; i++) {
                titleFont[i] = new Font("New Super Mario Font U", Font.PLAIN, 120);
            }

            titleColor[0] = new Color(255, 0, 0);
            titleColor[1] = new Color(255, 127, 0);
            titleColor[2] = new Color(255, 255, 0);
            titleColor[3] = new Color(38, 206, 94);
            titleColor[4] = new Color(48, 194, 255);
            titleColor[5] = new Color(0, 127, 255);
            titleColor[6] = new Color(67, 46, 255);
            titleColor[7] = new Color(127, 0, 255);
            titleColor[8] = new Color(247, 92, 201);
            titleColor[9] = new Color(255, 51, 153);

            font = new Font("Super Mario 256", Font.PLAIN, 60);
        } catch (Exception e) {}
    }

    public void setVector(double dx) {
        this.dx = dx;
    }

    public void update() {
        x += dx;
        if (x <= -1500) {
            x = 0;
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);

        if (x < 0) {
            g.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, null);
        }
        if (x > 0) {
            g.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, null);
        }

        g.setColor(titleColor[0]);
        g.setFont(titleFont[0]);
        g.drawString("M", 400, 150);
        g.setColor(titleColor[1]);
        g.setFont(titleFont[1]);
        g.drawString("a", 500, 150);
        g.setColor(titleColor[2]);
        g.setFont(titleFont[2]);
        g.drawString("r", 570, 150);
        g.setColor(titleColor[3]);
        g.setFont(titleFont[3]);
        g.drawString("i", 640, 150);
        g.setColor(titleColor[4]);
        g.setFont(titleFont[4]);
        g.drawString("o", 690, 150);
        g.setColor(titleColor[5]);
        g.setFont(titleFont[5]);
        g.drawString("P", 820, 150);
        g.setColor(titleColor[6]);
        g.setFont(titleFont[6]);
        g.drawString("i", 900, 150);
        g.setColor(titleColor[7]);
        g.setFont(titleFont[7]);
        g.drawString("x", 940, 150);
        g.setColor(titleColor[8]);
        g.setFont(titleFont[8]);
        g.drawString("e", 1020, 150);
        g.setColor(titleColor[9]);
        g.setFont(titleFont[9]);
        g.drawString("l", 1100, 150);

        // draw menu options
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(new Color(255, 51, 51));
            } else {
                g.setColor(new Color(0, 0, 102));
            }
            g.drawString(options[i], 650, 300 + i * 100);
        }
    }

    private void select() {
        if (currentChoice == 0) {
            Window.start_game();
        }
        if (currentChoice == 1) {
            Window.start_help();
        }
        if (currentChoice == 2) {
            System.exit(0);
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }
}
