package coreWar.genetics;

import coreWar.genetics.seed.Seed;
import coreWar.genetics.seed.SeedLine;

public class Demo {
    public static void main(String[] args) {
        Population p = new Population(100);
        int populationNumber = 100;
        for (int i = 0; i < populationNumber; i++) {
            for (Seed seed : p.keySet()) {
                for (SeedLine sl : seed) {
                    if (sl.toString().contains("-1"))
                        p.addPoint(seed, 1);
                }
            }
            Seed winner = p.getTheWinner();
            System.out.println("Point:" + p.get(winner));
            System.out.println(winner.getRedcode());
            System.out.println("");
            p = new Population(p);
        }
    }
}
