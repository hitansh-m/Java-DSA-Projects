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
 * HubInputFile name is passed through the command line as args[2]
 * Read from the HubInputFile with the format:
 * One integer
 * i. The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * CollectedOutputFile name is passed in through the command line as args[3]
 * Output to CollectedOutputFile with the format:
 * 1. e Lines, listing the Name of the anomaly collected with the Spider who
 * is at the same Dimension (if one exists, space separated) followed by
 * the Dimension number for each Dimension in the route (space separated)
 * 
 * @author Seth Kelley
 */

public class CollectAnomalies {
    private ArrayList<Integer> marked;
    private int x;
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    private HashMap<Integer, Integer> hmap;

    public CollectAnomalies(String hubFile, int dims) {
        StdIn.setFile(hubFile);
        x = StdIn.readInt();
        marked = new ArrayList<Integer>();
        hmap = new HashMap<Integer, Integer>();
    }

    public void BFS(Node[] arr, ArrayList<dimNode> spider, String output) {
        StdOut.setFile(output);
        for (int i = 0; i < spider.size(); i++) {
            String curSpider = null;
            boolean containsSpider = false;
            int dimensionCheck = spider.get(i).getCurD();
            String curName = spider.get(i).getName();
            if (spider.get(i).getCurD() == spider.get(i).getAccD()) {
                continue;
            }
            if (spider.get(i).getCurD() != x) {
                for (int j = 0; j < spider.size(); j++) {
                    if ((!(spider.get(j).getName().equals(curName))) && spider.get(j).getCurD() == dimensionCheck) {
                        if (containsSpider == false) {
                            containsSpider = true;
                            curSpider = spider.get(j).getName();
                        }
                    }
                }
                if (containsSpider == true && spider.get(i).getCurD() != spider.get(i).getAccD()) {
                    hmap.clear();
                    StdOut.print(curName + " " + curSpider + " " + spider.get(i).getCurD() + " ");
                    BFSonNodeBackWards(arr, spider.get(i).getCurD(), spider.get(i).getName());
                    StdOut.println();
                } else if (spider.get(i).getCurD() != spider.get(i).getAccD()) {
                    hmap.clear();
                    StdOut.print(curName + " " + " ");
                    BFSonNode(arr, spider.get(i).getCurD(), spider.get(i).getName());
                    BFSonNodeBackWards(arr, spider.get(i).getCurD(), spider.get(i).getName());
                    StdOut.println();
                }
            }
        }
    }

    public void BFSonNode(Node[] arr, int end, String name) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> edgeTo = new HashMap<>();
        List<Integer> path = new ArrayList<>();
        queue.add(x);
        marked.add(x);

        while (!queue.isEmpty()) {
            int v = queue.remove();
            if (v == end) {
                for (int i = end; i != x; i = edgeTo.get(i)) {
                    path.add(i);
                } 
                path.add(x);
                Collections.reverse(path);
                marked.clear();
                for (int i = 0; i < path.size(); i++) {
                    int node = path.get(i);
                    StdOut.print(node + " ");
                }
                return;
            }
            Node curr = lookup(arr, v);
            while (curr != null) {
                if (!marked.contains(curr.val)) {
                    queue.add(curr.val);
                    marked.add(curr.val);
                    edgeTo.put(curr.val, v);
                    if (curr.val == end) {
                        break;
                    }
                }
                curr = curr.next;
            }
        }
    }

    public void BFSonNodeBackWards(Node[] arr, int end, String name) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> edgeTo = new HashMap<>();
        List<Integer> path = new ArrayList<>();
        queue.add(x);
        marked.add(x);
        while (!queue.isEmpty()) {
            int v = queue.remove();

            if (v == end) {
                for (int at = end; at != x; at = edgeTo.get(at)) {
                    path.add(at);
                }
                path.add(x);
                Collections.reverse(path);
                marked.clear();
                for (int i = path.size() - 2; i >= 0; i--) {
                    int node = path.get(i);
                    StdOut.print(node + " ");
                }
                return;
            }
            Node curr = lookup(arr, v);
            while (curr != null) {
                if (!marked.contains(curr.val)) {
                    queue.add(curr.val);
                    marked.add(curr.val);
                    edgeTo.put(curr.val, v);
                    if (curr.val == end) {
                        break;
                    }
                }
                curr = curr.next;
            }
        }
    }

    public Node lookup(Node[] arr, int y) {
        for (int i = 0; i < arr.length; i++) {
            Node find = arr[i];
            if (find.val == y) {
                return find;
            }
        }
        return null;
    }

    public void printHmap(HashMap<Integer, Integer> hmap, int end) {
        ArrayList<Integer> valuesList = new ArrayList<>(hmap.values());
        int size = valuesList.size();
        for (int i = 0; i < size; i++) {
            int value = valuesList.get(i);
            StdOut.print(value + " ");
        }
        for (int i = size - 2; i >= 0; i--) {
            int value = valuesList.get(i);
            StdOut.print(value + " ");
        }
        StdOut.print(x + " ");

        return;
    }

    public void printOneway(HashMap<Integer, Integer> hmap, int end) {
        ArrayList<Integer> valuesList = new ArrayList<>(hmap.values());
        int size = valuesList.size();
        for (int i = 0; i < size; i++) {
            int value = valuesList.get(i);
            StdOut.print(value + " ");
        }
        StdOut.print(x + " ");

        return;
    }

    public static void main(String[] args) {

        if (args.length < 4) {
            StdOut.println(
                    "Execute: java -cp bin spiderman.CollectAnomalies <dimension INput file> <spiderverse INput file> <hub INput file> <collected OUTput file>");
            return;
        }

        Clusters clusters = new Clusters();
        HashTable hash = clusters.new HashTable(args[0]);
        hash.put();
        hash.wrap();
        Collider h = new Collider(args[0]);
        
        ArrayList<dimNode> spider = h.spider(args[1]);
        h.edge(hash);
        CollectAnomalies hub = new CollectAnomalies(args[2], hash.getTemp());
        hub.BFS(h.getAdjLists(), spider, args[3]);

    }
}
