package wanderer;

public class Characters extends PlayGround {
    // fields:

    // constructors:
    public Characters(Tile[][] ground) {
        super(ground);
    }

    // getters:

    // setters:
    private void setGround(Tile[][] ground) {
        this.ground = ground;
    }

    // methods:
    public void addCharacter(Character character) {
        this.characterList.add(character);
        removeFromFreePosition(character.getPosX(), character.getPosY());
    }

    public void move(Character character, int x, int y) {
        int currentX = character.getPosX();
        int currentY = character.getPosY();
        if (this.isFree(currentX + x, currentY + y)) {
            // TODO check for fight
            if (character instanceof Hero) {
                //System.out.println(ground[x][y]);
                System.out.println(character);

            }
            return;
        }
        character.move(x, y);
        removeFromFreePosition(currentX + x, currentY + y);
        addToFreePosition(currentX, currentY);
    }

    protected void moveAll() {
        for (Character character : characterList) {
            if (character instanceof Hero) continue;

            int x = 0;
            int y = 0;
            int direction = PlayGround.getRandNum(1, 2);

            if (direction == 1) {
                x = PlayGround.getRandNum(1, 2) == 1 ? -1 : 1;
            } else y = PlayGround.getRandNum(1, 2) == 1 ? -1 : 1;
            move(character, x, y);
        }
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
