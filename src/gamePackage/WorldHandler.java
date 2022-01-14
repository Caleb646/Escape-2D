package gamePackage;
import java.awt.Graphics2D;

public class WorldHandler {
    //0 open space, 1 floor
    private GameHandler gameHandler;
    private SpriteManager spriteManager;
    private ItemManager itemManager;

    private int gameWidth;//in units
    private int gameHeight;

    //levels
    public static String currentLvl;
    public static String[] levelArray;
    public static int maxLevels = 4;

    //button player pressed in start menu
    public static String btnPressedStartMenu;

    //all tiles
    private int[][] worldTiles;
    
    public WorldHandler(GameHandler handler) {

        this.gameHandler = handler;
        this.gameHandler.setWorldHandler(this);

        //managers
        spriteManager = new SpriteManager(gameHandler);
        itemManager = new ItemManager(gameHandler);

        determineLevel();//determine if there is saved data or not

        PathFinder pather = new PathFinder(gameHandler);//create AI pathing
        

        loadLevel();//load chosen level        
    }

    public void update() {
        spriteManager.update();
        itemManager.update();
    }

    public void render(Graphics2D g2d) {
        //checks to see where the player is in relation to the map and then only renders that area
        int xStart = (int) Math.max(0, gameHandler.getCamera().getCameraX() / Tiles.tileWidth);
        int xEnd = (int) Math.min(gameWidth, (gameHandler.getCamera().getCameraX() + gameHandler.getGameWidth()) / Tiles.tileWidth + 1);
        int yStart = (int) Math.max(0, gameHandler.getCamera().getCameraY() / Tiles.tileHeight);
        int yEnd = (int) Math.min(gameHeight, (gameHandler.getCamera().getCameraY() + gameHandler.getGameHeight()) / Tiles.tileHeight + 1);

        for(int y = yStart; y<yEnd; y++) {
            for(int x = xStart; x<xEnd; x++) {
                if(getTileType(x, y).getImage() == null)
                    continue;
                g2d.drawImage(getTileType(x, y).getImage(), (int) (x*Tiles.tileWidth-gameHandler.getCamera().getCameraX()),
                (int) (y*Tiles.tileWidth-gameHandler.getCamera().getCameraY()), Tiles.tileWidth, Tiles.tileHeight, null);
            }
        }
        spriteManager.render(g2d);
        itemManager.render(g2d);
    }

    public void createPlayer(float x, float y, int score, int health, int ammo) {

        Player p = new Player(gameHandler, x, y, 28, 28);
        spriteManager.addSprite(p);

        if(score != 0)
            p.setScore(score);
        if(health != 0)
            p.setHealth(health);
        if(ammo != 0)
            p.setCurrentAmmo(ammo);
    }

    public void determineLevel() {

        if(btnPressedStartMenu.equals(Constants.startBtnName)) {
            currentLvl = Constants.level1Fname;
            createPlayer(100, 100, 0, 0, 0);
        }
        else if(btnPressedStartMenu.equals(Constants.loadBtnName)) {

            boolean exists = Utility.fileExists(Constants.playerSaveFname);

            if(exists) {

                String[] playerData = Utility.loadFile(Constants.playerSaveFname).split("\\s+");
                float x = Float.parseFloat(playerData[0]);
                float y = Float.parseFloat(playerData[1]);
                int score = Integer.parseInt(playerData[2]);
                int health = Integer.parseInt(playerData[3]);
                int ammo = Integer.parseInt(playerData[4]);
                String level = playerData[5];

                createPlayer(x, y, score, health, ammo);
                currentLvl = level;
            }
            else {
                System.out.println("Save File Did Not Exist");
                currentLvl = Constants.level1Fname;
                createPlayer(500, 100, 0, 0, 0);
            }
        }
    }

    public void loadLevel() {

        String[] lvlInfo = Utility.loadFile(currentLvl).split("\\s+");
        gameWidth = Integer.parseInt(lvlInfo[0]);
        gameHeight = Integer.parseInt(lvlInfo[1]);
        worldTiles = new int[gameWidth][gameHeight];

        for(int y = 0; y<gameHeight; y++) {
            for(int x = 0; x<gameWidth; x++) {

                int tileId = Integer.parseInt(lvlInfo[(x+y*gameWidth)+2]);
                worldTiles[x][y] = tileId;

                switch(tileId) {//place none tiles entities

                case 3: {//striker enemy
                    Enemy e = new StrikerEnemy(gameHandler, x*Tiles.tileWidth, y*Tiles.tileHeight, 28, 28);
                    spriteManager.addSprite(e);
                    break;
                }
                case 5:{//ammo pickup
                    Item ammoPickUp = new AmmoPickUp(gameHandler, x*Tiles.tileWidth, y*Tiles.tileHeight, tileId);
                    itemManager.addItem(ammoPickUp);
                    break;
                }
                case 6: {//health pickup
                    Item healthPickUp = new HealthPickUp(gameHandler, x*Tiles.tileWidth, y*Tiles.tileHeight, tileId);
                    itemManager.addItem(healthPickUp);
                    break;
                }
                case 7: {//turret enemy
                    Enemy e = new TurretEnemy(gameHandler, x*Tiles.tileWidth, y*Tiles.tileHeight, 32, 32);
                    spriteManager.addSprite(e);
                    break;
                }
            }
        }     
    }
    this.gameHandler.getPathFinder().createNodes();  //creates node and edges graph 
    //this.gameHandler.getPathFinder().printGraph();
}

    //getters and setters

    public static String getBtnPressedStartMenu() {
        return btnPressedStartMenu;
    }

    public static void setBtnPressedStartMenu(String s) {
        btnPressedStartMenu = s;
    }

    public static String getCurrentLevel() {
        return currentLvl;
    }

    public void setCurrentLevel(String path) {
        currentLvl = path;
        loadLevel();
    }

    public Tiles getTileType(int x, int y) {
        if(x < 0 || y < 0 || x >= gameWidth || y >= gameHeight)
            return Tiles.blankTile;
        return Tiles.tileTypes[worldTiles[x][y]];
    }

    public int getGameWidth() {
        return this.gameWidth;
    }

    public int getGameHeight() {
        return this.gameHeight;
    }

}