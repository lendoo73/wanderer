package wanderer;

public class Skeleton extends Character {
    // fields:

    // constructors:
    public Skeleton() {
        super("img/skeleton.png");
        this.hp = 2 * this.level * d6();
        this.maxHP = hp;
        this.dp = 2 * d6();
        this.dp = this.level / 2 * d6();
        this.sp = this.level * d6();
    }

    // getters:

    // setters:

    // methods:

}
