package wanderer;

public class Character {
    // fields:
    private String imgPath;
    private int posX;
    private int posY;

    // constructors:
    public Character(String imgPath, int posX, int posY) {
        this.imgPath = imgPath;
        this.posX = posX;
        this.posY = posY;
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
    public void move(int x, int y) {
        if (isWall(this.getPosX() + x, this.getPosY() + y)) return;
        this.setPosX(this.getPosX() + x);
        this.setPosY(this.getPosY() + y);
    }
}
