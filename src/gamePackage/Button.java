package gamePackage;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

interface onClicked {

    public void clicked();
}

public class Button {

    protected GameHandler gameHandler;

    protected BufferedImage image;

    protected int x, y, w, h;

    protected onClicked clicked;

    public Button(GameHandler gm, BufferedImage image, int x, int y, int w, int h, onClicked clicked) {

        this.gameHandler = gm;
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.clicked = clicked;
    }
    
    public void update() {

        MouseInputHandler m = this.gameHandler.getMouseInputHandler();

        if(m.PRESSED && buttonClicked((int)m.getLastPressX(), (int)m.getLastPressY())) {
            m.PRESSED = false; //reset pressed bool
            clicked.clicked();
        }

    }

    public void render(Graphics2D g2d) {

        g2d.drawImage(image, x, y, w, h, null);
        //g2d.drawRect(x, y, w, h);
    }

    public boolean buttonClicked(int mX, int mY) {
        if(mX >= x && mX <= x+w && mY >= y && mY <= y+h) {
            System.out.println("Clicked");
            return true;
        }
        return false;
    }
}