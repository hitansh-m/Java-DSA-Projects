package spiderman;

import java.util.*;

import spiderman.Clusters.HashTable;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 * i. a (int): number of dimensions in the graph
 * ii. b (int): the initial size of the cluster table prior to rehashing
 * iii. c (double): the capacity(threshold) used to rehash the cluster table
 * 2. a lines, each with:
 * i. The dimension number (int)
 * ii. The number of canon events for the dimension (int)
 * iii. The dimension weight (int)
 * 
 * Step 2:
 * SpiderverseInputFile name is passed through the command line as args[1]
 * Read from the SpiderverseInputFile with the format:
 * 1. d (int): number of people in the file
 * 2. d lines, each with:
 * i. The dimension they are currently at (int)
 * ii. The name of the person (String)
 * iii. The dimensional signature of the person (int)
 * 
 * Step 3:
 * ColliderOutputFile name is passed in through the command line as args[2]
 * Output to ColliderOutputFile with the format:
 * 1. e lines, each with a different dimension number, then listing
 * all of the dimension numbers connected to that dimension (space separated)
 * 
 * @author Seth Kelley
 */

public class Collider {
    private int numOfDim;
    private int size;
    private double capacity;
    private Node[] adjLists;

    public Node[] getAdjLists() {
        return adjLists;
    }

    public Collider(String dimensionsFile) {
        StdIn.setFile(dimensionsFile);
        this.numOfDim = StdIn.readInt();
        this.size = StdIn.readInt();
        this.capacity = StdIn.readDouble();
        this.adjLists = new Node[numOfDim];
        for (int i = 0; i < numOfDim; i++) {
            int eachDim = StdIn.readInt();
            int canon = StdIn.readInt();
            int weight = StdIn.readInt();
            StdIn.readLine();
            adjLists[i] = new Node(eachDim, canon, weight, null);
        }
    }

    public void edge(HashTable hash) {
        Node[] arr = hash.table;
        for (int j = 0; j<arr.length; j++){
            int val = arr[j].val;
            if (j == arr.length-1){
                int val3 = arr[0].val;
                int val4 = arr[1].val;
                int c3 = arr[0].canon;
                int c4 = arr[1].canon;
                int w3 = arr[0].weight;
                int w4 = arr[1].weight;
                for (int k = 0; k<adjLists.length; k++){
                    if (val == adjLists[k].val){
                        insertE(adjLists[k], val3, c3, w3);
                        insertE(adjLists[k], val4, c4, w4);
                        continue;
                    }
                }
            }
        }
        for (int i = 0; i < adjLists.length; i++) {
            int index = hash.hashFunc(adjLists[i].val, hash.getSize());
            if ((arr[index].val == adjLists[i].val)) {
                for (Node ptr = arr[index].next; ptr != null; ptr = ptr.next) {
                    insertE(adjLists[i], ptr.val, ptr.canon, ptr.weight);
                }
            } else {
                insertE(adjLists[i], arr[index].val, arr[index].canon, arr[index].weight);
            }
        }
        for (int j = 0; j<arr.length; j++){
            int val = arr[j].val;
             if (j==arr.length-2) {
                int val1 = arr[j+1].val;
                int val2 = arr[0].val;
                int c1 = arr[j+1].canon;
                int c2 = arr[0].canon;
                int w1 = arr[j+1].weight;
                int w2 = arr[0].weight;
                for (int k = 0; k<adjLists.length; k++){
                    if (val == adjLists[k].val){
                        insertE(adjLists[k], val1, c1, w1);
                        insertSecond(adjLists[k], val2, c2, w2);
                        continue;
                    }
                }
            }
        }
        for (int j = 0; j<arr.length; j++){
            int val = arr[j].val;
            if (j!=arr.length-1 && j!=arr.length-2) {
                int val1 = arr[j+1].val;
                int val2 = arr[j+2].val;
                int c1 = arr[j+1].canon;
                int c2 = arr[j+2].canon;
                int w1 = arr[j+1].weight;
                int w2 = arr[j+2].weight;
                for (int k = 0; k<adjLists.length; k++){
                    if (val == adjLists[k].val){
                        insertE(adjLists[k], val1, c1, w1);
                        insertE(adjLists[k], val2, c2, w2);
                        continue;
                    }
                }
            }
        }
    }

    public void insertSecond(Node y, int x, int c, int w){
        Node ptr = y;
        Node temp = new Node(x, c, w, null);
         temp.next = ptr.next;
        ptr.next = temp;
    }

    
    public void insertE(Node y, int x, int c, int w) {
        Node last = y;
        while (last.next != null) {
            last = last.next;
        }
        last.next = (new Node(x, c, w, null));
    }

    public boolean Contains(HashTable hash, int cmp) {
        Node[] arr = hash.table;
        HashMap<Integer, Integer> h = new HashMap<>();
        for (int j = 0; j < arr.length; j++) {
            int cur = arr[j].val;
            h.put(j, cur);
        }
        if (h.containsValue(cmp)) {
            return true;
        }
        return false;
    }

    public int headIndex(HashTable hash, int cmp) {
        Node[] arr = hash.table;
        HashMap<Integer, Integer> h = new HashMap<>();
        for (int j = 0; j < arr.length; j++) {
            int cur = arr[j].val;
            h.put(cur, j);
        }
        return h.get(cmp);
    }

    public void printTable(String outputFile) {
        StdOut.setFile(outputFile);
        for (int i = 0; i < adjLists.length; i++) {
            Node current = adjLists[i];
            while (current != null) {
                StdOut.print(current.val + " ");
                current = current.next;
            }
            StdOut.println();
        }
    }
    public static ArrayList<dimNode> spider(String spiderverse){
        StdIn.setFile(spiderverse);
        int count = 0; 
        ArrayList<dimNode> spider = new ArrayList<>();
        int num = StdIn.readInt();
        StdIn.readLine();
        while (count < num) {
            int cur = StdIn.readInt();
            String name = StdIn.readString();
            int acc = StdIn.readInt();
            dimNode node = new dimNode(cur, name, acc);
            spider.add(node);
            StdIn.readLine();
            count++; 
        }
        return spider;
    }

    public static void main(String[] args) {

        if (args.length < 3) {
            StdOut.println(
                    "Execute: java -cp bin spiderman.Collider <dimension INput file> <spiderverse INput file> <collider OUTput file>");
            return;
        }
        Clusters clusters = new Clusters();
        HashTable hash = clusters.new HashTable(args[0]);
        hash.put();
        hash.wrap();
        // hash.printTable(args[1]);
        Collider d = new Collider(args[0]);
        d.edge(hash);
        d.printTable(args[2]);
        ArrayList<dimNode> dims = spider(args[1]);
    }
}