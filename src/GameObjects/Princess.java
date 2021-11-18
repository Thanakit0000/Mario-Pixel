package GameObjects;

import Enum.ObjectID;
import Enum.Type;
import Super.GameObject;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Princess extends GameObject {

    public static BufferedImage img;

    public Princess(int x, int y, int width, int height, ObjectID id, Type type) {
        super(x, y, width, height, id, type); //call GameObject class
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
