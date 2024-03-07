package coreWar.vmcore.memory;

import java.util.Random;
import coreWar.vmcore.memory.memoryCellData.InstructionEnum;
import coreWar.vmcore.memory.memoryCellData.Operande;

public class Memory {

    public MemoryCell start;  
    private int size; 

    public Memory(int size) {
        this.size = size; 
        this.start = new MemoryCell();
        MemoryCell tmp = this.start;
        tmp.hardIndex = 0; 
        for (int i = 1; i < size; i++) {
            MemoryCell newCell = new MemoryCell();
            newCell.hardIndex = i; 
            tmp.setNext(newCell);
            newCell.setPrevious(tmp);
            tmp = newCell;
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

    public MemoryCell getEmptySlot() {
        Random rand = new Random();
        int nm = rand.nextInt((this.size - 17*2) + 1) + 17;
        MemoryCell slot = this.start;
        for (int i = 0; i < nm; i++) {
            slot = slot.getNext();
        }
        return slot; 
    }
}
