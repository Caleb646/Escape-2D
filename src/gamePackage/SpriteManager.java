package gamePackage;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class SpriteManager {

    private ArrayList<Sprite> currentSprites;

    private GameHandler gameHandler;

    public SpriteManager(GameHandler g) {
        this.gameHandler = g;
        this.gameHandler.setSpriteManager(this);
        currentSprites = new ArrayList<Sprite>();
    }

    public void update() {

        for(int i = currentSprites.size()-1; i>-1; i--) {
            Sprite s = currentSprites.get(i);
            if(!s.isAlive()) {
                currentSprites.remove(s);
                return;
            }
            if(s instanceof PlayerAxe)
                s.checkSpriteCollision(this.currentSprites);
            else if(s instanceof TurretBullet)
                s.checkSpriteCollision(this.currentSprites);
            s.update();
        }
        // for(Sprite s : currentSprites) {
        //     s.update();
        // }
    }

    public void render(Graphics2D g2d) {
        for(Sprite s : currentSprites) {
            s.render(g2d);
        }
    }

    public void addSprite(Sprite s) {
        currentSprites.add(s);
    }

    public void clearAllSprites() {
        currentSprites.clear();
    }
}