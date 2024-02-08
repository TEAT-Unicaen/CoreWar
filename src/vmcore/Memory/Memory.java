package vmcore.Memory;

public class Memory {

    public MemoryCell start;  
    private int size; 

    public Memory(int size) {
        this.size = size; 
        this.start = new MemoryCell();
        MemoryCell tmp = this.start;
        for (int i = 1; i < size; i++) {
            tmp.next = new MemoryCell();
            tmp.next.previous = tmp;
            tmp = tmp.next;
        }
        this.start.previous = tmp;
        tmp.next = this.start;
    }

    public void Display() {
        MemoryCell current = this.start;
        for (int i = 0; i < this.size; i++) {
            System.out.println(current.B);
            current = current.next; 
        }
    }
}
