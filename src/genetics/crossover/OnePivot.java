package genetics.crossover;

import genetics.seed.Seed;

import java.util.Random;

public class OnePivot {
    private String best, loser;
    private Random random;
    
    public OnePivot(Seed best, Seed loser) {
        this.best = best.toString();
        this.loser = loser.toString();
        this.random = new Random();
    }

    public Seed getChild() {
        StringBuilder sb = new StringBuilder();
        int pivot = (this.random.nextInt(best.length()/8)+1)*8;
        sb.append(best.substring(0, pivot));
        sb.append(loser.substring(pivot, loser.length()));
        return new Seed(sb.toString());
    }
}
