package wanderer;

public class Characters extends PlayGround {
    // fields:
    private Hero hero;

    // constructors:
    public Characters(Tile[][] ground) {
        super(ground);
    }

    // getters:

    // setters:
    private void setGround(Tile[][] ground) {
        this.ground = ground;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    // methods:
    public void addCharacter(Character character) {
        this.characterList.add(character);
        removeFromFreePosition(character.getPosX(), character.getPosY());
    }

    public void move(Character character, int x, int y) {

        int currentX = character.getPosX();
        int currentY = character.getPosY();
        int targetX = currentX + x ;
        int targetY = currentY + y;

        if (matrixOutOfRange(targetX, targetY)) return; // character want to go out from the board
        if (isNotPermeable(targetX, targetY)) return;   // character want to go through the wall
        if ( this.isNotFree(targetX, targetY)) {
            Character enemy = getCharacterByCoord(targetX, targetY);
            fight(character, enemy);
            return;
        }
        character.move(x, y);
        removeFromFreePosition(currentX + x, currentY + y);
        addToFreePosition(currentX, currentY);
    }

    protected void moveAll() {
        for (Character character : characterList) {
            if (character instanceof Hero) continue;
            if (character.isDie()) continue;

            int x = 0;
            int y = 0;
            int direction = PlayGround.getRandNum(1, 2);

            if (direction == 1) {
                x = PlayGround.getRandNum(1, 2) == 1 ? -1 : 1;
            } else y = PlayGround.getRandNum(1, 2) == 1 ? -1 : 1;
            move(character, x, y);
        }
    }

    private void fight(Character offender,  Character defender) {

        if (isNotHero(offender) && isNotHero(defender)) return;

        Character offenseDead = offender.fight(defender);
        Character counterDead = defender.fight(offender);

        if (offenseDead != null) {
            offenseDead.die();
            addToFreePosition(offenseDead.getPosX(), offenseDead.getPosY());
            getKey(offenseDead);
        }
        if (counterDead != null) {
            counterDead.die();
            addToFreePosition(counterDead.getPosX(), counterDead.getPosY());
            getKey(counterDead);
        }
        System.out.println("Hero has key " + hero.getHasKey());
    }

    protected boolean isNotHero(Character character) {
        return !(character instanceof Hero);
    }

    private boolean isSkeleton(Character character) {
        return character instanceof Skeleton;
    }

    private void getKey(Character character) {
        if (hero.getHasKey()) return;
        hero.setHasKey(getKeyFromSkeleton(character));
    }

    private boolean getKeyFromSkeleton(Character character) {
        if (!isSkeleton(character)) return false;
        return ((Skeleton) character).isHasKey();
    }

    private Character getCharacterByCoord(int x, int y) {
        for (Character character : characterList) {
            if (character.getPosX() == x && character.getPosY() == y) return character;
        }
        return null;
    }

    @Override
    public String toString() {
        String introduce = String.format(
                "Number of Characters: %s\n" +
                "Reference: %s",
                characterList.size(),
                super.toString()
        );
        return introduce;
    }


}
