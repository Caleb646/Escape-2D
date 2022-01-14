package gamePackage;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class PlayerAxe extends DynamicSprite {

    private float ySpeed = 3.75f;
    private float xSpeed = 3.75f;
    private float maxYSpeed = 5.0f;
    private float maxXSpeed = 5.0f;
    private Vector2D target;

    //hitbox
    private Rectangle hitBox;

    //animations
    private Animater aRight = new Animater(GameAssets.pAxeRight);
    private Animater aLeft = new Animater(GameAssets.pAxeLeft);

    public PlayerAxe(GameHandler gameHandler, float x, float y, int w, int h, Vector2D target) {
        super(gameHandler, x, y, w, h);
        this.target = pos.normalize(target);//normalize vector
        aRight.setInterval(100);
        aLeft.setInterval(100);//set animation interval   
        gameHandler.getSpriteManager().addSprite(this); //add this so it can be drawn/rendered
        
        //partially setup hitbox. rest are in move method
        hitBox = new Rectangle();

        hitBox.width = spriteWidth/2;
        hitBox.height = spriteHeight/2;
    }

    public void update() {

        velocity.addX(target.getX()*xSpeed);
        velocity.addY(target.getY()-0.01f*ySpeed);

        aRight.update();
        aLeft.update();

        this.move();
    }

    public void render(Graphics2D g2d) {


        g2d.drawImage(getAnimation(), (int) (pos.getX()-gameHandler.getCamera().getCameraX()),
        (int) (pos.getY()-gameHandler.getCamera().getCameraY()), spriteWidth, spriteHeight, null);

        //g2d.fillRect((int) (pos.getX()-gameHandler.getCamera().getCameraX()),(int) (pos.getY()-gameHandler.getCamera().getCameraY()), spriteWidth, spriteHeight);
    }

    public BufferedImage getAnimation() {

        if(velocity.getX() > 0)
            return aRight.getCurrentAnimation();
        else if(velocity.getX() < 0)
            return aLeft.getCurrentAnimation();
        return aLeft.getCurrentAnimation();
    }

    @Override
    public void move() {
        //setup hit box

        hitBox.x = (int) (pos.getX() - spriteWidth*0.5f);
        hitBox.y = (int) (pos.getY() - spriteHeight*0.7f);

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
    
}