package coreWar.genetics;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import coreWar.genetics.seed.Seed;
import coreWar.genetics.seed.SeedMaker;

public class Population extends HashMap<Seed, Integer> {
    public Population(int size) {
        SeedMaker sm = new SeedMaker();
        Random rand = new Random();
        for (int i=0; i<size; i++)
            this.put(new Seed(sm, rand.nextInt(15)+1));
    }

    public Population(Population previousGeneration) {
        GeneticOperatorManager gom = new GeneticOperatorManager();
        Seed winner = previousGeneration.getTheWinner();
        this.put(winner);

        for(Seed seed : previousGeneration.keySet()) {
            if(seed != winner) {
                this.put(gom.generateChild(winner, seed));
            }
        }
    }

    public void put(Seed seed) {
        this.put(seed, 0);
    }

    public void addPoint(Seed seed, int point) {
        if (seed != null)
            this.replace(seed, this.get(seed) + point);
    }

    public Seed getTheWinner() {
        Seed seedWin = null;
        int winMax = -1;
        for (Map.Entry<Seed, Integer> entry : this.entrySet()) {
            if (entry.getValue() > winMax)
                seedWin = entry.getKey();
        }
        return seedWin;
    }
}
