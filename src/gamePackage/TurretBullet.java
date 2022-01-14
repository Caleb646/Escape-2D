package gamePackage;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class TurretBullet extends DynamicSprite {

    private float ySpeed = 2.75f;
    private float xSpeed = 2.75f;
    private float maxYSpeed = 4.0f;
    private float maxXSpeed = 4.0f;
    private Vector2D target;

    //animation
    private Animater idleAnim = new Animater(GameAssets.turretBulletImg);

    //hitbox
    private Rectangle hitBox;

    public TurretBullet(GameHandler gameHandler, float x, float y, int w, int h, Vector2D target) {
        super(gameHandler, x, y, w, h);
        this.gameHandler.getSpriteManager().addSprite(this);//add sprite

        this.target = pos.normalize(target);
        idleAnim.setInterval(300);


        hitBox = new Rectangle();

        hitBox.width = spriteWidth/2;
        hitBox.height = spriteHeight/2;
    } 

    public void update() {

        velocity.addX(target.getX()*xSpeed);
        velocity.addY(target.getY()-0.01f*ySpeed);

        idleAnim.update();
        this.move();
    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(getCurrentAnimation(), (int) (pos.getX()-gameHandler.getCamera().getCameraX()),
        (int) (pos.getY()-gameHandler.getCamera().getCameraY()), spriteWidth, spriteHeight, null);
    }
    
    @Override
    public void move() {
        //setup hit box

        hitBox.x = (int) (pos.getX() - spriteWidth*0.5f);
        hitBox.y = (int) (pos.getY() - spriteHeight*0.5f);

        boolean bX = canMoveX(hitBox);
        boolean bY = canMoveY(hitBox);
        //System.out.println(pos.distance(target));
        if(!bX || !bY || pos.distance(target) <= 8) {
           setAlive(false);
           return;
        }
        if(bX) {
            pos.addX(velocity.getX());
           
        }
        if(bY) {
            pos.addY(velocity.getY());
        }

        if(velocity.getY() > maxYSpeed) {
            velocity.setY(maxYSpeed);
        }
        if(velocity.getY() < -maxYSpeed) {
            velocity.setY(-maxYSpeed);
        }
        if(velocity.getX() > maxXSpeed) {
            velocity.setX(maxXSpeed);
        }
        if(velocity.getX() < -maxXSpeed) {
            velocity.setX(-maxXSpeed);
        }
    }

    public BufferedImage getCurrentAnimation() {
        return idleAnim.getCurrentAnimation();
    }
}