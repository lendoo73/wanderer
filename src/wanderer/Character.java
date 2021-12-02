package wanderer;

public class Character {
    // fields:
    private String imgPath;
    private int posX;
    private int posY;

    public Character(String imgPath) {
        this.imgPath = imgPath;
        this.posX = 0;
        this.posY = 0;
    }

    public String getImgPath() {
        return imgPath;
    }
}
