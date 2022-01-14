package gamePackage;

import java.awt.Graphics2D;
import java.awt.Color;

public class StartMenuState extends StateHandler {

    private GameHandler gameHandler;

    private ButtonManager buttonManager;

    public StartMenuState(GameHandler gm) {
        this.gameHandler = gm;

        //setup button manager
        buttonManager = new ButtonManager(gameHandler);

        //setup start buttons
        Button startButton = new Button(gameHandler, GameAssets.startBtn, gameHandler.getGameWidth()/2-64, 100,
         128, 64, new onClicked() {
             
            @Override
            public void clicked() {
                WorldHandler.setBtnPressedStartMenu(Constants.startBtnName);
                StateHandler.setCurrentState(StateHandler.getGameState());
                StateHandler.getGameState().setup(); 
            } 
        });

        Button loadButton = new Button(gameHandler, GameAssets.loadBtn, gameHandler.getGameWidth()/2-64, 205,
         128, 64, new onClicked() {
             
            @Override
            public void clicked() {
                WorldHandler.setBtnPressedStartMenu(Constants.loadBtnName);
                StateHandler.setCurrentState(StateHandler.getGameState());
                StateHandler.getGameState().setup(); 
            } 
        });

        //add buttons to button manager
        buttonManager.addButton(startButton);
        buttonManager.addButton(loadButton);


    }

    @Override
    public void update() {
        buttonManager.update();

    }

    @Override
    public void render(Graphics2D g2d) {
        buttonManager.render(g2d);

        String title = "Tutorial";

        String body = "Use WASD to move and Spacebar to jump. Click anywhere to throw your axe. Press P to pause the game.";
        
        String body2 = "Pressing Start will start a new game. Pressing Load will load a previously saved game.";

        String body3 = "Get to the white flag to advance to a new level";

        g2d.setColor(Color.WHITE);
        g2d.drawString(title, gameHandler.getGameWidth()/2.5f, 310);
        g2d.drawString(body, gameHandler.getGameWidth()/10, 345);
        g2d.drawString(body2, gameHandler.getGameWidth()/10, 365);
        g2d.drawString(body3, gameHandler.getGameWidth()/10, 385);
    }
    
}