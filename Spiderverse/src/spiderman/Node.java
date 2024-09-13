package spiderman;
public class Node {
    public Node next;
    public int val;
    public int canon;
    public int weight;

    public Node(int val, int canon, int weight, Node next) {
        this.next = next;
        this.val = val;
        this.canon = canon;
        this.weight = weight;
    }
    
}
