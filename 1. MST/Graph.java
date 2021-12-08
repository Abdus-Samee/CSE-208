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

    public void addEdge(int from, int to, double weight){
        Edge edge = new Edge(from, to, weight);
        this.adjacencyList.get(from).add(edge);
        this.adjacencyList.get(to).add(edge);
        this.edgeList.add(edge);
    }

    public void visit(int s){
        this.visited[s] = 1;
        ArrayList<Edge> edges =this.adjacencyList.get(s);
        for(Edge edge : edges){
            if(this.visited[edge.other(s)] == 0) this.queue.add(edge);
        }
    }

    public ArrayList<Edge> mst(int s){
        this.visited[s] = 1;
        ArrayList<Edge> ans = new ArrayList<>();
        ArrayList<Edge> edges = this.adjacencyList.get(s);
        for(Edge e : edges) this.queue.add(e);

        while(!this.queue.isEmpty() && ans.size() < this.n){
            Edge edge = this.queue.peek();
            this.queue.remove();
            int u = edge.either();
            int v = edge.other(u);
            if(this.visited[u] == 1 && this.visited[v] ==1) continue;

            ans.add(edge);
            this.cost += edge.w;

            if(this.visited[u] == 0) visit(u);
            if(this.visited[v] == 0) visit(v);
        }

        return ans;
    }

    public ArrayList<Edge> kruskal(){
        ArrayList<Edge> ans = new ArrayList<>();
        Collections.sort(this.edgeList);

        while(!this.edgeList.isEmpty() && ans.size() < this.n-1){
            Edge edge = this.edgeList.remove(0);
            int u = edge.either();
            int v = edge.other(u);

            if(!this.ds.isConnected(u, v)){
                this.ds.union(u, v);
                ans.add(edge);
            }
        }

        return ans;
    }

    public void eagerPrim(){
        EagerPrim eagerPrim = new EagerPrim(this.adjacencyList);

        if(!eagerPrim.mstExists()) System.out.println("No MST exists...");
        else{
            System.out.println("MST cost: " + eagerPrim.getMstCost());
            System.out.println("MST edges:");
            for(Edge e : eagerPrim.getMst()){
                System.out.println(String.format("(%d %d %f)", e.u, e.v, e.w));
            }
        }
    }
}
