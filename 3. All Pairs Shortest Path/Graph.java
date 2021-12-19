import java.util.*;

public class Graph {
    private int n;
    private double cost = 0;
    private int[][] adjacencyList;
    private PriorityQueue<Edge> queue;
    private ArrayList<Edge> edgeList;
    private int[] visited;

    public Graph(int size){
        this.n = size+1;
        this.adjacencyList = new int[size+1][size+1];
        this.queue = new PriorityQueue<>();
        this.edgeList = new ArrayList<>();
        this.visited = new int[size+1];

        for(int i = 0; i <= size; i++) Arrays.fill(this.adjacencyList[i], Integer.MAX_VALUE);
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

    public void addEdge(int from, int to, int weight){
        this.adjacencyList[from][to] = weight;
        Edge edge = new Edge(from, to, weight);
        this.edgeList.add(edge);
    }

    public void allPairShortestDistance(){
        for(int k = 1; k < this.n; k++){
            for(int i = 1; i < this.n; i++){
                for(int j = 1; j < this.n; j++){
                    if(i == j) this.adjacencyList[i][j] = 0;
                    else if(notUndefined(i,k, j)){
                        this.adjacencyList[i][j] = Math.min(this.adjacencyList[i][j], this.adjacencyList[i][k]+this.adjacencyList[k][j]);
                    }
                }
            }
        }
    }

    public boolean notUndefined(int i, int k, int j){
        if(this.adjacencyList[i][k]==Integer.MAX_VALUE || this.adjacencyList[k][j]==Integer.MAX_VALUE) return false;

        return true;
    }

    public void printMatrix(){
        for(int i = 1; i < this.n; i++){
            for(int j = 1; j <  this.n; j++){
                if(this.adjacencyList[i][j] == Integer.MAX_VALUE) System.out.printf("INF ");
                else System.out.printf(this.adjacencyList[i][j] + " ");
            }
            System.out.println();
        }
    }
}
