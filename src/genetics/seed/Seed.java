package genetics.seed;

import java.util.ArrayList;
import java.util.List;

public class Seed {
    private List<SeedLine> seedLines;

    public Seed(int lines) {
        SeedMaker seedMaker = new SeedMaker();
        this.seedLines = new ArrayList<SeedLine>();
        for (int i = 0; i < lines; i++)
            this.seedLines.add(seedMaker.generate());
    }

    public Seed(String seed) {
        String[] separated = seed.split("(?<=\\G.{8})");
        this.seedLines = new ArrayList<SeedLine>();
        for (String s : separated)
            this.seedLines.add(new SeedLine(s));
    }

    public int length() {
        return this.seedLines.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < seedLines.size(); i++)
            sb.append(this.seedLines.get(i).toString());
        return sb.toString();
    }

    public List<SeedLine> getLines() {
        return this.seedLines;
    }

    public void rmLine(int line) {
        this.seedLines.remove(line);
    }

    public void addLine(int line, SeedLine lineDate) {
        this.seedLines.add(line, lineDate);
    }

    public void setLine(int line, SeedLine lineData) {
        this.seedLines.set(line, lineData);
    }

    public int getLinesCount() {
        return this.seedLines.size();
    }

    public String getRedcode() {
        return RedCode.getRecode(this);
    }
}
