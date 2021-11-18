package Handler;

import java.awt.Graphics;
import java.util.LinkedList;

import Super.GameObject;
import Enum.*;

public class ObjectHandler {

    public LinkedList<GameObject> objectlist = new LinkedList<GameObject>();
    private GameObject tempObject;

    public void renderObjects(Graphics g) {

        for (int i = 0; i < objectlist.size(); i++) {

            tempObject = objectlist.get(i);//set value in tempobject
            if (tempObject.getType() == Type.OBJECT) {
                tempObject.render(g); //call GameObject
            }
        }
    }

    public void renderEntitys(Graphics g) {

        for (int i = 0; i < objectlist.size(); i++) {

            tempObject = objectlist.get(i);//set value in tempobject
            if (tempObject.getType() == Type.ENTITY) {
                tempObject.render(g); //call GameObject
            }
        }
    }

    public void tick() {
        for (int i = 0; i < objectlist.size(); i++) {

            tempObject = objectlist.get(i);
            try {
                tempObject.tick(objectlist);
            } catch (Exception e) {
            }
        }
    }

    public void addObject(GameObject obj) {
        this.objectlist.add(obj); //add object to list 
    }

    public void removeObject(GameObject obj) {
        this.objectlist.remove(obj);
    }
}
