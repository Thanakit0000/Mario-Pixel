package GameObjects;

import EntityObjects.Bowser;
import EntityObjects.Goomba;
import Enum.*;
import Handler.*;
import Loader.MusicLoader;
import Super.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import EntityObjects.Player;

public class Fireball extends GameObject {

    private float gravity = 0.5f;
    private final int maxSpeed = 10;

    public static BufferedImage[] fireballAnimation;

    ObjectHandler objecthandler;
    AnimationHandler animationhandler;
    MusicLoader musicloader;

    public Fireball(int x, int y, int width, int height, ObjectID id, ObjectHandler objecthandler, Type type, MusicLoader musicloader) {
        super(x, y, width, height, id, type);
        try {
            musicloader.play(MusicLoader.fireball);
        } catch (Exception e) {}
        
        this.objecthandler = objecthandler;
        this.animationhandler = new AnimationHandler();        
        animationhandler.setAnimation(fireballAnimation);
        animationhandler.setDelay(100);

        if (EntityObjects.Player.facingRight) {
            velX = 5; 
        } else if (!EntityObjects.Player.facingRight) {
            velX = -5;
        }

        this.musicloader = musicloader;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animationhandler.getImage(), x, y - 32, width, height + 48, null);
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
            } else if (tempObject.getId() == ObjectID.GOOMBA) {

                if (getBounds().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds())) {
                    
                    objecthandler.removeObject(this);
                    tempObject.setAnimation(Goomba.dieAnimation);
                    musicloader.play(MusicLoader.GoombaDie);
                    tempObject.setAlive(false);
                    Player.score += 30;
                    velY = -5;
                    return;
                }
            } else if (tempObject.getId() == ObjectID.BOWSER) {

                if (getBounds().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds())) {
                    objecthandler.removeObject(this);
                    Bowser.bowser_heart--;
                    try {
                        musicloader.play(MusicLoader.koopaHit);
                    } catch (Exception e) {}
                    
                    if (Bowser.bowser_heart <= 0) {
                        Player.score += 100;
                        tempObject.setAnimation(Bowser.bowserDieAnimation);
                        tempObject.setHeight(180);
                        musicloader.play(MusicLoader.koopaDie);
                        tempObject.setAlive(false);
                    }
                    return;
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
