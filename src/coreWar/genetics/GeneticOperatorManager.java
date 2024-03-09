package coreWar.genetics;

import java.util.Random;

import coreWar.genetics.seed.Seed;
import coreWar.genetics.seed.SeedMaker;


public class GeneticOperatorManager  {
    private Random random;
    private SeedMaker sm;
    private double probabilityMutation = 1.0/3.0;

    public GeneticOperatorManager() {}

    public Seed generateChild(Seed seed1, Seed seed2) {
        this.random = new Random();
        this.sm = new SeedMaker();
        Seed child = this.crossover(seed1, seed2);
        this.mutation(child);
        return child;
    }

    private Seed crossover(Seed seed1, Seed seed2) {
        Seed newSeed = new Seed();
        int pivot = this.random.nextInt(Math.min(seed1.size(), seed2.size()));
        if (this.random.nextInt() > 0) {
            newSeed.addToPivot(pivot, seed1);
            newSeed.addFromPivot(pivot, seed2);
        } else {
            newSeed.addToPivot(pivot, seed2);
            newSeed.addFromPivot(pivot, seed1);
        }
        return newSeed;
    }

    private void mutation(Seed seed) {
        switch (random.nextInt(5)) {
            case 1:// Modified
                if (random.nextDouble() < this.probabilityMutation) {
                    int line = random.nextInt(seed.size());
                    this.sm.regenerate(random.nextInt(4), seed.get(line));
                }
                break;
            case 2:// Additional
                if ((seed.size() < 15) && (random.nextDouble() < this.probabilityMutation)) {
                    int line = random.nextInt(seed.size());
                    seed.add(line, this.sm.generate());
                }
                break;
            case 3:// removing
                if ((seed.size() > 1) && (random.nextDouble() < this.probabilityMutation)) {
                    int line = random.nextInt(seed.size());
                    seed.remove(line);
                }
                break;
            case 4:// swapping
                if (random.nextDouble() < this.probabilityMutation) {
                    int line1 = random.nextInt(seed.size());
                    int line2 = random.nextInt(seed.size());
                    seed.swap(line1, line2);
                }
                break;
            default:
        }
    }
}
