package wanderer;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Playground {
    //field:
    Random rand;
    Graphics graphics;

    Tile[][] ground;
    int numOfWalls;
    List<Character> characterList;

    public Playground() {
        this.rand = new Random();
        this.ground = new Tile[10][10];
        this.numOfWalls = 37;
        this.characterList = new ArrayList<>();
    }

    public void drawGround() {
        printGround();
        if (ground[0][0] == null) createGround();
        for (int row = 0; row < ground.length; row++) {
            for (int col = 0; col < ground[0].length; col++) {
                drawTile(ground[row][col], row, col);
            }
        }
    }

    public void createGround() {
        Sand sand = new Sand();
        for (int row = 0; row < ground.length; row++) {
            for (int col = 0; col < ground[0].length; col++) {
                ground[row][col] = new Sand();
            }
        }

        createWalls();
    }

    private void drawTile(Tile tile, int row, int col) {
        PositionedImage image = new PositionedImage(
                tile.getImgPath(),
                col * Board.TILE_SIZE,
                row * Board.TILE_SIZE
        );
        image.draw(graphics);
    }

    private void createWalls() {
        while (true) {
            int[] startCoord = getRandCoord(0, 9);

            if (walidateWallStartCoord(startCoord)) {
                buildWall(startCoord);
                if (numOfWalls > 0) createWalls();
                break;
            }
        }
    }

    private void buildWall(int[] startCoord) {
        // copy the ground matrix:
        Tile[][] copyGround = deepCopyGround();
        HashSet<Integer> occupied = new HashSet<>();

        int row = startCoord[0];
        int col = startCoord[1];
        int sizeOfCurrentWallBlock = getRandNum(2, 9);
        numOfWalls -= sizeOfCurrentWallBlock;
        int counter = 0;
        while (--sizeOfCurrentWallBlock > 0) {
            counter++;
            if (counter > 150) {
                break;
            }

            int[] randNeighboursCoord = getRandomDirection(row, col);
            // avoid insula
            if (isInsula(startCoord[0], startCoord[1], randNeighboursCoord[0], randNeighboursCoord[1])) {
                sizeOfCurrentWallBlock++;
                continue;
            }
            // no insula, check if there is another existed wall:
            if ( !walidateWallStartCoord(randNeighboursCoord, copyGround) ) {
                sizeOfCurrentWallBlock++;
                continue;
            }

            if (occupied.contains(randNeighboursCoord[0] * 10 + randNeighboursCoord[1])) {
                sizeOfCurrentWallBlock++;
                continue;
            }

            // random position valid, place the next part of the wall:
            occupied.add(randNeighboursCoord[0] * 10 + randNeighboursCoord[1]);
            row = randNeighboursCoord[0];
            col = randNeighboursCoord[1];
            ground[row][col] = new Wall();
        }
    }

    private boolean isInsula(int startRow, int startCol, int plannedRow, int plannedCol) {
        if (startCol == 0 && plannedRow == 0) return true; // top-left insula
        if (startCol == 9 && plannedRow == 0) return true; // top-right insula
        if (startCol == 9 && plannedRow == 9) return true; // bottom-right insula
        if (startCol == 0 && plannedRow == 9) return true; // bottom-left insula

        return false;
    }

    private int[] getRandomDirection(int row, int col) {
        int startRow = row;
        int startCol = col;
        int randDirection = getRandNum(1, 4);
        switch (randDirection) {
            case 1: // left
                row--;
                break;
            case 2: // right
                row++;
                break;
            case 3: // up
                col--;
                break;
            case 4: // bottom
                col++;
                break;
        }

        if (matrixOutOfRange(row, col)) {
            getRandomDirection(startRow, startCol);
        }

        return new int[]{row, col};
    }

    private boolean matrixOutOfRange(int row,  int col) {
        if (row < 0) return true;
        if (col < 0) return true;
        if (row > 9) return true;
        if (col > 9) return true;
        return false;
    }

    private Tile[][] deepCopyGround() {
        Tile[][] copyGround = new Tile[10][10];
        for (int row = 0; row < ground.length; row++) {
            copyGround[row] = ground[row].clone();
        }
        return copyGround;
    }

    private int[] getRandCoord(int from, int to) {
        int randX = getRandNum(from, to);
        int randY = getRandNum(from, to);
        return new int[]{randX, randY};
    }

    private int getRandNum(int from, int to) {
        return rand.nextInt(to + 1 - from) + from;
    }

    private boolean walidateWallStartCoord(int[] startCoord) {
        return walidateWallStartCoord(startCoord, this.ground);
    }

    private boolean walidateWallStartCoord(int[] startCoord, Tile[][] ground) {
        int x = startCoord[0];
        int y = startCoord[1];
        if (x <= 0 && y <= 0) return false;  // hero starting position
        if (matrixOutOfRange(x, y)) return false;
        if ( !ground[x][y].isPermeable() ) return false;  // there is another wall

        // check neighbours area:
        for (int row = -1; row < 2; row++) {
            int currentX = x + row;
            if (currentX < 0) currentX = 0;
            if (currentX > 9) currentX = 9;

            // to avoid indexOut
            for (int col  = -1; col < 2; col++) {
                int currentY = y + col;
                if (currentY < 0) currentY = 0;
                if (currentY > 9) currentY = 9;
                if ( !ground[currentX][currentY].isPermeable() ) return false;
            }
        }

        return true;
    }

    public boolean isWall(int x, int y) {
        if (matrixOutOfRange(x, y)) return false;
        printGround();
        System.out.println(x + " " + y + " " + ground[x][y]);
        //return ground[x][y] instanceof Wall;
        return false;
    }

    private void printGround() {
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
