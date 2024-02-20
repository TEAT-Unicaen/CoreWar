package vmcore.Memory;

public class MemoryCell {
    
    private MemoryCell previous;
    private MemoryCell next;

    private Instruction inst;
    private Operande A;
    private Operande B;
    private int Owner;

    public MemoryCell(){
        this.previous = null; 
        this.next = null; 
        this.inst = Instruction.DAT;
        this.A = new Operande(); 
        this.B = new Operande();
    };

    public void SetNext(MemoryCell next) {
        this.next = next;
    }

    public MemoryCell GetNext() {
        return this.next;
    }

    public void SetPrevious(MemoryCell prev) {
        this.previous = prev;
    }

    public MemoryCell GetPrevious() {
        return this.previous;
    }

    public Operande CopyA() {
        Operande temp = new Operande();
        temp.SetMode(this.A.GetMode());
        temp.SetValue(this.A.GetValue());
        return temp;
    }

    public Operande CopyB() {
        Operande temp = new Operande();
        temp.SetMode(this.B.GetMode());
        temp.SetValue(this.B.GetValue());
        return temp;
    }

    public void PasteCell(Instruction inst, Operande A, Operande B) {
        this.inst = inst;
        this.A = A; 
        this.B = B;
    }

    public Instruction GetInstruction() {
        return this.inst;
    }

    public void SetInstruction(Instruction inst) {
        this.inst = inst;
    }

    public Operande GetA() {
        return this.A;
    }

    public Operande GetB() {
        return this.B;
    }

    public void SetOwner(int newOwner) {
        this.Owner = newOwner;
    }

    public int GetOwner() {
        return this.Owner;
    }
}
