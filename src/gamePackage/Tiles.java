package gamePackage;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Tiles {
    //1 floor tile, 0 blank tile
    public static int tileWidth, tileHeight;

    public static Tiles[] tileTypes = new Tiles[10];
    public static Tiles blankTile = new BlankTile(0, 32, 32);
    public static Tiles floorTile = new FloorTile(1, 32, 32);
    public static Tiles spikeTile = new SpikeTile(2, 32, 32);
    public static Tiles strikerEnemyTile = new StrikerEnemyTile(3, 32, 32);//placeholder so the world handler knows where to draw the enemies
    public static Tiles flagTile = new FlagTile(4, 32, 32);  
    public static Tiles axeAmmo = new AxeAmmoTile(5, 32, 32);//placeholder
    public static Tiles healthPowerUp = new HealthTile(6, 32, 32);//placeholder
    public static Tiles turretEnemyTile = new TurretEnemyTile(7, 32, 32);

    protected BufferedImage image;
    protected final int id;

    protected long currentAttackTime = System.currentTimeMillis();
    protected long lastAttackTime = System.currentTimeMillis();
    protected long deltaAttackTime = 0l;
    protected int attackInterval = 4000;

    public Tiles(BufferedImage img, int id, int tW, int tH) {
        this.id = id;
        this.image = img;
        tileWidth = tW;
        tileHeight = tH;
        tileTypes[id] = this;
    }

    public void update() {

    }

    public void render(Graphics2D g2d) {

    }

    public boolean isFlag() {
        return false;
    }

    public boolean canDamage() {
        return false;
    }

    //getters and setters

    public boolean isDamaging() {
        return false;
    }

    public static boolean isSolid(int id) {
        if(id == 0)
            return false;
        else {
            return true;
        }
    }

    public boolean isSolid() {
        return false;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public int getId() {
        return this.id;
    }
    
}

class FloorTile extends Tiles {

    public FloorTile(int id, int tW, int tH) {
        super(GameAssets.floorImg, id, tW, tH);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
}

class BlankTile extends Tiles {

    public BlankTile(int id, int tW, int tH) {
        super(null, id, tW, tH);
    }
}

class StrikerEnemyTile extends Tiles {

    public StrikerEnemyTile(int id, int tW, int tH) {
        super(null, id, tW, tH);
    }
}

class TurretEnemyTile extends Tiles {

    public TurretEnemyTile(int id, int tW, int tH) {
        super(null, id, tW, tH);
    }
    
}

class SpikeTile extends Tiles {

    public SpikeTile(int id, int tW, int tH) {
        super(GameAssets.spikeImg, id, tW, tH);

        attackInterval = 40_000;
    }

    @Override
    public boolean canDamage() {

        currentAttackTime = System.currentTimeMillis();
        deltaAttackTime += currentAttackTime - lastAttackTime;

        if(deltaAttackTime > attackInterval) {
            //System.out.println("Attacking: " + deltaAttackTime );
            lastAttackTime = currentAttackTime;
            deltaAttackTime = 0;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isDamaging() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}

class FlagTile extends Tiles {

    public FlagTile(int id, int tW, int tH) {
        super(GameAssets.flagImg, id, tW, tH);
    }

    @Override
    public boolean isFlag() {
        return true;
    }
}

class AxeAmmoTile extends Tiles {

    public AxeAmmoTile(int id, int tW, int tH) {
        super(null, id, tW, tH);
    }
}

class HealthTile extends Tiles {

    public HealthTile(int id, int tW, int tH) {
        super(null, id, tW, tH);
    }
}