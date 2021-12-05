package wanderer;

public abstract class Character {
    // fields:
    private String name;
    private String imgPath;
    private int posX;
    private int posY;
    protected int level;
    protected int maxHP;
    protected int hp;
    protected int dp;
    protected int sp;

    // constructors:
    public Character(String name, String imgPath) {
        this(name, imgPath, 99, 99);
    }

    public Character(String name, String imgPath, int posX, int posY) {
        this.name = name;
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
    private int getSv() {
        return sp + d6() * 2;
    }

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

    protected Character fight(Character enemy) {
        int offensiveValue = getSv();
        int defensiveValue = enemy.dp;
        if (offensiveValue > defensiveValue) {
            enemy.hp -= offensiveValue - defensiveValue;
            if (enemy.isDie()) return enemy;
        } else {
            this.hp -= offensiveValue - defensiveValue;
            if (this.isDie()) return this;
        }

        return null;
    }

    protected boolean isDie() {
        return this.hp <= 0;
    }

    protected void die() {

        System.out.println(String.format(
                "%s dead.",
                this.name
        ));
    }

    @Override
    public String toString() {
        return String.format(
                "%s\n" +
                "HP: %d/%d\n",
                this.name,
                this.hp,
                this.maxHP
        );
    }

}
