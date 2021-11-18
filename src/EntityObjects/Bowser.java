package EntityObjects;

import Enum.ObjectID;
import Enum.Type;
import GameObjects.BossFire;
import Handler.AnimationHandler;
import Handler.ObjectHandler;
import Loader.MusicLoader;
import Super.GameObject;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;

public class Bowser extends GameObject {

    Random random;
    private float gravity = 0.3f;
    private final int maxSpeed = 10;
    private int loopWalk = 0;
    private int callGoomba = 0;
    private BufferedImage image;
    public static int temp = 0; //**************
    public static int bowser_heart = 50;
    public static BufferedImage[] bowserAnimation;
    public static BufferedImage[] bowserDieAnimation;
    public static boolean direction;
    ObjectHandler objecthandler;
    AnimationHandler animationhandler;
    MusicLoader musicloader;

    public Bowser(int x, int y, int width, int height, ObjectID id, ObjectHandler objecthandler, Type type, MusicLoader musicloader) {
        super(x, y, width, height, id, type);
        this.objecthandler = objecthandler;
        this.animationhandler = new AnimationHandler();

        this.random = new Random();
        animationhandler.setAnimation(bowserAnimation);
        animationhandler.setDelay(400);
        this.velX = 2;

        this.musicloader = musicloader;
        direction = true;

        try {

            image = ImageIO.read(new File("src\\Graphics\\bossHeart.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {

        if (temp == 0) {
            g.drawImage(animationhandler.getImage(), x, y, width, height, null);
        } else if (temp == 1) {
            g.drawImage(animationhandler.getImage(), x + width, y, -width, height, null);
        }


        for (int i = 0; i < bowser_heart; i++) {
            g.drawImage(image, x + i * 3, y - 50, 16, 32, null);
        }
    }

    @Override
    public void tick(LinkedList<GameObject> objectlist) {

        if (loopWalk >= 100) {
            if (Bowser.bowser_heart >= 0) {
                if (velX >= 0) {
                    objecthandler.addObject(new BossFire(x + 160, y + 85, 64, 16, ObjectID.BOSSFIRE, objecthandler, Type.ENTITY, musicloader));
                    musicloader.play(MusicLoader.koopa);
                } else {
                    objecthandler.addObject(new BossFire(x, y + 85, 64, 16, ObjectID.BOSSFIRE, objecthandler, Type.ENTITY, musicloader));
                    try {
                        musicloader.play(MusicLoader.koopa);
                    } catch (Exception e) {
                    }
                }
            }
            loopWalk = 0;

            temp = random.nextInt(2);
        } else {
            loopWalk++;

        }

        if (callGoomba >= 800) {

            objecthandler.addObject(new Goomba(x, y + 150, 56, 56, ObjectID.GOOMBA, objecthandler, Type.ENTITY));
            callGoomba = 0;
        } else {
            callGoomba++;
        }
        if (temp == 0) {
            this.velX = 1; 
        } else if (temp == 1) {
            this.velX = -1;
        }

        if (alive) {
            this.x += this.velX;
        }
        this.y += this.velY;

        if (falling) {
            this.velY += gravity;
            if (this.velY >= maxSpeed) {
                this.velY = maxSpeed;
            }
        }
        if (bowser_heart != 0) {
            animationhandler.tick();
        }

        if (alive) {
            try {
                collisions(objectlist);
            } catch (Exception e) {
            }

        }

        if (velX > 0) {
            direction = true;
        } else if (velX < 0) {
            direction = false;
        }
    }

    public void collisions(LinkedList<GameObject> objectlist) {

        for (int i = 0; i < objecthandler.objectlist.size(); i++) {
            GameObject tempObject = objecthandler.objectlist.get(i);

            if (tempObject.getId() == ObjectID.BLOCK || tempObject.getId() == ObjectID.PIPE || tempObject.getId() == ObjectID.TRAMPOLINE) {

                if (getBoundsBottom().intersects(tempObject.getBounds())) {

                    y = tempObject.getY() - tempObject.getHeight() - 56 * 3;
                    velY = 0;
                    falling = false;

                } else {
                    falling = true;
                }

                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    loopWalk = 100;
                    x = tempObject.getX() - tempObject.getWidth() - 56 * 3;

                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    loopWalk = 100;
                    x = tempObject.getX() + tempObject.getWidth();

                }

                if (getBoundsTop().intersects(tempObject.getBounds())) {

                    y = tempObject.getY() + tempObject.getHeight();
                    velY = 0;
                }
            }

        }

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getBoundsBottom() {

        return new Rectangle((int) ((int) x + (width / 2) - ((width / 2) / 2)), (int) ((int) y + height / 2), (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsTop() {

        return new Rectangle((int) ((int) x + (width / 2) - ((width / 2) / 2)), (int) y, (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsRight() {

        return new Rectangle((int) ((int) x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
    }

    public Rectangle getBoundsLeft() {

        return new Rectangle((int) x, (int) y + 5, (int) 5, (int) height - 10);
    }

    @Override
    public void setAnimation(BufferedImage[] animation) {

        animationhandler.setAnimation(animation);
        animationhandler.setDelay(-1);
        height = 16; 
    }

}
