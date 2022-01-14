package gamePackage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JLabel;

public class GameView {
    //jpanel
    private Screen screen;

    //handlers
    private GameHandler gameHandler;
    private WorldHandler worldHandler;
    private KeyInputHandler keyInputHandler;
    private MouseInputHandler mouseInputHandler;

    private Camera camera;

    //states
    private GameState gameState;
    private StartMenuState startMenuState;
    private PauseState pauseState;
    
    private String title;
    private int width;
    private int height;

    private Image offScreenBuffer;

    public GameView(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        run();
    }

    public void init() {

        this.screen = new Screen(title, width, height);

        keyInputHandler = new KeyInputHandler();
        this.screen.addKeyListener(keyInputHandler);

        mouseInputHandler = new MouseInputHandler();
        this.screen.addMouseListener(mouseInputHandler);

        this.offScreenBuffer = screen.createImage(width, height);


        this.gameHandler = new GameHandler(this, keyInputHandler, mouseInputHandler);
        camera = new Camera(this.gameHandler, 0, 0);

        //load sprite sheets
        GameAssets.loadAssets();

        //setup different possible states
        gameState = new GameState(gameHandler);
        startMenuState = new StartMenuState(gameHandler);
        pauseState = new PauseState(gameHandler);

        //add states to StateHandler
        StateHandler.setGameState(gameState);
        StateHandler.setStartMenuState(startMenuState);
        StateHandler.setPauseState(pauseState);

        //set current state
        StateHandler.setCurrentState(startMenuState);     
    }

    private void update() {

        if(StateHandler.getCurrentState() != null) {
            StateHandler.getCurrentState().update();
        }

    }

    private void render() {

        Graphics2D currentScreen = (Graphics2D) screen.getBoard().getGraphics();
        Graphics2D g2d = (Graphics2D) offScreenBuffer.getGraphics();

        gameHandler.setG2D(g2d);
        
        g2d.setBackground(Color.BLUE);
        g2d.clearRect(0, 0, width, height);
        

        if(StateHandler.getCurrentState() != null) {
            StateHandler.getCurrentState().render(g2d);
        }
        currentScreen.drawImage(offScreenBuffer, 0, 0, width, height, null);  
        g2d.dispose();

    }

    private void run() {
        init();

        int fps = 60;
        double timePerTick = 1_000_000_000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(true) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1) {
                update();
                render();
                ticks++;
                delta--;
            }
            if(timer >= 1_000_000_000) {
                //System.out.println("Frames per second: "+ticks);
                ticks = 0;
                timer = 0;
            }
        }
    }

    //getters and setters

    public int getGameWidth() {
        return this.width;
    }

    public int getGameHeight() {
        return this.height;
    }
}