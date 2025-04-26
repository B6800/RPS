import java.util.Random;
public enum Item {//Represents 3 possible choices
    PAPER,
    SCISSORS,
    ROCK;

    final static Random RANDOM = new Random(System.currentTimeMillis());

    static Item random() {
        //Item.random is the method used to generate random choices for the computer player
        return switch (RANDOM.nextInt() % 3) {//Enhanced switch statement
            case 0 -> {
                System.out.println("Computer says: Paper!");
                yield PAPER;
            }
            case 1 -> {
                System.out.println("Computer says: Scissors!");
                yield SCISSORS;
            }
            default -> {
                System.out.println("Computer says: Rock!");
                yield ROCK;
            }
        };
    }
}

