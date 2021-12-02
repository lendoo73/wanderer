package wanderer;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public class Playground {
    //field:
    Random rand;
    Graphics graphics;

    Tile[][] ground;
    int numOfWalls;

    public Playground(Graphics graphics) {
        this.rand = new Random();
        this.graphics = graphics;

        this.ground = new Tile[10][10];
        this.numOfWalls = 37;
    }

    public void drawGround() {
        Sand sand = new Sand();
        for (int row = 0; row < ground.length; row++) {
            for (int col = 0; col < ground[0].length; col++) {
                drawTile(new Sand(), row, col);
                ground[row][col] = new Sand();
            }
        }

        drawWalls();
    }

    private void drawTile(Tile tile, int row, int col) {
        PositionedImage image = new PositionedImage(
                tile.getImgPath(),
                row * Board.TILE_SIZE,
                col * Board.TILE_SIZE
        );
        image.draw(graphics);
        ground[row][col] = tile;
    }

    private void drawWalls() {
        while (true) {
            int[] startCoord = getRandCoord(0, 9);

            if (walidateWallStartCoord(startCoord)) {
                buildWall(startCoord);
                if (numOfWalls > 0) drawWalls();
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
        drawTile(new Wall(), row, col);
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
            drawTile(new Wall(), randNeighboursCoord[0], randNeighboursCoord[1]);
            occupied.add(randNeighboursCoord[0] * 10 + randNeighboursCoord[1]);
            row = randNeighboursCoord[0];
            col = randNeighboursCoord[1];
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

}
