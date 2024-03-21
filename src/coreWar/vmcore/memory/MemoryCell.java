package coreWar.vmcore.memory;

import coreWar.vmcore.memory.memoryCellData.InstructionEnum;
import coreWar.vmcore.memory.memoryCellData.Operande;

public class MemoryCell {
    
    private MemoryCell previous;
    private MemoryCell next;

    private InstructionEnum inst;
    private Operande A;
    private Operande B;
    private int Owner = -1;

    public int hardIndex; //pour l'affichage

    public MemoryCell(){
        this.previous = null; 
        this.next = null; 
        this.inst = InstructionEnum.DAT;
        this.A = new Operande(); 
        this.B = new Operande();
    };

    public String toStringDebug() {
        StringBuilder sB = new StringBuilder();
        sB.append(this.getInstruction().toString());
        sB.append(" | ");
        sB.append(this.getA().toString());
        sB.append(" | ");
        sB.append(this.getB().toString());
        return sB.toString();
    }

    @Override
    public boolean equals(Object mem) {
        if (mem == this) 
            return true;
        if (!(mem instanceof MemoryCell))
            return false;
        MemoryCell obj = (MemoryCell) mem; 
        return this.inst.equals(obj.getInstruction()) && this.A.equals(obj.getA()) && this.B.equals(obj.getB());
    }

    public void setNext(MemoryCell next) {
        this.next = next;
    }

    public MemoryCell getNext() {
        return this.next;
    }

    public void setPrevious(MemoryCell prev) {
        this.previous = prev;
    }

    public MemoryCell getPrevious() {
        return this.previous;
    }

    public Operande copyA() {
        Operande temp = new Operande();
        temp.setMode(this.A.getMode());
        temp.setValue(this.A.getValue());
        return temp;
    }

    public Operande copyB() {
        Operande temp = new Operande();
        temp.setMode(this.B.getMode());
        temp.setValue(this.B.getValue());
        return temp;
    }

    public void pasteCell(InstructionEnum inst, Operande A, Operande B) {
        this.inst = inst;
        this.A = A; 
        this.B = B;
    }

    public InstructionEnum getInstruction() {
        return this.inst;
    }

    public void setInstruction(InstructionEnum inst) {
        this.inst = inst;
    }

    public Operande getA() {
        return this.A;
    }

    public Operande getB() {
        return this.B;
    }

    public void setOwner(int newOwner) {
        //System.out.println("SET TO " + newOwner);
        this.Owner = newOwner;
    }

    public int getOwner() {
        return this.Owner;
    }
}
