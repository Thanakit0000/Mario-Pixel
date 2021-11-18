package mario;

import EntityObjects.Player;
import Enum.ObjectID;
import Handler.ObjectHandler;
import Inputs.*;
import Loader.*;
import Super.GameObject;

import java.awt.*; //Abstract Window Toolkit use to create GUI
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

//implement is class interface and every method is abstract method.When use you must Overriding ***
public class Mario extends Canvas implements Runnable { //draw graphic GUI

    private double x; // x in mario
    private double y; // y in mario
    private double dx; //x vector

    public int nextLV = 1; //nextLV is variable to call map

    public static int width; //width in window
    public static int height; //height in window

    private JFrame frame; //create JFrame
    private BufferedImage image; //create BufferedImage

    public boolean running = false; //running is show program is ruuning and render graphics per sec

    Thread thread; //Thread is multi process and make good program, fast process, a few memory
    ObjectHandler objecthandler;
    LevelLoader levelloader;
    Camera camera;
    GraphicsLoader graphicsloader;
    MusicLoader musicloader;

    public void init() {

        width = this.getWidth(); //set width
        height = this.getHeight(); //set height

        graphicsloader = new GraphicsLoader();
        graphicsloader.load(); //call load in graphicsloader

        objecthandler = new ObjectHandler();
        levelloader = new LevelLoader(objecthandler, musicloader);

        try {
            if (nextLV == 1) {
                image = ImageIO.read(new File("src\\Graphics\\BG.jpg"));
                levelloader.load("src/Level/Level_1.txt"); //call load(load in LevelLoader class)

            } else if (nextLV == 3) {
                image = ImageIO.read(new File("src\\Graphics\\BG2.jpg"));
                levelloader.load("src/Level/Level_2.txt"); //call load(load in LevelLoader class)
            } else if (nextLV == 5) {
                image = ImageIO.read(new File("src\\Graphics\\BG3.png"));
                levelloader.load("src/Level/Level_3.txt"); //call load(load in LevelLoader class)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        camera = new Camera(0, 0);//create object pass(0,0)

        addKeyListener(new KeyInput(objecthandler, musicloader));//call KeyInput to used class KeyInput in class Mario 
        setFocusable(true);
    }

    public synchronized void start() {
        new Thread(this).start();
        musicloader = new MusicLoader();
        musicloader.load();
        if (nextLV == 1) {
            musicloader.loop(MusicLoader.theme);
        } else if (nextLV == 3) {
            musicloader.loop(MusicLoader.theme3);
        } else if (nextLV == 5) {
            musicloader.loop(MusicLoader.theme5);
        }
        running = true;
    }

    public void run() { //run not argument and not return value!!
        init();
        long lastTime = System.nanoTime(); //count time return nano time
        double amountOfTicks = 60.0; //clock tick
        double ns = 1000000000 / amountOfTicks;
        double delta = 0; //DeltaTime is mostly used to keep track of exactly the amount of time  
        int updates = 0;
        int frames = 0;     //frames graphics
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            update();
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }

            try {
                render();//draw the frame/graphics
            } catch (Exception e) {
            }
        }
    }

    public void render() throws InterruptedException {
        //Gets the buffer strategy our canvas is currently using
        BufferStrategy bs = this.getBufferStrategy(); // easy to drawing to surfaces and components in a general way

        if (bs == null) {
            this.createBufferStrategy(3);//Create a buffer strategy using three buffers (3 buffer the canvas)
            return;
        }

        Graphics g = bs.getDrawGraphics(); //add the graphics
        Graphics2D g2d = (Graphics2D) g;

        if (nextLV == 1 || nextLV == 3 || nextLV == 5) {
            setVector(-0.4);
            g.fillRect(0, 0, width, height); //draw rectangle

            g.drawImage(image, (int) x, 0, 1539, 1080, null);
            g.drawImage(image, (int) x + 1539, 0, 1539, 1080, null);

            g2d.translate(camera.getX(), camera.getY()); //set point pixel (right,down)

            objecthandler.renderObjects(g); //render graphics
            objecthandler.renderEntitys(g);

            g2d.translate(-camera.getX(), -camera.getY()); //left and up

            //g.dispose(); // close this window
            bs.show();   //show graphics
        } else if (nextLV == 2 || nextLV == 4) {

            try {
                image = ImageIO.read(new File("src\\Graphics\\nextLV.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            g.drawImage(image, (int) x, 0, 1539, 1080, null);

            g2d.setFont(new Font("Super Mario Brothers", Font.PLAIN, 100));
            g.setColor(new Color(224, 224, 224));
            g.drawString(" NEXT LEVEL ", (int) (x + 750), 600);

            bs.show();   //show graphics

        } else if (nextLV == 6) {

            try {
                image = ImageIO.read(new File("src\\Graphics\\end.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            g.drawImage(image, (int) x, 0, 1550, 870, null);

            g2d.setFont(new Font("New Super Mario Font U", Font.PLAIN, 140));
            g.setColor(new Color(153, 0, 0));
            g.drawString("YOU WIN!", 490, 160);
            g.setColor(new Color(224, 224, 224));
            g2d.setFont(new Font("New Super Mario Font U", Font.PLAIN, 80));
            g.drawString("Total Score: " + Player.score, 480, 320);

            bs.show();   //show graphics

        } else if (nextLV == 7) {

            try {
                image = ImageIO.read(new File("src\\Graphics\\BG_gameover.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            g.drawImage(image, (int) x, 0, 1560, 870, null);

            g2d.setFont(new Font("New Super Mario Font U", Font.PLAIN, 190));
            g.setColor(new Color(224, 224, 224));
            g.drawString("GAME", 30, 420);
            g.drawString("VER!!", 1000, 420);

            bs.show();   //show graphics

        } else if (nextLV == 0) {
            try {
                image = ImageIO.read(new File("src\\Graphics\\BG_help.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            g.drawImage(image, (int) x, 0, 1550, 850, null);

            bs.show();
        }
    }

    public void tick() {

        objecthandler.tick(); //call tick

        for (int i = 0; i < objecthandler.objectlist.size(); i++) {
            GameObject tempObject = objecthandler.objectlist.get(i);
            if (tempObject.getId() == ObjectID.PLAYER) {
                camera.tick(tempObject);
            }
        }
    }

    public void setVector(double dx) {
        this.dx = dx;
    }

    public void update() {
        x += dx;
        if (x <= -1539) {
            x = 0;
        }
    }
}
