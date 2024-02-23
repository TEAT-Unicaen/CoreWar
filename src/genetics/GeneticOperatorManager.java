package genetics;

import java.util.Random;

import genetics.seed.Seed;
import genetics.seed.SeedMaker;


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
        StringBuilder sb = new StringBuilder();
        String stringSeed1 = seed1.toString();
        String stringSeed2 = seed2.toString();
        int pivot = this.random.nextInt(Math.min(seed1.length(), seed2.length()))*8;
        if (this.random.nextInt() > 0) {
            sb.append(stringSeed1.substring(0, pivot));
            sb.append(stringSeed2.substring(pivot, stringSeed2.length()));
        } else {
            sb.append(stringSeed2.substring(0, pivot));
            sb.append(stringSeed1.substring(pivot, stringSeed1.length()));
        }
        return new Seed(sb.toString());
    }

    private void mutation(Seed seed) {
        SeedMaker sm = new SeedMaker();
        switch (random.nextInt(4)) {
            case 1:
                if (random.nextDouble() < this.probabilityMutation) {
                    System.out.println("modif");
                    int line = random.nextInt(seed.getLinesCount());
                    System.out.println(line);
                    sm.regenerate(random.nextInt(4), seed.getLines().get(line));
                }
                break;
            case 2:
                if ((seed.getLinesCount() < 15) && (random.nextDouble() < this.probabilityMutation)) {
                    System.out.println("add");
                    int line = random.nextInt(seed.getLinesCount());
                    seed.addLine(line, sm.generate());
                }
                break;
            case 3:
                if ((seed.getLinesCount() > 1) && (random.nextDouble() < this.probabilityMutation)) {
                    System.out.println("rm");
                    int line = random.nextInt(seed.getLinesCount());
                    seed.rmLine(line);
                }
                break;
            default:
        }
    }
}
