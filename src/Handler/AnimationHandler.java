package Handler;

import java.awt.image.BufferedImage;

public class AnimationHandler {
    
    private BufferedImage[] animation;
    private int currentImage;
    
    private long startTime;
    private long delay;
    
    public void setAnimation(BufferedImage[] images){
        
        animation = images;
        if(currentImage >= animation.length){
            currentImage = 0;
        }
    }
    
    public void tick(){
        
        if(delay == -1){
            return;
        }
        
        long pastTime = (System.nanoTime()-startTime)/1000000;
        
        if(pastTime > delay){
            currentImage++;
            startTime = System.nanoTime();
        }
        
        if(currentImage == animation.length){
            currentImage = 0;
        }
    }
    
    public BufferedImage getImage(){
        return animation[currentImage];
    }
    
    public void setDelay(long delay){
        this.delay = delay;
    }
}
