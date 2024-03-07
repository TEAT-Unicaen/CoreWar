package coreWar.genetics.seed;

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

    public void regenerate(int element, SeedLine src) {
        switch (element) {
            case 0:
                src.setInstruction(this.generateInstruction());
            case 1:
                src.setAdressing(this.generateMode(src.getInstruction()));
                break;
            case 2:
                src.setValue1(this.generateValue());
                break;
            case 3:
                src.setValue2(this.generateValue());
                break;
        }
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
        return this.randomSeed.nextInt(26)-9;//T: Don't ask only me can know it //P: LMAOOOOOOO
    }
}
