package GameObjects;

import Enum.*;
import Handler.AnimationHandler;
import Super.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Starman extends GameObject {

    public static BufferedImage[] starmanAnimation;
    
    AnimationHandler animationhandler;

    public Starman(int x, int y, int width, int height, ObjectID id, Type type) {
        super(x, y, width, height, id, type);
        this.animationhandler = new AnimationHandler();
        animationhandler.setAnimation(starmanAnimation);
        animationhandler.setDelay(100);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animationhandler.getImage(), x + 8, y, width, height, null);
    }

    @Override
    public void tick(LinkedList<GameObject> objecttlist) {
        animationhandler.tick();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void setAnimation(BufferedImage[] animation) {}
}
