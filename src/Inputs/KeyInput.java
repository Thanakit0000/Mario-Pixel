package Inputs;

import EntityObjects.Player;
import Enum.*;
import GameObjects.Fireball;
import Handler.ObjectHandler;
import Loader.MusicLoader;
import Super.GameObject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    ObjectHandler objecthandler;
    MusicLoader musicloader;

    public KeyInput(ObjectHandler objecthandler, MusicLoader musicloader) {
        this.objecthandler = objecthandler;
        this.musicloader = musicloader;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        for (int i = 0; i < objecthandler.objectlist.size(); i++) {
            GameObject tempObject = objecthandler.objectlist.get(i);
            if (tempObject.getId() == ObjectID.PLAYER) {
                
                if (key == KeyEvent.VK_LEFT) {
                    tempObject.setVelX(-5);
                }

                if (key == KeyEvent.VK_RIGHT) {
                    tempObject.setVelX(5);
                }

                if (key == KeyEvent.VK_UP && !tempObject.getJumping()) { //or vk_space
                    tempObject.setVelY(-8);
                    tempObject.setJumping(true);
                    musicloader.play(MusicLoader.jump);
                }
                
                if (key == KeyEvent.VK_SPACE) {
                    if (Player.ballNumber > 0) {
                        Player.ballNumber--;
                        objecthandler.addObject(new Fireball((int) tempObject.getX(), (int) tempObject.getY(), 64, 16, ObjectID.FIREBALL, objecthandler, Type.ENTITY, musicloader));
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        for (int i = 0; i < objecthandler.objectlist.size(); i++) {
            GameObject tempObject = objecthandler.objectlist.get(i);
            if (tempObject.getId() == ObjectID.PLAYER) {

                if (key == KeyEvent.VK_LEFT) {
                    tempObject.setVelX(0);
                }

                if (key == KeyEvent.VK_RIGHT) {
                    tempObject.setVelX(0);
                }
            }
        }
    }
}
