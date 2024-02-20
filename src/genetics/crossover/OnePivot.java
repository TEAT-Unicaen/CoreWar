package genetics.crossover;

import genetics.seed.Seed;

import java.util.Random;

public class OnePivot {
    private String seed1, seed2;
    private Random random;
    
    public OnePivot(Seed seed1, Seed seed2) {
        this.seed1 = seed1.toString();
        this.seed2 = seed2.toString();
        this.random = new Random();
    }

    public Seed getChild() {
        StringBuilder sb = new StringBuilder();
        int pivot1 = (this.random.nextInt(seed1.length()/8)+1)*8;
        int pivot2 = (this.random.nextInt(seed2.length()/8)+1)*8;
        int seed2L = seed2.length();
        sb.append(seed1.substring(0, pivot1));
        sb.append(seed2.substring(pivot2, ((pivot1+seed2.length()-pivot2)/8)>15?pivot2+pivot1-seed2L:seed2L));
        return new Seed(sb.toString());
    }
}
