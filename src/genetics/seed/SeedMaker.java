package genetics.seed;

import java.util.Random;

public class SeedMaker {
    private Random randomSeed;

    public SeedMaker() {
        this.randomSeed = new Random();
    }

    public SeedLine generate() {
        int instruction = generateInstruction();
        return new SeedLine(instruction, generateMode(instruction), generateValue(), generateValue());
    }

    public int generateInstruction() {
        return (this.randomSeed.nextInt(11));
    }

    public int generateMode(int typeLike) {
        switch (typeLike) {
            case 0:
                return this.randomSeed.nextInt(4);
            default:
                return this.randomSeed.nextInt(12);
        }
    }

    public int generateValue() {
        return this.randomSeed.nextInt(17);
    }
}
