package genetics;

import genetics.converter.Converter;

public class Demo {
    public static void main(String[] args) {
        SeedMaker seedMaker = new SeedMaker();
        for(int i=0; i<100; i++) {
            String seed = seedMaker.generate();
            Converter.toRedCode(seed);
        }
    }
}
