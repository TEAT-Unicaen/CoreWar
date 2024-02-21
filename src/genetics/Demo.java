package genetics;

import genetics.seed.Seed;

public class Demo {
    public static void main(String[] args) {
        Seed seed = new Seed(15);
        System.out.println(seed.getRedcode());

        Seed seed2 = new Seed(15);
        System.out.println(seed2.getRedcode());

        GeneticOperatorManager gom = new GeneticOperatorManager();

        Seed child = gom.generateChild(seed, seed2);

        System.out.println(child.getRedcode());
    }
}
