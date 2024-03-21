package coreWar.genetics.seed;

import java.io.Serializable;

public class SeedLine implements Serializable {
    private int instruction, adressing, value1, value2;

    public SeedLine(int instruction, int adressing, int value1, int value2) {
        this.instruction = instruction;
        this.adressing = adressing;
        this.value1 = value1;
        this.value2 = value2;
    }

    public SeedLine(SeedLine srcLine) {
        this(srcLine.getInstruction(), srcLine.getAdressingMode(), srcLine.getValue1(), srcLine.getValue2());
    }

    public SeedLine(String seedLine) {
        String[] separated = seedLine.split("(?<=\\G.{2})");
        this.instruction = Integer.valueOf(separated[0]);
        this.adressing = Integer.valueOf(separated[1]);
        this.value1 = Integer.valueOf(separated[2]);
        this.value2 = Integer.valueOf(separated[3]);
    }

    public String toString() {
        return String.format("%02d%02d%02d%02d", this.instruction, this.adressing, this.value1, this.value2);
    }

    public int getInstruction() {
        return this.instruction;
    }

    public int getAdressingMode() {
        return this.adressing;
    }

    public int getValue1() {
        return this.value1;
    }

    public int getValue2() {
        return this.value2;
    }

    public void setInstruction(int instruction) {
        this.instruction = instruction;
    }

    public void setAdressing(int adressing) {
        this.adressing = adressing;
    }

    public void setValue1(int value) {
        this.value1 = value;
    }

    public void setValue2(int value) {
        this.value2 = value;
    }

    public String getRedcode() {
        return RedCode.getRedCodeLine(this);
    }
}
