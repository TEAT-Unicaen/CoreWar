package vmcore.Memory;

public class Memory {

    public MemoryCell start;  
    private int size; 

    public Memory(int size) {
        this.size = size; 
        this.start = new MemoryCell();
        MemoryCell tmp = this.start;
        for (int i = 1; i < size; i++) {
            MemoryCell newCell = new MemoryCell();
            tmp.SetNext(newCell);
            newCell.SetPrevious(tmp);
            tmp = newCell;
        }
        this.start.SetPrevious(tmp);
        tmp.SetNext(this.start);
    }

    public void Display() {
        MemoryCell current = this.start;
        for (int i = 0; i < this.size; i++) {
            System.out.println(current.GetB().GetValue() + " " + i);
            current = current.GetNext(); 
        }
    }
}
