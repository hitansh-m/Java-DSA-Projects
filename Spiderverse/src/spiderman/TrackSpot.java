package spiderman;

import java.util.ArrayList;


import spiderman.Clusters.HashTable;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table prior to rehashing
 *      iii.  c (double): the capacity(threshold) used to rehash the cluster table 
 * 2. a lines, each with:
 *      i.    The dimension number (int)
 *      ii.   The number of canon events for the dimension (int)
 *      iii.  The dimension weight (int)
 * 
 * Step 2:
 * SpiderverseInputFile name is passed through the command line as args[1]
 * Read from the SpiderverseInputFile with the format:
 * 1. d (int): number of people in the file
 * 2. d lines, each with:
 *      i.    The dimension they are currently at (int)
 *      ii.   The name of the person (String)
 *      iii.  The dimensional signature of the person (int)
 * 
 * Step 3:
 * SpotInputFile name is passed through the command line as args[2]
 * Read from the SpotInputFile with the format:
 * Two integers (line seperated)
 *      i.    Line one: The starting dimension of Spot (int)
 *      ii.   Line two: The dimension Spot wants to go to (int)
 * 
 * Step 4:
 * TrackSpotOutputFile name is passed in through the command line as args[3]
 * Output to TrackSpotOutputFile with the format:
 * 1. One line, listing the dimenstional number of each dimension Spot has visited (space separated)
 * 
 * @author Seth Kelley
 */

public class TrackSpot {
    private ArrayList<Integer> marked;
    private int Start;
    private int End;
    
    public int getStart() {
        return Start;
    }

    public int getEnd() {
        return End;
    }

    public TrackSpot(String spotFile){
        StdIn.setFile(spotFile);
         Start = StdIn.readInt();
         End = StdIn.readInt(); 
         marked = new ArrayList<Integer>();
    }

    public void DFS(Node[] arr, int x){
        marked.add(x);  
            Node curr = lookup(arr, x);
            while (curr!=null){
                if (marked.contains(End)){
                    return;
                }
                else if (!marked.contains(curr.val)){
                StdOut.print(curr.val + " ");
                DFS(arr, curr.val);
                }
                curr = curr.next;
            }
        }

        public Node lookup(Node[] arr, int y){
            for (int i = 0; i<arr.length; i++){
                Node find = arr[i];
                if (find.val == y){
                    return find;
                }
            }
            return null;
        }
    public void DFSHelper(Node[] arr, int x, String output){
        StdOut.setFile(output);
        StdOut.print(x + " ");
        DFS(arr, x);
    }
    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.TrackSpot <dimension INput file> <spiderverse INput file> <spot INput file> <trackspot OUTput file>");
                return;
        }
        Clusters clusters = new Clusters();
        HashTable hash = clusters.new HashTable(args[0]);
        hash.put();
        hash.wrap();
        Collider h = new Collider(args[0]);
        h.edge(hash);
        TrackSpot t = new TrackSpot(args[2]);
        t.DFSHelper(h.getAdjLists(), t.getStart(), args[3]);

    }
}
