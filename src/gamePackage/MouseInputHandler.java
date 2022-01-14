package gamePackage;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MouseInputHandler implements MouseListener {

    public boolean CLICK;
    public boolean PRESSED;

    private float lastPressX;
    private float lastPressY;

    public MouseInputHandler(){}

    @Override
    public void mouseClicked(MouseEvent e) {
        PRESSED = true;
        lastPressX = e.getX();
        lastPressY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("Mouse pressed");
        CLICK = true;
        lastPressX = e.getX();
        lastPressY = e.getY();
        //System.out.println(" x: "+lastPressX+" y: "+lastPressY);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CLICK = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public float getLastPressX() {
        return this.lastPressX;
    }

    public float getLastPressY() {
        return this.lastPressY;
    }
    
}