package gamePackage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputHandler implements KeyListener {

    public boolean UP, DOWN, RIGHT, LEFT, GROUND, SPACE, SHOOT, PAUSE;

    public KeyInputHandler() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_SPACE: 
                SPACE = true;
                break;
            case KeyEvent.VK_A: 
                LEFT = true;
                break;
            case KeyEvent.VK_D: 
                RIGHT = true;
                break;

            case KeyEvent.VK_S: 
                DOWN = true;
                break;
            case KeyEvent.VK_P: 
                PAUSE = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_SPACE: 
                SPACE = false;
                break;
            case KeyEvent.VK_A: 
                LEFT = false;
                break;
            case KeyEvent.VK_D: 
                RIGHT = false;
                break;

            case KeyEvent.VK_S: 
                DOWN = false;
                break;
            case KeyEvent.VK_P: 
                PAUSE = false;
                break;
        }
    }

    public void setSpace(boolean b) {
        SPACE = b;
    }
}