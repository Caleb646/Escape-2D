package gamePackage;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Item {

    //all item amounts
    public final int ammoAmount = 3, healthAmount = 5;

    //all item ids
    public final int ammoId = 5, healthId = 6;

    protected GameHandler gameHandler;

    protected final int itemWidth = 32, itemHeight = 32;

    protected final int id;//5 ammo, 6 health

    protected final int pickUpRange;

    protected Vector2D pos;

    protected boolean pickedUp = false;
    
    public Item(GameHandler gm, float x, float y, int id) {

        this.gameHandler = gm;
        this.id = id;
        this.pickUpRange = 10;
        this.pos = new Vector2D(x, y);
    }

    public void update() {
        
    }

    public void render(Graphics2D g2d) {

    }

    public void playerClose() {
        Player p = this.gameHandler.getPlayer();
        if(this.pos.distance(p.getPos()) < pickUpRange) {
            setPickedUp(true);//Item manager will delete it
            addItemToPlayer(getId());
        }
    }

    public void addItemToPlayer(int id) {

        Player p = this.gameHandler.getPlayer();

        switch(id) {

            case ammoId: {
                p.addtoCurrentAmmo(ammoAmount);
                break;
            }
            case healthId: {
                p.addHealth(healthAmount);
                break;
            }
        }
    }

    //getters and setters

    public int getId() {
        return this.id;
    }

    public void setPickedUp(boolean b) {
        this.pickedUp = b;
    }

    public boolean isPickedUp() {
        return this.pickedUp;
    }

    public float getX() {
        return this.pos.getX();
    }

    public float getY() {
        return this.pos.getY();
    }
}

class AmmoPickUp extends Item {

	public AmmoPickUp(GameHandler gm, int x, int y, int id) {
        super(gm, x, y, id);
    }
    
    public void update() {
        playerClose();
    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(GameAssets.pAxe, (int) (getX()-this.gameHandler.getCamera().getCameraX()), (int) (getY()-this.gameHandler.getCamera().getCameraY()), itemWidth, itemHeight, null);
    }
}

class HealthPickUp extends Item {

    private Animater idleA = new Animater(GameAssets.healthItem); 

	public HealthPickUp(GameHandler gm, int x, int y, int id) {
        super(gm, x, y, id);
        idleA.setInterval(700);
    }
    
    public void update() {
        idleA.update();
        playerClose();
    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(getCurrentAnimation(), (int) (getX()-this.gameHandler.getCamera().getCameraX()), (int) (getY()-this.gameHandler.getCamera().getCameraY()), itemWidth, itemHeight, null);
    }

    public BufferedImage getCurrentAnimation() {
        //System.out.println("Animating");
        return idleA.getCurrentAnimation();
    }
}