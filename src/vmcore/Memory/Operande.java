package vmcore.Memory;

public class Operande {
    
    private Modes mode;
    private int value;

    @Override
    public boolean equals(Object ope) {
        if (ope == this) {return true;}
        if (!(ope instanceof Operande)) {return false;}
        Operande obj = (Operande) ope;
        return this.value == obj.GetValue() && this.mode.equals(obj.GetMode());
    }

    public Operande() {
        this.mode = Modes.IMMEDIATE;
        this.value = 0;
    } 

    public void SetMode(Modes newMode) {
        this.mode = newMode;
    }

    public Modes GetMode() {
        return this.mode;
    }

    public void SetValue(int newVal) {
        this.value = newVal;
    }

    public int GetValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return " " + this.mode + " " + this.value;
    }
}
