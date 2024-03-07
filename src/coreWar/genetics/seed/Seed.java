package coreWar.genetics.seed;

import java.util.ArrayList;

public class Seed extends ArrayList<SeedLine> {

    public Seed() {
        super();
    }

    public Seed(SeedMaker seedMaker, int lines) {
        super();
        for (int i = 0; i < lines; i++)
            this.add(seedMaker.generate());
    }

    public Seed(String seed) {
        super();
        String[] separated = seed.split("(?<=\\G.{8})");
        for (String s : separated)
            this.add(new SeedLine(s));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size(); i++)
            sb.append(this.get(i).toString());
        return sb.toString();
    }

    public void addToPivot(int pivot, Seed seed) {
        for (int i = 0; i < pivot; i++) {
            this.add(seed.get(i));
        }
    }

    public void addFromPivot(int pivot, Seed seed) {
        for (int i = pivot; i < seed.size(); i++) {
            this.add(seed.get(i));
        }
    }

    public String getRedcode() {
        return RedCode.getRecode(this);
    }
}
