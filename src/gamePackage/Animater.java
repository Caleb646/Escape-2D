package gamePackage;
import java.awt.image.BufferedImage;

public class Animater {
    
    private long lastTime = System.currentTimeMillis();
    private long now;
    private long deltaTime = 0l;
    private int interval = 200;
    private int index = 0;

    private BufferedImage[] animation;

    public Animater(BufferedImage[] animation) {
        this.animation = animation;
    }

    public void update() {
        now = System.currentTimeMillis();
        deltaTime = now - lastTime;

        if(deltaTime > interval) {
            index++;
            lastTime = System.currentTimeMillis();
            deltaTime = 0l;

            if(index > animation.length-1)
                index = 0;
        }
    }

    public BufferedImage getCurrentAnimation() {
        //System.out.println(index);
        return this.animation[index];
    }

    //getters and setters
    
    public void setInterval(int i) {
        this.interval = i;
    }
}