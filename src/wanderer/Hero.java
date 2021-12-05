package wanderer;

public class Hero extends Character {

    public Hero() {
        super("img/hero-down.png", 0, 0);
        this.hp = 20 + 3 * d6();
        this.maxHP = hp;
        this.dp = 2 * d6();
        this.sp = 5 + d6();
    }

    // methods
    public void turn(int x, int y) {
        String direction = x == -1
                ? "left"
                : x == 1
                ? "right"
                : y == 1
                ? "down"
                : "up";
        this.setImgPath(String.format("img/hero-%s.png", direction));

    }

}
