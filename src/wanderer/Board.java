package wanderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JComponent implements KeyListener {

    Graphics graphics;
    public static final int TILE_SIZE = 72;
    DrawGround drawGround;
    Characters characters;
    Hero hero;

    public Board() {

        // beállítja a rajztábla méretét
        setPreferredSize(new Dimension(720, 750));
        setVisible(true);

        drawGround = new DrawGround();
        this.characters = new Characters(drawGround.ground);
        characters.freePositions = drawGround.freePositions;
        drawGround.setCharacterList(characters.characterList);

        hero = new Hero();
        characters.addCharacter(hero);

        // create skeletons:
        int numOfSkeletons = 3;
        for (int i = 1 ; i <= numOfSkeletons; i ++) {
            characters.addCharacter(new Skeleton());
        }

        characters.addCharacter(new Boss());



    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        // Van egy 720 x 720-as rajztábla
        drawGround.graphics = graphics;
        drawGround.drawGround();
        // Az alábbi class-al készíthetsz és rajzolhatsz ki egy képet. pl.:

        // place all characters on the board:
        for (Character character : characters.characterList) {
            PositionedImage image = new PositionedImage(
                    character.getImgPath(),
                    character.getBoxX(),
                    character.getBoxY()
            );
            image.draw(graphics);
        }

        footer(graphics);


    }

    private void footer(Graphics graphics) {
        Font font = new Font("Arial", Font.BOLD, 20);
        graphics.setFont(font);
        String heroInfo = String.format(
                "Hero (Level %d) HP: %d/%d | DP: %d | SP: %d",
                hero.level,
                hero.hp,
                hero.maxHP,
                hero.dp,
                hero.sp
        );
        graphics.drawString(heroInfo, 5, 742);
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
        int x = 0, y = -1;
        // Mikor megnyomódik a lefele vagy felfele gomb, a négyzetünk pozíciója változik
        if (event.getKeyCode() == KeyEvent.VK_UP) {

        } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            x = 0;
            y = 1;
        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            x = 1;
            y = 0;
        } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            x = -1;
            y = 0;
        }
        hero.turn(x, y);
        characters.move(hero, x, y);
        characters.moveAll();

        // és újra rajzolódik az új koordinátákkal
        repaint();

    }

}

