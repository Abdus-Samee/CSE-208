import java.util.*;

public class Graph {
    private int n;
    private double cost = 0;
    private List<ArrayList<Edge>> adjacencyList;
    private PriorityQueue<Edge> queue;
    private ArrayList<Edge> edgeList;
    private DisjointSet ds;
    private int[] visited;

    public Graph(int size){
        this.n = size;
        this.adjacencyList = new ArrayList<>();
        this.queue = new PriorityQueue<>();
        this.edgeList = new ArrayList<>();
        this.ds = new DisjointSet(size);
        this.visited = new int[size];

        for(int i = 0; i < size; i++) this.adjacencyList.add(new ArrayList<>());
    }

    public double getCost() { return this.cost; }

    public class Edge implements Comparable{
        private int u;
        private int v;
        private double w;

        public Edge(int u, int v, double w){
            this.u = u;
            this.v = v;
            this.w = w;
        }

        public double getWeight(){ return  this.w; }

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

            if (this.w > edge.w) return -1;
            else if (this.w < edge.w) return 1;
            else{
                if(this.u < u) return -1;
                else if(this.u > u) return 1;
                else if(this.v < v) return -1;
                else if(this.v > v) return 1;
                else return 0;
            }
        }
    }

    public void addEdge(int from, int to, double weight){
        Edge edge = new Edge(from-1, to-1, weight);
        this.adjacencyList.get(from-1).add(edge);
        this.adjacencyList.get(to-1).add(edge);
        this.edgeList.add(edge);
    }

    public void visit(int s){
        this.visited[s] = 1;
        ArrayList<Edge> edges =this.adjacencyList.get(s);
        for(Edge edge : edges){
            if(this.visited[edge.other(s)] == 0) this.queue.add(edge);
        }
    }

    public int mst(int s){
        this.visited[s] = 1;
        ArrayList<Edge> manEdges = new ArrayList<>();
        ArrayList<Edge> edges1 = this.adjacencyList.get(s);
        for(Edge e : edges1) if(e.w != 2) this.queue.add(e);

        while(!this.queue.isEmpty() && manEdges.size() < this.n){
            Edge edge = this.queue.peek();
            this.queue.remove();
            int u = edge.either();
            int v = edge.other(u);
            if(this.visited[u] == 1 && this.visited[v] ==1) continue;

            if(edge.w != 2){
                manEdges.add(edge);

                if(this.visited[u] == 0) visit(u);
                if(this.visited[v] == 0) visit(v);
            }
        }

        this.visited = new int[this.n+1];
        this.visited[s] = 1;
        ArrayList<Edge> womanEdges = new ArrayList<>();
        ArrayList<Edge> edges2 = this.adjacencyList.get(s);
        for(Edge e : edges2){
            if(e.w != 1) this.queue.add(e);
        }

        while(!this.queue.isEmpty() && manEdges.size() < this.n){
            Edge edge = this.queue.peek();
            this.queue.remove();
            int u = edge.either();
            int v = edge.other(u);
            if(this.visited[u] == 1 && this.visited[v] ==1) continue;

            if(edge.w != 1){
                womanEdges.add(edge);

                if(this.visited[u] == 0) visit(u);
                if(this.visited[v] == 0) visit(v);
            }
        }

        ArrayList<Edge> ans = new ArrayList<>();
        int[] total = new int[this.n+1];
        for(int i = 0; i < this.edgeList.size(); i++){
            Edge edge = this.edgeList.get(i);
            int u = edge.either();
            int v = edge.other(u);
            if(manEdges.contains(edge) && womanEdges.contains(edge)){
                ans.add(edge);
                total[u] = 2;
                total[v] = 2;
            }
        }

        int res = ans.size();
        for(int i = 1; i <= this.n; i++){
            if(total[i-1] == 0) res += 2;
            else if(total[i-1] == 1) res += 1;
        }
        return this.edgeList.size() - res;
    }
}
