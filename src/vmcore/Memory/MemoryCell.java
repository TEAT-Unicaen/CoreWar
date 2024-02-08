package vmcore.Memory;

public class MemoryCell {
    
    public MemoryCell previous;
    public MemoryCell next;

    public Instruction inst;
    public Operande A;
    public Operande B;

    public MemoryCell(){
        this.previous = null; 
        this.next = null; 
        this.inst = Instruction.DAT;
        this.A = new Operande(); 
        this.B = new Operande();
    };
}
