import java.util.*;

public class Graph {
    private int n, M, N, X, Y, P;
    private int[][] adjacencyList;
    private ArrayList<Edge> edgeList;
    private int[] visited;
    private int[] parent;

    public Graph(int size){
        this.n = size;
        this.adjacencyList = new int[size][size];
        this.edgeList = new ArrayList<>();
        this.parent = new int[size];
    }

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

    public void setCardinality(int M, int N, int X, int Y){
        this.M = M;
        this.N = N;
        this.X = X;
        this.Y = Y;
    }

    public void setPairs(int P) { this.P = P; }

    public void networkFlow(){
        int maxFlow = 0;
        while(bfs(0, this.n-1)){
            int flow = Integer.MAX_VALUE;
            int vertex = this.n-1;

            //finding bottleneck capacity
            while(vertex != 0){
                int p = this.parent[vertex];
                flow = Math.min(flow, this.adjacencyList[p][vertex]);
                vertex = p;
            }
            maxFlow += flow;

            vertex = this.n-1;
            while(vertex != 0){
                int p = this.parent[vertex];
                this.adjacencyList[p][vertex] -= flow;
                this.adjacencyList[vertex][p] += flow;
                vertex = p;
            }

            ArrayList<Integer> path = new ArrayList<>();
            vertex = this.n - 1;
            while(vertex != 0){
                path.add(vertex);
                vertex = this.parent[vertex];
            }
            path.add(0);
            Collections.reverse(path);

            int start = path.get(1)-1;
            int end = path.get(2)-1-this.X;
            System.out.println(start + "->" + end);
        }
    }

    public boolean bfs(int source, int sink){
        boolean found = false;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        this.visited = new int[this.n];
        this.visited[source] = 1;

        while(!queue.isEmpty()){
            int u = queue.poll();
            for(int i = 0; i < this.n; i++){
                if(visited[i] != 1 && this.adjacencyList[u][i] > 0){
                    this.parent[i] = u;
                    this.visited[i] = 1;
                    queue.add(i);

                    if(i == sink){
                        found = true;
                        break;
                    }
                }
            }
        }

        return found;
    }
}
