import java.util.*;

public class Graph {
    private int n;
    private double cost = 0;
    private List<ArrayList<Edge>> adjacencyList;
    private ArrayList<Edge> edgeList;
    private IPQ<Integer> ipq;
    private int[] dist;
    private int[] d;
    private int[] parent;
    private boolean[] visited;

    public Graph(int size){
        this.n = size;
        this.adjacencyList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
        this.dist = new int[size];
        this.d = new int[size];
        this.visited = new boolean[size];
        this.parent = new int[size];
        int degree = (int)Math.ceil(Math.log(size)/Math.log(2));
        this.ipq = new IPQ<>(Math.max(2, degree), size);

        for(int i = 0; i < size; i++) this.adjacencyList.add(new ArrayList<>());
    }

    public double getCost() { return this.cost; }

    public class Edge implements Comparable{
        private int u;
        private int v;
        private int w;

        public Edge(int u, int v, int w){
            this.u = u;
            this.v = v;
            this.w = w;
        }

        public int getWeight(){ return  this.w; }

        public int either(){ return this.u; }

        public int other(int node){
            if(node == this.u) return this.v;

            return this.u;
        }

        @Override
        public int compareTo(Object o) {
            Edge edge = (Edge) o;
            int u = edge.either();
            int v = edge.other(u);

            if (this.w < edge.w) return -1;
            else if (this.w > edge.w) return 1;
            else{
                if(this.u < u) return -1;
                else if(this.u > u) return 1;
                else if(this.v < v) return -1;
                else if(this.v > v) return 1;
                else return 0;
            }
        }
    }

    public void addEdge(int from, int to, int weight){
        Edge edge = new Edge(from, to, weight);
        this.adjacencyList.get(from).add(edge);
        this.edgeList.add(edge);
    }

    public void dijkstra(int s, int d){
        List<Integer> path = new LinkedList<>();
        this.parent[0] = -1;
        ipq.insert(s, 0);

        while(!ipq.isEmpty()){
            int destNodeIndex = ipq.peekMinKeyIndex();
            int value = ipq.pollMinValue();

            this.dist[destNodeIndex] = value;
            relaxEdgesAtNode(destNodeIndex);
        }

        System.out.println("Shortest path cost: " + this.dist[d]);

        int i = d;
        path.add(0, d);
        while(this.parent[i] != -1){
            int p = this.parent[i];
            path.add(0, p);
            i = p;
        }

        for(int j = 0; j < path.size(); j++){
            if(path.get(j) != d) System.out.printf(path.get(j) + " -> ");
            else System.out.printf(d + "\n");
        }
    }

    private void relaxEdgesAtNode(int currentNodeIndex){
        this.visited[currentNodeIndex] = true;
        List<Graph.Edge> edges = this.adjacencyList.get(currentNodeIndex);

        for(Graph.Edge edge : edges){
            int destNodeIndex = edge.other(currentNodeIndex);
            if(this.visited[destNodeIndex]) continue;

            if(this.ipq.contains(destNodeIndex)) this.ipq.decrease(destNodeIndex, this.dist[currentNodeIndex]+edge.w);
            else this.ipq.insert(destNodeIndex, this.dist[currentNodeIndex]+edge.w);

            this.parent[destNodeIndex] = currentNodeIndex;
        }
    }

    public void bellmanFord(int s, int d){
        for(int i = 0; i < this.n; i++) this.d[i] = Integer.MAX_VALUE;
        this.d[s] = 0;
        this.parent[s] = -1;

        for(int i = 0; i < this.n-1; i++){
            for(Edge edge : this.edgeList) bellmanFordRelax(edge);
        }

        boolean negativeCycle = false;
        for(Edge edge : this.edgeList){
            int u = edge.either();
            int v = edge.other(u);
            if(this.d[v] > this.d[u]+edge.w){
                negativeCycle = true;
                break;
            }
        }

        if(negativeCycle) System.out.println("The graph contains a negative cycle");
        else{
            System.out.println("The graph does not contain a negative cycle");
            System.out.println("Shortest path cost: " + this.d[d]);

            int i = d;
            List<Integer> path = new LinkedList<>();
            path.add(0, d);
            while(this.parent[i] != -1){
                int p = this.parent[i];
                path.add(0, p);
                i = p;
            }

            for(int j = 0; j < path.size(); j++){
                if(path.get(j) != d) System.out.printf(path.get(j) + " -> ");
                else System.out.printf(d + "\n");
            }
        }
    }

    public void bellmanFordRelax(Edge edge){
        int u = edge.either();
        int v = edge.other(u);

        if(this.d[u] != Integer.MAX_VALUE && this.d[v] > this.d[u]+edge.w){
            this.d[v] = this.d[u] + edge.w;
            this.parent[v] = u;
        }
    }
}
