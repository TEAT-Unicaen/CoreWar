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
            this.put(new Seed(sm, rand.nextInt(11)+5), 0);
    }

    public Population nextPopulation() {
        Population newP = new Population(0);
        GeneticOperatorManager gom = new GeneticOperatorManager();
        Seed winner = this.getTheWinner();

        newP.put(new Seed(winner), 0);

        for(Seed seed : this.keySet()) {
            if(seed != winner) {
                Seed child = new Seed();
                do {
                    child = gom.generateChild(winner, seed);
                } while(newP.put(child, 0) != null);
            }
        }
        return newP;
    }


    public void addPoint(Seed seed, int point) {
        if (seed != null)
            this.replace(seed, this.get(seed) + point);
    }

    public Seed getTheWinner() {
        Seed seedWin = null;
        int winMax = Integer.MIN_VALUE;
        for (Map.Entry<Seed, Integer> entry : this.entrySet()) {
            if (entry.getValue() > winMax) {
                winMax = entry.getValue();
                seedWin = entry.getKey();
            }
        }
        return seedWin;
    }
}
