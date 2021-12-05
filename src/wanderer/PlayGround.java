package wanderer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public abstract class PlayGround {
    // fields:
    static Random rand;

    Tile[][] ground;
    List<Character> characterList;
    HashSet<Integer> freePositions;

    // constructors:
    public PlayGround(Tile[][] ground) {
        this.rand = new Random();
        this.characterList = new ArrayList<>();
        this.ground = ground;
        calcualteFreePositions();
    }

    // setters:
    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    // methods:
    private void calcualteFreePositions() {
        freePositions = new HashSet<>();
        for (int i = 0; i < ground.length * ground[0].length; i++) {
            freePositions.add(i);
        }
    }

    protected void removeFromFreePosition (int x, int y) {
        freePositions.remove(getCoord(x, y));
    }

    protected void addToFreePosition(int x, int y) {
        freePositions.add(getCoord(x, y));
    }

    protected int[] getRandomPosition() {
        int length = freePositions.size();
        int randIndex = getRandNum(length - 1);
        int index = 0;
        int randomCoord = 99;
        for (Integer coord : freePositions) {
            if (index == randIndex) {
                randomCoord = coord;
            }
            index++;
        }
        return parsePosition(randomCoord);
    }

    private int[] parsePosition(int coord) {
        int x = coord % 10;
        int y = (coord - x) / 10;
        return new int[]{x, y};
    }

    public int getRandNum(int to) {
        return getRandNum(0, to);
    }

    public static int getRandNum(int from, int to) {
        return rand.nextInt(to + 1 - from) + from;
    }

    private int getCoord(int x, int y) {
        return y * 10 + x;
    }

    public boolean isFree(int x, int y) {
        return !(freePositions.contains(getCoord(x, y)));
    }

    protected boolean matrixOutOfRange(int row,  int col) {
        if (row < 0) return true;
        if (col < 0) return true;
        if (row > 9) return true;
        if (col > 9) return true;
        return false;
    }

    protected void printGround() {
        for (int i = 0; i < ground[0].length; i++) {
            for (int j = 0; j < ground[0].length; j++) {
                if (ground[i][j] instanceof Wall) {
                    System.out.print(" " + "W");
                } else System.out.print(" " + "_");
            }
            System.out.println();
        }
    }
}
