import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int n;
    private List<ArrayList<Integer>> adjacencyList;
    private int[] visited;

    public Graph(int size){
        this.n = size;
        this.adjacencyList = new ArrayList<>();
        this.visited = new int[size];

        for(int i = 0; i <= size; i++) this.adjacencyList.add(new ArrayList<>());
    }

    public void addEdge(int from, int to){
        this.adjacencyList.get(from).add(to);
        this.adjacencyList.get(to).add(from);
    }

    public boolean isConnected(int s){
        dfs(s);

        for(int i = 0; i < this.n; i++){
            if(visited[i] == 0) return false;
        }

        return true;
    }

    public void dfs(int i){
        this.visited[i] = 1;
        ArrayList<Integer> edges = this.adjacencyList.get(i);
        for(int e : edges){
            if(visited[e] == 0) dfs(e);
        }

        this.visited[i] = 2;
    }
}