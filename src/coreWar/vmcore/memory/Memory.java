package coreWar.vmcore.memory;

import coreWar.vmcore.interpreter.Adressage;
import coreWar.vmcore.memory.memoryCellData.InstructionEnum;
import coreWar.vmcore.memory.memoryCellData.Operande;


public class Memory {

    public MemoryCell start;  
    public MemoryCell mid; 
    private int size; 

    public Memory(int size) {
        this.size = size; 
        Adressage.memorySize = size;
        this.start = new MemoryCell();
        MemoryCell tmp = this.start;
        tmp.hardIndex = 0; 
        for (int i = 1; i < size; i++) {
            MemoryCell newCell = new MemoryCell();
            newCell.hardIndex = i; 
            tmp.setNext(newCell);
            newCell.setPrevious(tmp);
            tmp = newCell;
            if (i == size/2) {mid = newCell;}
        }
        this.start.setPrevious(tmp);
        tmp.setNext(this.start);
    }

    public void setInstructionInMemoryAtIndex(int index, InstructionEnum inst, Operande A, Operande B) {
        MemoryCell tmp = this.start; 
        if (index > 0) {
            for (int i = 0; i < index; i++)
                tmp = tmp.getNext(); 
        } else {
            index *= -1; 
            for (int i = 0; i < index; i++)
                tmp = tmp.getPrevious();
        }
        tmp.pasteCell(inst, A, B);
    }

    public int getSize() {
        return this.size; 
    }

    public void display() {
        MemoryCell current = this.start;
        for (int i = 0; i < this.size; i++) {
            System.out.println(current.getB().getValue() + " " + i);
            current = current.getNext(); 
        }
    }
}
