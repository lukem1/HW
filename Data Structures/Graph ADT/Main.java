//
// GraphADT
// Main.class
//
// lukem1
// 04 November 2020
//

public class Main {

    // This method removes 20% of edges from a complete graph so that |E| = (|V|(|V|-1))/2 * 80%
    // Note: It is assumed that the input graph is undirected and complete
    private static void trimGraph(Graph g) {
        int edgeCount = g.size() * (g.size() - 1) / 2;
        int edgeGoal = (int) (edgeCount * .8);
        int toRemove = edgeCount - edgeGoal;
        // Trim the graph starting at the upper left corner of the matrix
        // Ensures at least 1 edge is left at trimmed vertices
        for (int i = 2; i < g.size(); i++) {
            for (int j = 0; j < i - 1; j++) {
                g.removeEdge(i, j);
                toRemove -= 1;
                if (toRemove == 0) return;
            }
        }
    }

    // This method implements section 3 of the project specs
    // Given a Graph g that conforms to section 1 the nodes of g are enumerated using dfs and placed in 2 groups
    // A complete bipartite graph is then created using the 2 sets of enumerated vertices
    private static Graph enumerate(Graph g) {
        // By definition the Graph g can not be bipartite
        // The maximum number of edges in a balanced bipartite graph with vertex sets A, B is |A| * |B|
        // Spec 1 requires that the undirected graphs with vertex set V have (|V|(|V|-1))/2 * 80% edges
        // |V|/2 * |V|/2 < (|V|(|V|-1))/2 * 80% for values of v > 8/3
        int[] sets = g.dfs(); // DFS pattern can be used to determine the subsets, % 2 = 0 -> evens, % 2 != 0 -> odds
        //System.out.println("Sets: " + Arrays.toString(sets));
        // Create the complete and balanced bipartite graph using the dfs pattern
        Graph b = new Graph(g.size(), false, false);
        for (int i = 0; i < b.size(); i += 2) {
            for (int j = 1; j < b.size(); j += 2) {
                b.addEdge(sets[i], sets[j]);
                b.addEdge(sets[j], sets[i]);
            }
        }
        return b;
    }

    // Perform the 3 computations defined in the assignment for a graph of a given size
    private static void runTests(int size) {
        System.out.format("---Running tests on a graph of size %d---\n", size);
        Graph g = new Graph(size, true, true);
        trimGraph(g);
        System.out.println("Graph: ");
        System.out.print(g.toString());
        System.out.println("Computing SCCs...");
        long start = System.nanoTime();
        g.findSCComponents();
        long stop = System.nanoTime();
        System.out.format("Time to Compute SCCs: %d nanoseconds\n", stop-start);
        Graph b = enumerate(g);
        System.out.println("Bipartite Graph:");
        System.out.print(b.toString());
    }

    public static void main(String[] args) {
        System.out.println("---GraphADT---");
        
        // Testing
        /*
        Graph g = new Graph(8, false, false);
        g.addEdge(0, 1); g.addEdge(1, 6); g.addEdge(6, 5); g.addEdge(5, 0); g.addEdge(5, 1); g.addEdge(3, 1);
        g.addEdge(3, 6); g.addEdge(2, 3); g.addEdge(2, 4); g.addEdge(4, 7); g.addEdge(7, 2); g.addEdge(7, 3);
        System.out.println("Edges: " + g.countEdges());
        System.out.print(g.toString());
        System.out.println("-----");
        g.findSCComponents();
         */

        // Assignment

        // Graph of Size 10

        runTests(10);

        // Graph of Size 100

        runTests(100);

        // Graph of Size 1000

        runTests(1000);

        // Graph of Size 2000

        runTests(2000);

        // Graph of Size 5000

        runTests(5000);

        // Graph of Size 10000
        /*
        At this point attempting to compute the SCCs of the graph causes a stackoverflow due to the volume of recursive calls.
        It is worth noting that this could be improved by using a non-recursive method.
         */
        // Graph of Size 100000
        /*
        Here we begin to encounter significant time and memory issues when creating the graph.
        Using an adjacency matrix a graph of this size requires a matrix with 10 billion elements.
        In this implementation each element is a byte in size, so creating a matrix of this scale would require 10 GB of memory.
        This could certainly be improved by using bit sized elements, but this requires techniques outside the scope of this implementation.
        Additionally, even if using bit sized elements, the matrix would still be around 1.25 GB in size.
        It is also worth noting that the runtime of this program would similarly suffer, making matrices of this scale even less feasible here.
         */
        System.out.println("---Done---");
    }
}
