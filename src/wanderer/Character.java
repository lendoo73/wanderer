package wanderer;

public abstract class Character {
    // fields:
    private String imgPath;
    private int posX;
    private int posY;
    protected int level;
    protected int maxHP;
    protected int hp;
    protected int dp;
    protected int sp;

    // constructors:
    public Character(String imgPath) {
        this.imgPath = imgPath;
        this.posX = 99;
        this.posY = 99;
    }

    public Character(String imgPath, int posX, int posY) {
        this.imgPath = imgPath;
        this.posX = posX;
        this.posY = posY;
        this.level = 1;
    }

    // getters:
    public String getImgPath() {
        return imgPath;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    // setters:
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setPosX(int x) {
        if (x < 0 || x > 9) return;
        this.posX = x;
    }

    public void setPosY(int y) {
        if (y < 0 || y > 9) return;
        this.posY = y;
    }

    // methods:

    public int d6() {
        return PlayGround.getRandNum(1, 6);
    }

    protected void setPosition(int x, int y) {
        setPosX(x);
        setPosY(y);
    }

    public int getBoxX() {
        return this.getPosX() * Board.TILE_SIZE;
    }

    public int getBoxY() {
        return this.getPosY() * Board.TILE_SIZE;
    }

    protected void move(int x, int y) {
        setPosition(getPosX() + x, getPosY() + y);
    }

    private void die() {}

}
