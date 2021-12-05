package wanderer;

public class Skeleton extends Character {
    // fields:
    private boolean hasKey;

    // constructors:
    public Skeleton() {
        this(false);
    }

    public Skeleton(boolean hasKey) {
        super("Skeleton", "img/skeleton.png");
        this.hp = 2 * this.level * d6();
        this.maxHP = hp;
        this.dp = 2 * d6();
        this.dp = this.level / 2 * d6();
        this.sp = this.level * d6();
        this.hasKey = hasKey;
    }


    // getters:
    public boolean isHasKey() {
        return hasKey;
    }


    // setters:

    // methods:

}
