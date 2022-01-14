package gamePackage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class PauseState extends StateHandler {

    private GameHandler gameHandler;

    private ButtonManager buttonManager;

    public PauseState(GameHandler gm) {
        this.gameHandler = gm;

        buttonManager = new ButtonManager(gameHandler);

        Button startButton = new Button(gameHandler, GameAssets.startBtn, gameHandler.getGameWidth()/2-64, 200,
         128, 64, new onClicked() {
             
            @Override
            public void clicked() {
                StateHandler.setCurrentState(StateHandler.getGameState()); 
            } 
        });

        Button saveBtn = new Button(gm, GameAssets.saveBtn, gameHandler.getGameWidth()/2-64, 300, 128, 64, new onClicked() {
            
            @Override
            public void clicked() {

                Player p = gameHandler.getPlayer();

                String data = ""+p.getX()+" "+p.getY()+" "+p.getScore()+" "+p.getHealth()+" "+p.getCurrentAmmo()+" "+WorldHandler.getCurrentLevel();

                Utility.writeToFile(Constants.playerSaveFname, data);
            }
        });

        Button mainMenuBtn = new Button(gm, GameAssets.mainMenuBtn, gameHandler.getGameWidth()/2-64, 400, 128, 64, new onClicked() {
            
            @Override
            public void clicked() {
                StateHandler.setCurrentState(StateHandler.getStartMenuState());
            }
        });

        buttonManager.addButton(startButton);
        buttonManager.addButton(saveBtn);
        buttonManager.addButton(mainMenuBtn);
    }

    @Override
    public void update() {
        buttonManager.update();
    }

    @Override
    public void render(Graphics2D g2d) {

        
        String line = "Game is Paused";
        int len = line.length();
        float strokeW = 4.0f;

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(strokeW));

        g2d.drawString(line, gameHandler.getGameWidth()/2-len*strokeW/2, 100);

        buttonManager.render(g2d);
    }
    
}