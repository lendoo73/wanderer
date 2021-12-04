package wanderer;

public abstract class Tile {
    // fields:
    private boolean permeable;
    private String imgPath;
    private String name;

    // constructors:
    public Tile() {}

    public Tile(String name,  boolean permeable, String imgPath) {
        this.name = name;
        this.permeable = permeable;
        this.imgPath = imgPath;
    }

    // getters:
    public String getName() {
        return name;
    }

    public boolean isPermeable() {
        return permeable;
    }

    public String getImgPath() {
        return imgPath;
    }


    // setters:
}
