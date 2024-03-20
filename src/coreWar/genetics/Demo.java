package coreWar.genetics;

import java.util.ArrayList;

import coreWar.genetics.seed.Seed;
import coreWar.genetics.seed.SeedLine;

public class Demo {
    public static void main(String[] args) {
        int populationNumber = 1000;
        ArrayList<Population> p = new ArrayList<Population>(populationNumber);
        p.add(new Population(100));
        for (int i = 0; i < populationNumber; i++) {
            System.out.println("Generation " + i);
            for (Seed seed : p.get(i).keySet()) {
                for (SeedLine sl : seed) {
                    if (sl.getRedcode().contains("DAT"))
                        p.get(i).addPoint(seed, 1);
                    if (sl.getRedcode().contains("DAT #8"))
                        p.get(i).addPoint(seed, 2);
                    if (sl.getRedcode().contains("DAT #8, #8"))
                        p.get(i).addPoint(seed, 2);
                    if (sl.getRedcode().contains("DAT #8, <"))
                        p.get(i).addPoint(seed, -10);
                    if (sl.getRedcode().contains("ADD"))
                        p.get(i).addPoint(seed, -2);
                }
            }
            Seed winner = p.get(i).getTheWinner();
            System.out.println("Point:" + p.get(i).get(winner));
            System.out.println(winner.getRedcode());
            System.out.println("");
    
            p.add(p.get(i).nextPopulation());
        }
    System.out.println(p.get(998));
    }
}
