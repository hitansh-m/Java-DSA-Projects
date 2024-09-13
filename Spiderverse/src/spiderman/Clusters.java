package spiderman;
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
 * 
 * Step 2:
 * ClusterOutputFile name is passed in through the command line as args[1]
 * Output to ClusterOutputFile with the format:
 * 1. n lines, listing all of the dimension numbers connected to
 * that dimension in order (space separated)
 * n is the size of the cluster table.
 * 
 * @author Seth Kelley
 */

 public class Clusters {
    public class HashTable {
        public int numOfDim;
        public int temp;

        public int getTemp() {
            return temp;
        }

        private int size;
        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public double capacity;
        public Node[] table;

        public Node[] getTable() {
            return table;
        }

        public void setTable(Node[] table) {
            this.table = table;
        }

        public HashTable(String inputfile) {
            StdIn.setFile(inputfile);
            this.numOfDim = StdIn.readInt();
            this.size = StdIn.readInt();
            this.capacity = StdIn.readDouble();
            this.table = new Node[size];
        }
        public void put() {
            int dimensionsStored = 0;
            while (numOfDim > 0) {
                int eachDim = StdIn.readInt();
                int canon = StdIn.readInt();
                int weight = StdIn.readInt();
                StdIn.readLine();
                insertF(eachDim, canon, weight);
                dimensionsStored++;
                if ((double) dimensionsStored / size >= capacity) {
                    rehash();
                }
                numOfDim--;
            }
            temp = dimensionsStored;
        }

        public void insertF(int eachDim, int canon, int weight) {
            int index = hashFunc(eachDim, size);
            Node newNode = new Node(eachDim, canon, weight, table[index]);
            table[index] = newNode;
        }

        public int hashFunc(int eachDim, int tableSize) {
            return eachDim % tableSize;
        }

        public void rehash() {
            int newSize = size * 2;
            Node[] temp = new Node[newSize];
            for (int i = 0; i < table.length; i++) {
                Node current = table[i];
                while (current != null) {
                    int newIndex = hashFunc(current.val, newSize);
                    Node newNode = new Node(current.val, current.canon, current.weight, temp[newIndex]);
                    temp[newIndex] = newNode;
                    current = current.next;
                }
            }
            table = temp;
            size = newSize;
        }
        public void wrap(){
            for (int i = 0; i<table.length; i++){
                if (i == 0){
                    insertE(table[i], table[table.length-1].val);
                    insertE(table[i], table[table.length-2].val);
                } else if (i == 1){
                    insertE(table[i], table[0].val);
                    insertE(table[i], table[table.length-1].val);
                } else {
                    insertE(table[i], table[i-1].val);
                    insertE(table[i], table[i-2].val);
                }
            }
        }
        public void insertE(Node y, int x){
            Node last = y;
            while (last.next != null){
                last = last.next;
            } 
            last.next = new Node(x, y.canon, y.weight, null);
        }

        public void printTable(String outputFile) {
            StdOut.setFile(outputFile);
            for (int i = 0; i < table.length; i++) {
                Node current = table[i];
                while (current != null) {
                    StdOut.print(current.val + " ");
                    current = current.next;
                }
                StdOut.println();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                "Execute: java -cp bin spiderman.Clusters <dimension input file> <cluster output file>");
            return;
        }
        Clusters clusters = new Clusters();
        HashTable hash = clusters.new HashTable(args[0]);
        hash.put();
        hash.wrap();
        hash.printTable(args[1]);
    }
}