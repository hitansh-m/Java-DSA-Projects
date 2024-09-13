package spiderman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

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
 * Read from the SpotInputFile with the format:
 * One integer
 * i. The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * AnomaliesInputFile name is passed through the command line as args[3]
 * Read from the AnomaliesInputFile with the format:
 * 1. e (int): number of anomalies in the file
 * 2. e lines, each with:
 * i. The Name of the anomaly which will go from the hub dimension to their home
 * dimension (String)
 * ii. The time allotted to return the anomaly home before a canon event is
 * missed (int)
 * 
 * Step 5:
 * ReportOutputFile name is passed in through the command line as args[4]
 * Output to ReportOutputFile with the format:
 * 1. e Lines (one for each anomaly), listing on the same line:
 * i. The number of canon events at that anomalies home dimensionafter being
 * returned
 * ii. Name of the anomaly being sent home
 * iii. SUCCESS or FAILED in relation to whether that anomaly made it back in
 * time
 * iv. The route the anomaly took to get home
 * 
 * @author Seth Kelley
 */

public class GoHomeMachine {
    private Set<Integer> done;
    private List<Integer> fringe;
    private Map<Integer, Integer> d;
    private Map<Integer, Integer> pred;
    private Node[] arr;
    private int x;
    private int source;

    public GoHomeMachine(ArrayList<dimNode> spider, String anomInput, Node[] arr, int s, String output) {
        StdOut.setFile(output);
        this.source = s;
        StdIn.setFile(anomInput);
        x = StdIn.readInt();
        this.arr = arr;
        done = new HashSet<>();
        fringe = new ArrayList<>();
        d = new HashMap<>();
        pred = new HashMap<>();
        while (StdIn.hasNextLine()) {
            String anomName = StdIn.readString();
            int allottedTime = StdIn.readInt();
            for (int i = 0; i < spider.size(); i++) {
                if (spider.get(i).getName().equals(anomName)) {
                    StdOut.print(0 + " ");
                    StdOut.print(anomName + " ");
                    DJAlgo(source, spider.get(i).getAccD(), allottedTime);
                }
            }
        }
    }   

    public void DJAlgo(int source, int end, int allottedT) {
        List<Integer> path = new ArrayList<>();
        done.clear();
        fringe.clear();
        pred.clear();
        for (Node node : arr) {
            int v = node.val;
            if (v != source) {
                d.put(v, Integer.MAX_VALUE);
                pred.put(v, null);
            }
        }
        d.put(source, 0);
        pred.put(source, null);
        fringe.add(source);
        while (!fringe.isEmpty()) {
            int m = getMinVertex();
            done.add(m);
            fringe.remove(Integer.valueOf(m));
            Node mptr = lookup(arr, m);
            mptr = mptr.next;
            while (mptr != null) {
                if (!done.contains(mptr.val)) {
                    if (d.get(mptr.val) == Integer.MAX_VALUE) {
                        d.put(mptr.val, d.get(m) + w(m, mptr.val));
                        fringe.add(mptr.val);
                        pred.put(mptr.val, m);
                    } else if ((d.get(mptr.val)) > (d.get(m) + w(m, mptr.val))) {
                        d.put(mptr.val, d.get(m) + w(m, mptr.val));
                        pred.put(mptr.val, m);
                    }
                }
                mptr = mptr.next;
            }
        }
        int current = end;
        while (current != source && pred.get(current) != null) {
        path.add(current);
        current = pred.get(current);
        }
        path.add(source);
        int totalWeight = sumPathWeights(path);
        if (totalWeight>allottedT){
            StdOut.print("FAILED" + " ");
        } else {
            StdOut.print("SUCCESS" + " ");
        }
        for (int i = path.size() - 1; i >= 0; i--) {
        StdOut.print(path.get(i) + " ");
        }
        StdOut.println();
    }
    public int sumPathWeights(List<Integer> path) {
        int sum = 0;
        for (int i = 0; i < path.size()-1; i++) {
            int nextNode = path.get(i + 1);
            if (pred.containsKey(nextNode)) {
                sum += d.get(nextNode);
            }
        }
        return sum;
    }


    public int w(int m, int w) {
        int weight = 0;
        for (Node node : arr) {
            if (node.val == m) {
                Node cur = node.next;
                while (cur != null) {
                    if (cur.val == w) {
                        weight = cur.weight;
                        break; // Exit loop once the weight is found
                    }
                    cur = cur.next;
                }
                break; // Exit loop once the node is found
            }
        }
        return weight;
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

    private int getMinVertex() {
        int minVertex = fringe.get(0);
    int minDistance = d.get(minVertex);

    for (int v : fringe) {
        int distance = d.get(v);
        if (distance < minDistance) {
            minDistance = distance;
        }
    }
        return minVertex;
    }

   



    public static void main(String[] args) {

        if (args.length < 5) {
            StdOut.println(
                    "Execute: java -cp bin spiderman.GoHomeMachine <dimension INput file> <spiderverse INput file> <hub INput file> <anomalies INput file> <report OUTput file>");
            return;
        }
        Clusters clusters = new Clusters();
        HashTable hash = clusters.new HashTable(args[0]);
        hash.put();
        hash.wrap();
        Collider h = new Collider(args[0]);
        h.edge(hash);
        ArrayList<dimNode> spider = h.spider(args[1]);
        CollectAnomalies hub = new CollectAnomalies(args[2], hash.getTemp());
        GoHomeMachine p = new GoHomeMachine(spider, args[3], h.getAdjLists(), hub.getX(), args[4]);

    }
}
