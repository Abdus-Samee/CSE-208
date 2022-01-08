import java.util.*;

public class Graph {
    private int n;
    private double cost = 0;
    private String[] indexToTeam;
    private int[] win, loss, remaining;
    private int[][] adjacencyList;
    private ArrayList<Edge> edgeList;
    private int[] visited;
    private int[] parent;
    private List<List<Integer>> augmentedPaths;

    //Dinic algorithm
    private int[] level;

    public Graph(int size){
        this.n = size;
        this.indexToTeam = new String[size];
        this.win = new int[size];
        this.loss = new int[size];
        this.remaining = new int[size];
        this.adjacencyList = new int[size][size];
        this.edgeList = new ArrayList<>();
        this.parent = new int[size];
        this.augmentedPaths = new ArrayList<>();

        this.level = new int[size];
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

    public void addStat(int idx, String team, int win, int loss, int remaining){
        this.indexToTeam[idx] = team;
        this.win[idx] = win;
        this.loss[idx] = loss;
        this.remaining[idx] = remaining;
    }

    public void networkFlow(int source, int sink){
        int[][] residualList = new int[this.n][];
        for(int i = 0; i < this.n; i++) residualList[i] = this.adjacencyList[i].clone();

        int maxFlow = 0;
        while(bfs(source, sink, residualList)){
            List<Integer> path = new ArrayList<>();
            int flow = Integer.MAX_VALUE;
            int vertex = sink;

            //finding bottleneck capacity
            while(vertex != source){
                path.add(vertex);
                int p = this.parent[vertex];
                flow = Math.min(flow, residualList[p][vertex]);
                vertex = p;
            }
            path.add(source);
            Collections.reverse(path);
            this.augmentedPaths.add(path);
            maxFlow += flow;

            vertex = sink;
            while(vertex != source){
                int p = this.parent[vertex];
                residualList[p][vertex] -= flow;
                residualList[vertex][p] += flow;
                vertex = p;
            }
        }

        System.out.println("Max flow is: " + maxFlow);
        printAugmentedPaths();
    }

    public boolean bfs(int source, int sink, int[][] residualList){
        boolean found = false;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        this.visited = new int[this.n];
        this.visited[source] = 1;

        while(!queue.isEmpty()){
            int u = queue.poll();
            for(int i = 0; i < this.n; i++){
                if(visited[i] != 1 && residualList[u][i] > 0){
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

    public void printAugmentedPaths(){
        System.out.println("Augmented Paths");
        for(List<Integer> path : this.augmentedPaths){
            for(int i : path) System.out.printf(i + " ");
            System.out.println();
        }
    }

    public void dinicMaxFlow(int s, int t){
        int maxFlow = 0;
        int[] count = new int[this.n];
        int[][] residualList = new int[this.n][];
        for(int i = 0; i < this.n; i++) residualList[i] = this.adjacencyList[i].clone();

        while(dinicBFS(s, t, residualList)){
            Arrays.fill(count, 0);
            for(int f = dinicDFS(s, t, count, Integer.MAX_VALUE, residualList); f != 0; f = dinicDFS(s,t, count, Integer.MAX_VALUE, residualList)){
                maxFlow += f;
            }
        }

        System.out.println("Maximum flow from Dinic's algorithm: " + maxFlow);
    }

    public boolean dinicBFS(int s, int t, int[][] residualList){
        Arrays.fill(this.level, -1);
        Deque<Integer> q = new ArrayDeque<>(this.n);
        q.offer(s);
        this.level[s] = 0;

        while(!q.isEmpty()){
            int node = q.poll();
            for(int i = 0; i < this.n; i++){
                if(node != i && residualList[node][i] > 0 && this.level[i] == -1){
                    this.level[i] = this.level[node]+1;
                    q.offer(i);
                }
            }
        }

        return this.level[t] != -1;
    }

    public int dinicDFS(int s, int t, int[] count, int flow, int[][] residualList){
        if(s == t) return flow;

        if(count[s] == residualList[s].length) return 0;

        for(int v = 0; v < this.n; v++){
            if(residualList[s][v] > 0){
                count[s]++;
                if(this.level[v] == this.level[s]+1){
                    int bottleNeck = dinicDFS(v, t, count, Math.min(flow, residualList[s][v]), residualList);
                    if(bottleNeck > 0){
                        residualList[s][v] -= bottleNeck;
                        residualList[v][s] += bottleNeck;
                        return bottleNeck;
                    }
                }
            }
        }

        return 0;
    }
}
