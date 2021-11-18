package GameObjects;

import Enum.*;
import Handler.*;
import Loader.MusicLoader;
import Super.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;


public class BossFire extends GameObject {

    private float gravity = 0.5f;

    public static BufferedImage[] bossfireAnimation;

    ObjectHandler objecthandler;
    AnimationHandler animationhandler;
    MusicLoader musicloader;

    public BossFire(int x, int y, int width, int height, ObjectID id, ObjectHandler objecthandler, Type type, MusicLoader musicloader) {
        super(x, y, width, height, id, type);
        this.objecthandler = objecthandler;
        this.animationhandler = new AnimationHandler();
        animationhandler.setAnimation(bossfireAnimation);
        animationhandler.setDelay(100);
           
        if (EntityObjects.Bowser.direction) {
            velX = 5;
        } else if (!EntityObjects.Bowser.direction) {
            velX = -5;
        }
        
        this.musicloader = musicloader;
    }

    @Override
    public void render(Graphics g) {

        if (velX >= 0) {
            g.drawImage(animationhandler.getImage(), x + width, y - 32, -width, height + 48, null);
        } else {
            g.drawImage(animationhandler.getImage(), x, y - 32, width, height + 48, null);
        }
    }

    @Override
    public void tick(LinkedList<GameObject> objectlist) {
     
        this.x += this.velX;       
        this.y += this.velY;
        this.velY += gravity;

        animationhandler.tick();    
        try {
            collisions(objectlist);
        } catch (Exception e) {}       
    }

    public void collisions(LinkedList<GameObject> objectlist) {

        for (int i = 0; i < objecthandler.objectlist.size(); i++) {
            GameObject tempObject = objecthandler.objectlist.get(i);

            if (tempObject.getId() == ObjectID.BLOCK || tempObject.getId() == ObjectID.PIPE || tempObject.getId() == ObjectID.TRAMPOLINE) {

                if (getBoundsBottom().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - tempObject.getHeight();
                    velY = 0;
                }

                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    objecthandler.removeObject(this);
                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    objecthandler.removeObject(this);
                }
                
                if (this.y >= 1100) {
                    objecthandler.removeObject(this);
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
        height = 16; //this is height in goomba die
    }
}
