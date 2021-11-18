package EntityObjects;

import Enum.*;
import Handler.*;
import Super.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class Goomba extends GameObject {

    private float gravity = 0.3f;
    private final int maxSpeed = 10;

    public static BufferedImage[] walkingAnimation;
    public static BufferedImage[] dieAnimation;

    ObjectHandler objecthandler;
    AnimationHandler animationhandler;
    Random random;

    public Goomba(int x, int y, int width, int height, ObjectID id, ObjectHandler objecthandler, Type type) {
        super(x, y, width, height, id, type);
        this.objecthandler = objecthandler;
        this.animationhandler = new AnimationHandler();
        this.random = new Random();

        animationhandler.setAnimation(walkingAnimation);
        animationhandler.setDelay(400);

        int temp = random.nextInt(2);
        if (temp == 0) {
            velX = 2;
        } else if (temp == 1) {
            velX = -2;
        }
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(animationhandler.getImage(), x, y, width, height, null);

    }

    @Override
    public void tick(LinkedList<GameObject> objectlist) {

        if (alive) {
            this.x += this.velX;
        }
        this.y += this.velY;

        if (falling || jumping) {
            this.velY += gravity;
            if (this.velY >= maxSpeed) {
                this.velY = maxSpeed;
            }
        }

        animationhandler.tick();
        if (alive) {
            try {
                collisions(objectlist);
            } catch (Exception e) {
            }

        }
    }

    public void collisions(LinkedList<GameObject> objectlist) {

        for (int i = 0; i < objecthandler.objectlist.size(); i++) {

            GameObject tempObject = objecthandler.objectlist.get(i);

            if (tempObject.getId() == ObjectID.BLOCK || tempObject.getId() == ObjectID.PIPE || tempObject.getId() == ObjectID.TRAMPOLINE) {

                if (getBoundsBottom().intersects(tempObject.getBounds())) {

                    y = tempObject.getY() - tempObject.getHeight();
                    velY = 0;
                    falling = false;
                    jumping = false;
                } else {
                    falling = true;
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
    public void setAnimation(BufferedImage[] animation) {

        animationhandler.setAnimation(animation);
        animationhandler.setDelay(-1);
        height = 16;
    }

}
