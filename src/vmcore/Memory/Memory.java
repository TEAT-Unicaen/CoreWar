package vmcore.Memory;

public class Memory {

    public Node start;  
    private int size; 

    public Memory(int size) {
        this.size = size; 
        this.start = new Node(0);
        Node tmp = this.start;
        for (int i = 1; i < size; i++) {
            tmp.next = new Node(i);
            tmp.next.previous = tmp;
            tmp = tmp.next;
        }
        this.start.previous = tmp;
        tmp.next = this.start;
    }

    public void Display() {
        Node current = this.start;
        for (int i = 0; i < this.size; i++) {
            System.out.println(current.value);
            current = current.next; 
        }
    }
}
