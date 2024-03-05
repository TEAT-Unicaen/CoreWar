package coreWar.genetics;

import coreWar.genetics.seed.Seed;

public class Demo {
    public static void main(String[] args) {
        Population gen1 = new Population(5);
        int x =0;
        for (Seed seed : gen1.keySet()) {
            if (x == 2)
                gen1.win(seed);
            x++;
        }
        Population gen2 = new Population(gen1);
        System.out.println(gen1);
        System.out.println(gen2);
    }
}
