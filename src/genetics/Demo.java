package genetics;

import genetics.seed.Seed;
import genetics.crossover.OnePivot;

public class Demo {
    public static void main(String[] args) {
        Seed seed = new Seed(10);
        System.out.println(seed.getRedcode());

        Seed seed2 = new Seed(10);
        System.out.println(seed2.getRedcode());

        OnePivot pivot = new OnePivot(seed, seed2);

        Seed child = pivot.getChild();
        System.out.println(child.getRedcode());

        
    }
}
