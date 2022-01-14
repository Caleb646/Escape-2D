package gamePackage;
import java.awt.Graphics2D;

public class GameState extends StateHandler {

    private GameHandler gameHandler;
    private WorldHandler worldHandler;

    public GameState(GameHandler handler) {
        this.gameHandler = handler;
        //this.worldHandler = new WorldHandler(gameHandler);
    }

    @Override
    public void update() {
        worldHandler.update();
    }

    @Override
    public void render(Graphics2D g2d) {
        worldHandler.render(g2d);
    }

    public void setup() {
        this.worldHandler = new WorldHandler(gameHandler);
    }
    
}