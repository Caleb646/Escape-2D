package gamePackage;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TurretEnemy extends Enemy {

    private final int turretRange;

    private BufferedImage rightFrame = GameAssets.turretLeftImg;
    private BufferedImage leftFrame = GameAssets.turretRightImg;

    public TurretEnemy(GameHandler gameHandler, float x, float y, int w, int h) {
        super(gameHandler, x, y, w, h);
        turretRange = 50;
        attackInterval = 25_000;
    }

    public void update() {
        if(canSee() && canAttack()) {

            Player p = this.gameHandler.getPlayer();

            // Vector2D target = new Vector2D(p.getX()+this.gameHandler.getCamera().getCameraX(),
            //  p.getY()+this.gameHandler.getCamera().getCameraY());

             Vector2D target = new Vector2D(p.getX(), p.getY()-spriteHeight*0.3f);

           new TurretBullet(gameHandler, getX(), getY()-spriteHeight, spriteWidth, spriteHeight, target);
        }

    }

    public void render(Graphics2D g2d) {

        g2d.drawImage(getCurrentAnimation(), (int) (pos.getX()-gameHandler.getCamera().getCameraX()),
        (int) (pos.getY()-gameHandler.getCamera().getCameraY()), spriteWidth, spriteHeight, null);
    }

    public BufferedImage getCurrentAnimation() {

        if(this.gameHandler.getPlayer().getX() > this.getX())//to the right
            return rightFrame;
        else  
            return leftFrame;
    }

    public boolean canSee() {
        if(this.pos.distance(this.gameHandler.getPlayer().getPos()) < turretRange) {
            return true;
        }
        return false;
    }
    
}