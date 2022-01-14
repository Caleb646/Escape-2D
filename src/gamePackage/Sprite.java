package gamePackage;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Sprite {
    protected Vector2D pos;
    protected GameHandler gameHandler;

    protected int spriteWidth;
    protected int spriteHeight;

    protected int maxHealth = 10;
    protected int health = 2;
    protected boolean alive = true;

    protected boolean hit = false;//was the sprite hit with an attack?

    protected Vector2D gravity = new Vector2D(0, 1.0f);

    //required methods
    public abstract void update();
    public abstract void render(Graphics2D g2d);

    public Sprite(GameHandler gameHandler, float x, float y, int w, int h) {
        this.gameHandler = gameHandler;
        this.spriteWidth = w;
        this.spriteHeight = h;
        pos = new Vector2D(x, y);
    }

    public void checkSpriteCollision(ArrayList<Sprite> sL) {
        //right now this is only used for the players axe
        for(Sprite s : sL) {
            if(this.pos.distance(s.pos) < 5 && s instanceof Enemy && this instanceof PlayerAxe) {
                this.setAlive(false);//kill the axe
                s.hurt();//hurt the enemy
                if(s.getHealth() < 1) {//sprite is dead
                    this.gameHandler.getPlayer().addScore(1);
                }
            }
            else if(this instanceof TurretBullet && s instanceof Player && this.pos.distance(s.pos) < 5) {
                s.hurt();
            }
        }
    }

    public void hurt() {
        takeHealth(1);
        setHit(true);
        if(health < 1)
            die();
    }

    public void die() {
        setAlive(false);
        if(this instanceof Player) {
            //reset the state to the start menu
            StateHandler.setCurrentState(StateHandler.getStartMenuState());
            System.out.println("You died!!!!");
        }
    }

    public void addHealth(int amt) {
        if(this.health+amt <= this.maxHealth)
            this.health += amt;
    }

    public void takeHealth(int amt) {
        this.health -= amt;
    }

    //getters and setters

    public void setHealth(int amt) {
        this.health = amt;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int amt) {
        this.maxHealth = amt;
    }

    public boolean isHit() {
        return this.hit;
    }

    public void setHit(boolean b) {
        this.hit = b;
    }

    public Vector2D getPos() {
        return this.pos;
    }

    public float getX() {
        return this.pos.getX();
    }

    public float getY() {
        return this.pos.getY();
    }

    public int getWidth() {
        return this.spriteWidth;
    }

    public int getHeight() {
        return this.spriteHeight;
    }

    public void setPos(Vector2D v) {
        this.pos = v;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean b) {
        alive = false;
    }


}