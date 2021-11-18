package Super;

import Enum.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class GameObject {
    
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    protected float velX;
    protected float velY;
    
    protected ObjectID id;
    protected Type type;
    
    protected boolean falling = true;
    protected boolean jumping = false;
    protected boolean alive = true;
     
    public GameObject(int x, int y ,int width, int height,ObjectID id, Type type){ //set value
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.type = type;     
    }
    
    public abstract void render(Graphics g); // render graphics
    public abstract void tick(LinkedList<GameObject>objecttlist);
    public abstract Rectangle getBounds();
    public abstract void setAnimation(BufferedImage[]animation);
    
    public void setX(int x){
        this.x=x;
    }
    public int getX(){
        return x;
    }
    
    public void setY(int y){
        this.y=y;
    }
    public int getY(){
        return y;
    }
    
    public void setWidth(int width){
        this.width=width;
    }
    public int getWidth(){
        return width;
    }
    
    public void setHeight(int height){
        this.height=height;
    }
    public int getHeight(){
        return height;
    }
    
    public void setFalling(boolean falling){
        this.falling=falling;
    }
    public boolean getFalling(){
        return falling;
    }
    
    public void setId(ObjectID id){
        this.id=id;
    }
    public ObjectID getId(){
        return id;
    }
    
    public void setJumping(boolean jumping){
        this.jumping=jumping;
    }
    public boolean getJumping(){
        return jumping;
    }
    
    public void setVelX(float velX){
        this.velX=velX;
    }
    public float getVelX(){
        return velX;
    }
    
    public void setVelY(float velY){
        this.velY=velY;
    }
    public float getVelY(){
        return velY;
    }
    
    public void setType(Type type){
        this.type=type;
    }
    public Type getType(){
        return type;
    }

    public void setAlive(boolean alive){
        this.alive=alive;
    }
    public boolean isAlive(){
        return alive;
    }  
}
