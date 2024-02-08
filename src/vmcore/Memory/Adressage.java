package vmcore.Memory;

public class Adressage {
    
    private MemoryCell mem;
    private Operande op;

    public Adressage(Operande op, MemoryCell mem) {
        this.op = op;
        this.mem = mem;
    }

    public int DEF() {
        //TODO : review code here 
        MemoryCell prov = this.mem;
        if (this.op == this.mem.GetA()) {
            if (this.op.GetValue() > 0) {
                for (int i = 0; i < this.op.GetValue(); i++) {
                    prov = prov.GetNext();
                }
            } else {
                int val = this.op.GetValue() * -1; 
                for (int i = 0; i < val; i++) {
                    prov = prov.GetPrevious();
                }
            }
            return prov.GetB().GetValue();
        } else {
            Operande b = this.mem.GetB();
            if (b.GetValue() > 0) {
                for (int i = 0; i < b.GetValue(); i++) {
                    prov = prov.GetNext();
                }
            } else {
                int val = b.GetValue() * -1; 
                for (int i = 0; i < val; i++) {
                    prov = prov.GetPrevious();
                }
            }
            return prov.GetA().GetValue();
        }
    }

    public int IMMEDIATE() {
        return this.op.GetValue();
    }
    public int INDIRECT() {
        //TODO : review code here 
        if (this.op != this.mem.GetA() && this.op == this.mem.GetB()) {return this.IMMEDIATE();}
        MemoryCell prov = this.mem;
        if (this.op.GetValue() > 0) {
            for (int i = 0; i < this.op.GetValue(); i++) {
                prov = prov.GetNext();
            }
        } else {
            int val = this.op.GetValue() * -1; 
            for (int i = 0; i < val; i++) {
                prov = prov.GetPrevious();
            }
        }
        Operande OtherVal = this.mem.GetB();
        if (OtherVal.GetValue() > 0) {
            for (int i = 0; i < this.op.GetValue(); i++) {
                prov = prov.GetNext();
            }
        } else {
            int val = OtherVal.GetValue() * -1; 
            for (int i = 0; i < val; i++) {
                prov = prov.GetPrevious();
            }
        }
        return prov.GetB().GetValue();
    }
    public int PREDEC() {
        //TODO : Make here 
        return 0;
    }

}
