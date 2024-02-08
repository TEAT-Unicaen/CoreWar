package vmcore.Memory;

public class Operande {
    
    public Modes mode;
    public int A;

    public Operande() {
        this.mode = Modes.IMMEDIATE;
        this.A = 0;
    } 

}
