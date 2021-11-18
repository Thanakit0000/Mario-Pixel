package EntityObjects;

import Enum.ObjectID;
import Enum.Type;
import Handler.AnimationHandler;
import Handler.ObjectHandler;
import Super.GameObject;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Boo extends GameObject {

    public static BufferedImage[] booAnimation;

    public boolean direction;
    
    ObjectHandler objecthandler;
    AnimationHandler animationhandler;
    
    public Boo(int x, int y, int width, int height, ObjectID id, ObjectHandler objecthandler, Type type) {
        super(x, y, width, height, id, type);
        this.objecthandler = objecthandler;
        this.animationhandler = new AnimationHandler();
        animationhandler.setAnimation(booAnimation);
        animationhandler.setDelay(400);

        int temp = 1;
        if (temp == 0) {
            velX = 2; 
        } else if (temp == 1) {
            velX = -2;
        }

        direction = true;
    }

    @Override
    public void render(Graphics g) {

        if (direction) {
            g.drawImage(animationhandler.getImage(), x, y, width, height, null);
        } else if (!direction) {
            g.drawImage(animationhandler.getImage(), x + width, y, -width, height, null);
        }
    }

    @Override
    public void tick(LinkedList<GameObject> objectlist) {
 
        this.x += this.velX;        
        this.y += this.velY;

        animationhandler.tick();
        collisions(objectlist);
        
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
                    y = tempObject.getY() - tempObject.getHeight();
                    velY = 0;                    
                } 

                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - tempObject.getWidth();
                    velX *= -1;
                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + tempObject.getWidth();
                    velX *= -1;
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
    public void setAnimation(BufferedImage[] animation) {}
}
