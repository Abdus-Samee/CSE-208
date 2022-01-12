import java.util.*;

public class BaseballLeague {
    private int idx, tot, satFlow;
    private int[] parent;
    private int[] visited;
    private String[] idxToNode;
    private int[][] g;

    public BaseballLeague(int idx, int tot, int temp){
        this.idx = idx;
        this.tot = tot;
        this.visited = new int[tot];
        this.parent = new int[tot];
        this.g = new int[tot][tot];
        this.idxToNode = new String[temp+1];
    }

    public void addEdge(int from, int to, int weight){
        this.g[from][to] = weight;
    }

    public void addIdxToNode(int idx, String str) { this.idxToNode[idx] = str; }

    public String getIdxToNode(int idx) { return this.idxToNode[idx]; }

    public void setSatFlow(int satFlow) { this.satFlow = satFlow; }

    public int getVal(int r, int c) { return this.g[r][c]; }

    public boolean networkFlow(){
        int maxFlow = 0;
        while(bfs(0, tot-1, g)){
            int flow = Integer.MAX_VALUE;
            int vertex = tot-1;

            //finding bottleneck capacity
            while(vertex != 0){
                int p = this.parent[vertex];
                flow = Math.min(flow, g[p][vertex]);
                vertex = p;
            }
            maxFlow += flow;

            vertex = tot-1;
            while(vertex != 0){
                int p = this.parent[vertex];
                g[p][vertex] -= flow;
                g[vertex][p] += flow;
                vertex = p;
            }
        }

        //System.out.println("Max flow for satFlow: " + this.satFlow + ", is: " + maxFlow);
        return this.satFlow == maxFlow;
    }

    public boolean bfs(int source, int sink, int[][] g){
        boolean found = false;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        this.visited = new int[this.tot];
        this.visited[source] = 1;

        while(!queue.isEmpty()){
            int u = queue.poll();
            for(int i = 0; i < this.tot; i++){
                if(visited[i] != 1 && g[u][i] > 0){
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
