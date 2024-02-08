package genetics;

import java.util.Random;

public class SeedMaker {
    private Random randomSeed;

    public SeedMaker() {
        this.randomSeed = new Random();
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();
        int instruction = this.generateInstruction();
        sb.append(instruction);
        sb.append('|');
        sb.append(this.generateMode(instruction));
        for(int i = 0; i <2; i++) {
            sb.append('|');
            sb.append(this.generateValue());
        }
        return sb.toString();
    }

    public int generateInstruction() {
        return (randomSeed.nextInt(11));
    }

    public int generateMode(int typeLike) {
        switch (typeLike) {
            case 0:
                return randomSeed.nextInt(4);
            default:
                return randomSeed.nextInt(12);
        }
    }

    public String generateValue() {
        StringBuilder sb = new StringBuilder();
        sb.append(randomSeed.nextInt(17));
        return sb.toString();
    }
}
