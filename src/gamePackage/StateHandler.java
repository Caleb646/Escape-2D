package gamePackage;
import java.awt.Graphics2D;

public abstract class StateHandler {

    // all possible states
    private static GameState gameState;
    private static StartMenuState startMenuState;
    private static PauseState pauseState;
    
    private static StateHandler currentState;

    public abstract void update();
    public abstract void render(Graphics2D g2d);

    //getters and setters

    public static void setPauseState(PauseState pt) {
        pauseState = pt;
    }

    public static PauseState getPauseState() {
        return pauseState;
    }

    public static void setGameState(GameState gm) {
        gameState = gm;
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static void setStartMenuState(StartMenuState st) {
        startMenuState = st;
    }

    public static StartMenuState getStartMenuState() {
        return startMenuState;
    }

    public static void setCurrentState(StateHandler s) {
        currentState = s;
    }

    public static StateHandler getCurrentState() {
        return currentState;
    }
}