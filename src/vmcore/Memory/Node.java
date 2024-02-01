package vmcore.Memory;

public class Node {
    
    public Node previous;
    public Node next; 
    public int value;

    public Node(int val){
        this.previous = null; 
        this.next = null; 
        this.value = val; 
    };
}
