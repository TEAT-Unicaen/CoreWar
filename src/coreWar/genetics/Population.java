package coreWar.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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

        List<Map.Entry<Seed, Integer>> top = this.getTop();
        for (Map.Entry<Seed, Integer> ind : top) {
            for (Map.Entry<Seed, Integer> ind2 : top) {
                if (ind == ind2) {
                    newP.put(ind.getKey(), 0);
                }
                Seed child = new Seed();
                do {
                    child = gom.generateChild(ind.getKey(), ind2.getKey());
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
        this.remove(seedWin);
        return seedWin;
    }

    public List<Map.Entry<Seed, Integer>> getTop() {
        List<Map.Entry<Seed, Integer>> top = new ArrayList<>();
        for (Map.Entry<Seed, Integer> entry : this.entrySet()) {
            top.add(entry);
        }
        Collections.sort(top, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        
        return top.subList(0, 10);
    }
}
