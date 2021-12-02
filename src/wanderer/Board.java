package wanderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JComponent implements KeyListener {

    int testBoxX;
    int testBoxY;
    public static final int TILE_SIZE = 72;

    public Board() {
        testBoxX = 0;
        testBoxY = 0;

        // beállítja a rajztábla méretét
        setPreferredSize(new Dimension(720, 720));
        setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        // Van egy 720 x 720-as rajztábla
        // Az alábbi class-al készíthetsz és rajzolhatsz ki egy képet. pl.:
        PositionedImage image = new PositionedImage("img/floor.png", testBoxX, testBoxY);

        Playground playground = new Playground(graphics);
        playground.drawGround();



        //image.draw(graphics);
    }

    public static void main(String[] args) {
        // Itt láthatod, hogy készíthetsz egy új ablakot, és hogyan adhatod hozzá a táblánkat (board).
        JFrame frame = new JFrame("RPG Game");
        Board board = new Board();
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        // Itt láthatod, hogy adsz hozzá gomb esemény figyelőt (key event listener)
        // A board object értesítődik ha valamelyik gomb lenyomásra kerül
        // és a rendszer meghívja az alábbi 3 függvény egyikét
        frame.addKeyListener(board);
        // Figyeld meg, (fent) hogy csak így tudjuk kivitelezni
        // mivel a Board class (a board object típusa) is egy KeyListener
    }
    // Hogy legyen egy KeyListenerünk, a classnak erre a 3 függvényre van szüksége.
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
    // De valójában csak ezt a függvényt használjuk a projekt során
    @Override
    public void keyReleased(KeyEvent event) {
        // Mikor megnyomódik a lefele vagy felfele gomb, a négyzetünk pozíciója változik
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            testBoxY -= TILE_SIZE;
        } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            testBoxY += TILE_SIZE;
        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            testBoxX += TILE_SIZE;
        } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            testBoxX -= TILE_SIZE;
        }
        // és újra rajzolódik az új koordinátákkal
        repaint();

    }

}

