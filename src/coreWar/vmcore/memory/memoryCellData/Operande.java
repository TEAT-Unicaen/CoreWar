package coreWar.vmcore.memory.memoryCellData;

public class Operande {
    
    private AdressingModeEnum mode;
    private int value;

    @Override
    public boolean equals(Object ope) {
        if (ope == this) {return true;}
        if (!(ope instanceof Operande)) {return false;}
        Operande obj = (Operande) ope;
        return this.value == obj.getValue() && this.mode.equals(obj.getMode());
    }

    public Operande() {
        this.mode = AdressingModeEnum.IMMEDIATE;
        this.value = 0;
    } 

    public void setMode(AdressingModeEnum newMode) {
        this.mode = newMode;
    }

    public AdressingModeEnum getMode() {
        return this.mode;
    }

    public void setValue(int newVal) {
        this.value = newVal;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return " " + this.mode + " " + this.value;
    }
}
