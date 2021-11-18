package Inputs;

import Super.GameObject;
import mario.Mario;

public class Camera {

    private float x;
    private float y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject obj) {
        this.x = -obj.getX() + Mario.width / 2 - 100;
    }

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
}
