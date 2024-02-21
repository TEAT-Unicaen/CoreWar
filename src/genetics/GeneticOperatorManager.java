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
        Seed child = this.crossover(seed1.toString(), seed2.toString());
        this.mutation(child);

        return child;
    }

    private Seed crossover(String seed1, String seed2) {
        StringBuilder sb = new StringBuilder();
        int pivot = this.random.nextInt(Math.min(seed1.length(), seed2.length())/8)*8;
        if(this.random.nextInt() > 0) {
            sb.append(seed1.substring(0, pivot));
            sb.append(seed2.substring(pivot, seed2.length()));
        } else {
            sb.append(seed2.substring(0, pivot));
            sb.append(seed1.substring(pivot, seed1.length()));
        }

        return new Seed(sb.toString());
    }

    private void mutation(Seed seed) {
        SeedMaker sm = new SeedMaker();
        switch (random.nextInt(4)) {
            case 1:
                if(random.nextDouble()<this.probabilityMutation) {
                    System.out.println("modif");
                    int line = random.nextInt(seed.getLinesCount());
                    seed.setLine(line, sm.generate());
                }
                break;
            case 2:
                if((seed.getLinesCount()<15)&&(random.nextDouble()<this.probabilityMutation)) {
                    System.out.println("add");
                    int line = random.nextInt(seed.getLinesCount());
                    seed.addLine(line, sm.generate());
                }
                break;
            case 3:
                if((seed.getLinesCount()>1)&&(random.nextDouble()<this.probabilityMutation)) {
                    System.out.println("rm");
                    int line = random.nextInt(seed.getLinesCount());
                    seed.rmLine(line);
                }
                break;
            default:
        }
    }
}
