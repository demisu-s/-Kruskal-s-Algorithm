public class Main {
    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph(4);
        graph.insertEdge(0, 1, 7.2);
        graph.insertEdge(0, 2, 7.5);
        graph.insertEdge(0, 3, 7.3);
        graph.insertEdge(1, 2, 7.6);
        graph.insertEdge(1, 3, 7.4);
        graph.insertEdge(2, 3, 7.7);
        System.out.println("The minimum spanning tree is:"+ graph.minimumSpanningTree()); // Returns the minimum spanning tree weight: 22.0
        System.out.println("Number of Edge is:"+graph.edgeCount()); // returns the number of edges: 6
        graph.insertEdge(0, 2, 7.5); // Returns true
        graph.insertEdge(0, 2, 7.8); // Returns true
        System.out.println("The minimum spanning tree after insertion is:"+ graph.minimumSpanningTree()); // Returns the minimum spanning tree weight: 22.1
        System.out.println("Number of Edge:"+graph.edgeCount()); // returns the number of edges: 6
    }
}
