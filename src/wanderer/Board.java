package wanderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JComponent implements KeyListener {

    Graphics graphics;
    public static final int TILE_SIZE = 72;
    Hero hero;
    Playground playground;

    public Board() {

        // beállítja a rajztábla méretét
        setPreferredSize(new Dimension(720, 720));
        setVisible(true);
        System.out.println("Start board");
        playground = new Playground();
        hero = new Hero();
        playground.characterList.add(hero);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        // Van egy 720 x 720-as rajztábla
        playground.graphics = graphics;
        playground.drawGround();
        // Az alábbi class-al készíthetsz és rajzolhatsz ki egy képet. pl.:

        PositionedImage image = new PositionedImage(
                hero.getImgPath(),
                hero.getHeroBoxX(),
                hero.getHeroBoxY()
        );
        image.draw(graphics);

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
            hero.move(0, -1);
        } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            hero.move(0, 1);
        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            hero.move(1, 0);
        } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            hero.move(-1, 0);
        }

        // és újra rajzolódik az új koordinátákkal
        repaint();

    }

}

