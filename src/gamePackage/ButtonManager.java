package gamePackage;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class ButtonManager {

    private GameHandler gameHandler;

    private ArrayList<Button> currentButtons;

    public ButtonManager(GameHandler gm) {
        this.gameHandler = gm;
        currentButtons = new ArrayList<Button>();
    }

    public void update() {
        for(Button b : currentButtons) {
            b.update();
        }
    }

    public void render(Graphics2D g2d) {
        for(Button b : currentButtons) {
            b.render(g2d);
        }
    }

    public void addButton(Button btn) {
        this.currentButtons.add(btn);
    }
    
}