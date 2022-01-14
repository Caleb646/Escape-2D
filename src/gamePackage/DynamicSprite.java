package gamePackage;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class DynamicSprite extends Sprite {

    protected Vector2D velocity;
    protected float xSpeed = 4.0f;
    protected int maxSpeed = 10;
    protected float jumpHeight = 15.0f;

    //attack timing
    protected long currentAttackTime = System.currentTimeMillis();
    protected long lastAttackTime = System.currentTimeMillis();
    protected long deltaAttackTime = 0l;
    protected int attackInterval = 4000;
    protected boolean attacking = false;

    public DynamicSprite(GameHandler gameHandler, float x, float y, int w, int h) {
        super(gameHandler, x, y, w, h);
        velocity = new Vector2D(0.0f, 0.0f);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2d) {

    }

    public void win() {
        String currentLvl = WorldHandler.currentLvl.substring(0,1);
        int lvlNum = Integer.parseInt(currentLvl);
        if(lvlNum < Constants.maxNumLvls) {

            //clear all prexisting sprites
            gameHandler.getSpriteManager().clearAllSprites();
            gameHandler.getItemManager().clearAllItems();

            //add player back
            Player p = gameHandler.getPlayer();
            p.setPos(new Vector2D(100f, 100f));//reset player to starting pos
            gameHandler.getSpriteManager().addSprite(p);

            //increment chosen lvl by one
            lvlNum += 1;
            String lvlFname = ""+lvlNum+".txt";
            gameHandler.getWorldHandler().setCurrentLevel(lvlFname);
            
        }

        else {
            System.out.println("You have beaten the game!!");
            StateHandler.setCurrentState(StateHandler.getStartMenuState());//return to start menu
        }
    }

    public boolean canAttack() {
        currentAttackTime = System.currentTimeMillis();
        deltaAttackTime += currentAttackTime - lastAttackTime;

        if(deltaAttackTime > attackInterval) {
            //System.out.println("Attacking: " + deltaAttackTime );
            setAttacking(true);
            lastAttackTime = currentAttackTime;
            deltaAttackTime = 0;
            return true;
        }
        else {
            setAttacking(false);
            return false;
        }
    }

    public boolean onGround() {

        return tileCollision((int) ((pos.getX()+ spriteWidth / 2) /Tiles.tileWidth), (int) (pos.getY()+spriteHeight+5)/Tiles.tileHeight);
    }

    public void jump() {
        if(onGround()) {
            velocity.addY(-jumpHeight);
        }
    }

    public void move() {

        if(canMoveX()) {
            pos.addX(velocity.getX());
        }
        if(canMoveY()) {
            pos.addY(velocity.getY());
        }

        if(velocity.getY() > maxSpeed) {
            velocity.setY(maxSpeed);
        }
    }

    //hitbox version
    public boolean canMoveX(Rectangle hitBox) {
        if(velocity.getX() > 0) {//running right
            if(!tileCollision((int) (hitBox.x + hitBox.width + velocity.getX()) / Tiles.tileWidth, (int) hitBox.y  / Tiles.tileHeight) && //top right
            !tileCollision((int) (hitBox.x  + hitBox.width + velocity.getX()) / Tiles.tileWidth, (int) (hitBox.y  + hitBox.height) / Tiles.tileHeight))//bottom right
                return true;
        }
        else if(velocity.getX() < 0) {//running left
            if(!tileCollision((int) (hitBox.x  + velocity.getX()) / Tiles.tileWidth, (int) hitBox.y  / Tiles.tileHeight) && //top left
            !tileCollision((int) (hitBox.x  + velocity.getX()) / Tiles.tileWidth, (int) (hitBox.y  + hitBox.height) / Tiles.tileHeight))//bottom left
                return true;
        }
        return false;
    }

    //hitbox version
    public boolean canMoveY(Rectangle hitBox) {
        if(velocity.getY() < 0) {//running up
            if(!tileCollision((int) (hitBox.x ) / Tiles.tileWidth, (int) (hitBox.y  + velocity.getY()) / Tiles.tileHeight) && //top left
            !tileCollision((int) (hitBox.x  + hitBox.width) / Tiles.tileWidth, (int) (hitBox.y  + velocity.getY()) / Tiles.tileHeight))//top right
                return true;
        }
        else if(velocity.getY() > 0) {//running down
            if(!tileCollision((int) (hitBox.x ) / Tiles.tileWidth, (int) (hitBox.y  + velocity.getY() + hitBox.height) / Tiles.tileHeight) && //bottom left
            !tileCollision((int) (hitBox.x  + hitBox.width) / Tiles.tileWidth, (int) (hitBox.y  + velocity.getY() + hitBox.height) / Tiles.tileHeight))//bottom right
                return true;
        }
        velocity.setY(0);
        return false;
    }


    public boolean canMoveX() {
        if(velocity.getX() > 0) {//running right
            if(!tileCollision((int) (pos.getX() + spriteWidth + velocity.getX()) / Tiles.tileWidth, (int) pos.getY() / Tiles.tileHeight) && //top right
            !tileCollision((int) (pos.getX() + spriteWidth + velocity.getX()) / Tiles.tileWidth, (int) (pos.getY() + spriteHeight) / Tiles.tileHeight))//bottom right
                return true;
        }
        else if(velocity.getX() < 0) {//running left
            if(!tileCollision((int) (pos.getX() + velocity.getX()) / Tiles.tileWidth, (int) pos.getY() / Tiles.tileHeight) && //top left
            !tileCollision((int) (pos.getX() + velocity.getX()) / Tiles.tileWidth, (int) (pos.getY() + spriteHeight) / Tiles.tileHeight))//bottom left
                return true;
        }
        return false;
    }

    public boolean canMoveY() {
        if(velocity.getY() < 0) {//running up
            if(!tileCollision((int) (pos.getX()) / Tiles.tileWidth, (int) (pos.getY() + velocity.getY()) / Tiles.tileHeight) && //top left
            !tileCollision((int) (pos.getX() + spriteWidth) / Tiles.tileWidth, (int) (pos.getY() + velocity.getY()) / Tiles.tileHeight))//top right
                return true;
        }
        else if(velocity.getY() > 0) {//running down
            if(!tileCollision((int) (pos.getX()) / Tiles.tileWidth, (int) (pos.getY() + velocity.getY() + spriteHeight) / Tiles.tileHeight) && //bottom left
            !tileCollision((int) (pos.getX() + spriteWidth) / Tiles.tileWidth, (int) (pos.getY() + velocity.getY() + spriteHeight) / Tiles.tileHeight))//bottom right
                return true;
        }
        velocity.setY(0);
        return false;
    }

    public boolean tileCollision(int x, int y) {

        Tiles tile = this.gameHandler.getWorldHandler().getTileType(x, y);

        if(tile.isSolid()) {

                if(this instanceof Player && tile.isDamaging() && tile.canDamage()) {//checks for special tiles
                        this.hurt();                             
                        return true;
                    }

                else {

                    return true;
                }

            }

        else if(tile.isFlag()) {

            this.win();

            return false;

        }

        return false;
    }

    //getters and setters

    public float getXspeed() {
        return this.xSpeed;
    }

    public float getJumpHeight() {
        return this.jumpHeight;
    }

    public Vector2D getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector2D v) {
        this.velocity = v;
    }

    public void setAttacking(boolean b) {
        this.attacking = b;
    }
    
}