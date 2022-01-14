package gamePackage;
import java.awt.Graphics2D;

public class GameHandler {
    
    //game view
    private GameView game;

    //graphics context
    private Graphics2D g2d;

    //player
    private Player player;

    //path finder
    private PathFinder pathFinder;

    //camera
    private Camera camera;

    //handlers
    private WorldHandler worldHandler;
    private KeyInputHandler keyInputHandler;
    private MouseInputHandler mouseInputHandler;

    //managers
    private SpriteManager spriteManager;
    private ItemManager itemManager;

    public GameHandler(GameView game, KeyInputHandler keyInputHandler, MouseInputHandler m) {
        this.game = game;
        this.keyInputHandler = keyInputHandler;
        this.mouseInputHandler = m;
    }

    // getters and setters

    public void setItemManager(ItemManager it) {
        this.itemManager = it;
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

    public MouseInputHandler getMouseInputHandler() {
        return this.mouseInputHandler;
    }

    public void setG2D(Graphics2D g2d) {
        this.g2d = g2d;
    }

    public Graphics2D getG2D() {
        return this.g2d;
    }

    public void setSpriteManager(SpriteManager s) {
        this.spriteManager = s;
    }

    public SpriteManager getSpriteManager() {
        return this.spriteManager;
    }

    public void setPathFinder(PathFinder p) {
        pathFinder = p;
    }

    public PathFinder getPathFinder() {
        return this.pathFinder;
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setWorldHandler(WorldHandler h) {
        this.worldHandler = h;
    }

    public WorldHandler getWorldHandler() {
        return this.worldHandler;
    }

    public KeyInputHandler getKeyInputHandler() {
        return this.keyInputHandler;
    }

    public GameView getGameView() {
        return this.game;
    }

    public void setCamera(Camera c) {
        this.camera = c;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public int getGameWidth() {
        return this.game.getGameWidth();
    }

    public int getGameHeight() {
        return this.game.getGameHeight();
    }
}