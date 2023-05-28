import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class WeightedGraph {
    private int n; // Number of vertices
    private List<Edge>[] adjacencyList; // Adjacency list representation of the graph

    public WeightedGraph(int n) //constructor
    {
        this.n = n;
        adjacencyList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    public int degree(int i) {
        validateVertex(i);
        return adjacencyList[i].size();
    }

    public int edgeCount() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            count += adjacencyList[i].size();
        }
        return count / 2; // Divide by 2 since it's an undirected graph
    }

    public double minimumSpanningTree() {
        List<Edge> mst = new ArrayList<>(); // Minimum spanning tree
        UnionFind unionFind = new UnionFind(n);
        PriorityQueue<Edge> edges = getAllEdges(); // Get all the edges in the graph

        int edgeCount = 0;
        double mstWeight = 0.0;

        while (!edges.isEmpty() && edgeCount < n - 1) {
            Edge edge = edges.poll();
            int source = edge.source;
            int destination = edge.destination;

            if (!unionFind.connected(source, destination)) {
                unionFind.union(source, destination);
                mst.add(edge);
                mstWeight += edge.weight;
                edgeCount++;
            }
        }

        return mstWeight;
    }

    public boolean insertEdge(int i, int j, double weight) {
        validateVertices(i, j);
        if (i == j) {
            return false; // Self-loop not allowed
        }

        List<Edge> edges = adjacencyList[i];
        for (Edge edge : edges) {
            if (edge.destination == j) {
                edge.weight = weight; // Update existing edge weight
                return true;
            }
        }

        edges.add(new Edge(j, weight));
        adjacencyList[j].add(new Edge(i, weight)); // Add the edge in both directions
        return true;
    }

    public boolean eraseEdge(int i, int j) {
        validateVertices(i, j);

        List<Edge> edges = adjacencyList[i];
        for (int k = 0; k < edges.size(); k++) {
            if (edges.get(k).destination == j) {
                edges.remove(k);
                break;
            }
        }

        edges = adjacencyList[j];
        for (int k = 0; k < edges.size(); k++) {
            if (edges.get(k).destination == i) {
                edges.remove(k);
                break;
            }
        }

        return true;
    }

    public void clearEdges() {
        for (int i = 0; i < n; i++) {
            adjacencyList[i].clear();
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= n) {
            throw new IllegalArgumentException("Vertex " + v + " is out of range.");
        }
    }

    private void validateVertices(int v1, int v2) {
        validateVertex(v1);
        validateVertex(v2);
    }

    private PriorityQueue<Edge> getAllEdges() {
        PriorityQueue<Edge> edges = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));
        for (int i = 0; i < n; i++) {
            edges.addAll(adjacencyList[i]);
        }
        return edges;
    }

    private class Edge {
        int source;
        int destination;
        double weight;

        Edge(int destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    private class UnionFind {
        int[] parent;
        int[] rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }

        boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }
}
