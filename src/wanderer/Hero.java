package wanderer;

public class Hero extends Character {

    public Hero() {
        super("img/hero-down.png", 0, 0);
    }

    public int getHeroBoxX() {
        return this.getPosX() * Board.TILE_SIZE;
    }

    public int getHeroBoxY() {
        return this.getPosY() * Board.TILE_SIZE;
    }

    // methods
    @Override
    public void move(int x, int y) {
        String direction = x == -1
                ? "left"
                : x == 1
                ? "right"
                : y == 1
                ? "down"
                : "up";
        this.setImgPath(String.format("img/hero-%s.png", direction));
        super.move(x, y);
    }

}
