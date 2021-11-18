package GameObjects;

import Enum.*;
import Super.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Trampoline extends GameObject {

    public static BufferedImage img;

    public Trampoline(int x, int y, int width, int height, ObjectID id, Type type) {
        super(x, y, width, height, id, type);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void tick(LinkedList<GameObject> objecttlist) {}

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void setAnimation(BufferedImage[] animation) {}
}
