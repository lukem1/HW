//
// GraphADT
// Graph.class
//
// lukem1
// 04 November 2020
//

import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
    public byte[][] matrix; // Adjacency Matrix
    public final boolean undirected;

    Graph(int size, boolean undirected, boolean complete) {
        this.undirected = undirected;
        matrix = new byte[size][size];
        // Initialize the values of the matrix
        byte fill = 0;
        if (complete) { fill = 1; } // If specified create a complete graph by filling matrix with 1s (minus self loops)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                }
                else {
                    matrix[i][j] = fill;
                }
            }
        }
    }

    // Perform DFS on the graph, computing the DFS forest and recording the order in which vertices become dead ends
    // int[] order specifies the order in which to select vertices if restarts are needed
    // reverse specifies if the edges in the graph should be treated as reversed (ie transpose graph)
    public ArrayList<ArrayList<Integer>> dfs(int[] order, boolean reverse) {
        //System.out.println("Using order: " + Arrays.toString(order));
        boolean[] visits = new boolean[this.size()];
        Arrays.fill(visits, false);
        int[] ends = new int[this.size()];
        Arrays.fill(ends, -1);
        ArrayList<ArrayList<Integer>> forest = new ArrayList<ArrayList<Integer>>();

        while (true) {
            int nextVertex = -1;
            // If there are unvisited vertices remaining, pick the next one in the order
            for (int i = visits.length-1; i >= 0; i--) {
                int next = order[i];
                if (!visits[next]) {
                    nextVertex = next;
                    break;
                }
            }
            if (nextVertex != -1) {
                //System.out.println("Starting DFS From New Node");
                forest.add(dfsHelper(nextVertex, visits, ends, reverse));
            }
            else break; // If there are no unvisited vertices end
        }
        /*
        System.out.println("Visits:");
        System.out.println(Arrays.toString(visits));
        System.out.println("Ends:");
        System.out.println(Arrays.toString(ends));
        System.out.println("Forest:");
        System.out.println(forest.toString());
        */
        // Copy the dfs pop path into the order parameter to return
        for (int i = 0; i < ends.length; i++) {
            order[i] = ends[i];
        }
        return forest;
    }

    // Begins a 'standard' dfs (in order, not reversed)
    public int[] dfs() {
        int[] order = new int[this.size()];
        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }
        dfs(order, false);
        return order;
    }


    // Recursive helper method for dfs()
    private ArrayList<Integer> dfsHelper(int vertex, boolean[] visits, int[] ends, boolean reverse) {
        //System.out.println("Stack: " + vertex);
        ArrayList<Integer> treeNodes = new ArrayList<Integer>();
        treeNodes.add(vertex);
        visits[vertex] = true;
        // Attempt to follow an edge to another unvisited vertex
        for (int j = 0; j < this.size(); j++) {
            byte val = matrix[vertex][j];
            if (reverse) val = matrix[j][vertex];
            if (val > 0) {
                if (!visits[j]) {
                    treeNodes.addAll(dfsHelper(j, visits, ends, reverse));
                }
            }
        }
        // Done visiting the node, add to dfs ordering 'ends'
        for (int i = 0; i < ends.length; i++) {
            if (ends[i] == -1) {
                ends[i] = vertex;
                break;
            }
        }
        return treeNodes;
    }

    // Computes the strongly connected components (SCCs) of a graph using the DFS based algorithm described in the text
    // Note: SCCs only exist in digraphs, since matrices for undirected graphs can be treated as directed, the method accepts both
    public void findSCComponents() {
        //System.out.println("---Initial DFS---");
        int[] ends = dfs();
        //System.out.println("Order of Initial DFS: " + Arrays.toString(ends));
        //System.out.println("---Reversed DFS---");
        if (this.size() <= 50){
            System.out.println("SCCs: " + dfs(ends, true).toString());
        }
        else System.out.println("Found SCCs. (Printing Disabled on Large Graphs)");
    }

    // Returns the size (|V|) of the graph
    public int size() { return matrix.length; }

    // Counts and returns the number of edges in the graph
    public int countEdges() {
        int edges = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                edges += matrix[i][j];
            }
        }
        // Divide count by two if the graph is undirected
        if (this.undirected) {edges = edges / 2; }
        return edges;
    }

    // Adds an edge to the graph connecting from -> to
    // If the graph is undirected, an edge to -> from is also created
    public void addEdge(int from, int to) {
        matrix[from][to] += 1;
        if (undirected) matrix[to][from] += 1;
    }

    // Removes an edge from the graph connecting from -> to
    // If the graph is undirected, an edge to -> from is also removed
    public void removeEdge(int from, int to){
        matrix[from][to] -= 1;
        if (undirected) matrix[to][from] -= 1;
    }

    // Returns the matrix as a formatted string
    public String toString() {
        String matrixString = "";
        if (this.size() <= 50) {
            for (int i = 0; i < matrix.length; i++) {
                matrixString += "";
                for (int j = 0; j < matrix.length; j++) {
                    matrixString += Integer.toString(matrix[i][j]);
                    if (j != matrix.length - 1) matrixString += ",";
                }
                matrixString += "\n";
            }
        }
        else matrixString = "Printing Disabled for Large Graphs\n";
        return matrixString;
    }
}
