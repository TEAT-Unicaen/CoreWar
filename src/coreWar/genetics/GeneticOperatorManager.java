package coreWar.genetics;

import java.util.Random;

import coreWar.genetics.seed.Seed;
import coreWar.genetics.seed.SeedMaker;


public class GeneticOperatorManager  {
    private Random random;
    private double probabilityMutation = 1.0/3.0;

    public GeneticOperatorManager() {
        this.random = new Random();
    }

    public Seed generateChild(Seed seed1, Seed seed2) {
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
        SeedMaker sm = new SeedMaker();
        switch (random.nextInt(4)) {
            case 1:
                if (random.nextDouble() < this.probabilityMutation) {
                    int line = random.nextInt(seed.size());
                    sm.regenerate(random.nextInt(4), seed.get(line));
                }
                break;
            case 2:
                if ((seed.size() < 15) && (random.nextDouble() < this.probabilityMutation)) {
                    int line = random.nextInt(seed.size());
                    seed.add(line, sm.generate());
                }
                break;
            case 3:
                if ((seed.size() > 1) && (random.nextDouble() < this.probabilityMutation)) {
                    int line = random.nextInt(seed.size());
                    seed.remove(line);
                }
                break;
            default:
        }
    }
}
