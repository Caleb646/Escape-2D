package gamePackage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

public class Screen extends JFrame {

    private static final long serialVersionUID = 1L;
    private Board board;
    
    public Screen(String title, int width, int height) {
        super(title);
        setup(width, height);
    }

    private void setup(int width, int height) {
        board = new Board();
        board.setPreferredSize(new Dimension(width, height));
        board.setMaximumSize(new Dimension(width, height));
        board.setMinimumSize(new Dimension(width, height));
        board.setFocusable(false);
        this.add(board);
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        
    }

    public Board getBoard() {
        return this.board;
    }
}

class Board extends JPanel {

    private static final long serialVersionUID = 2L;
    
    public Board() {
        //this.setDoubleBuffered(true);
        this.setIgnoreRepaint(true);
    }
}