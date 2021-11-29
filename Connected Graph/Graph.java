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
    
    /*
    *call this from within a loop to get all the connected components
    */
    public void connectedComponent(int s){
        this.visited = new int[this.n];
        ArrayList<Integer> queue = new ArrayList<>();
        ArrayList<Integer> ans = new ArrayList<>();
        queue.add(s);

        while(!queue.isEmpty()){
            int node = queue.remove(0);

            if(this.visited[node] == 0){
                this.visited[node] = 1;
                ans.add(node);
            }

            ArrayList<Integer> edges = this.adjacencyList.get(node);
            for(int i : edges){
                if(this.visited[i] == 0) queue.add(i);
            }
        }

        System.out.println("Connected components");
        for(int i : ans) System.out.printf(" " + i);
    }
}
