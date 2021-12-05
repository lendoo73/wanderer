package wanderer;

public class Boss extends Character {
    // fields:

    // constructors:
    public Boss() {
        super("img/boss.png");
        this.hp = 2 * this.level * d6() + d6();
        this.maxHP = hp;
        this.dp = 2 * d6();
        this.dp = this.level / 2 * d6() + d6() / 2;
        this.sp = this.level * d6() + this.level;
    }

    // getters:

    // setters:

    // methods:

}
