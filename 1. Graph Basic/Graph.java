import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int n;
    private int[] visited;
    private List<ArrayList<Integer>> adjacencyList;
    
    public Graph(int n){
        this.n = n;
        this.visited = new int[n];
        this.adjacencyList = new ArrayList<>();
        
        for(int i = 0; i < n; i++) this.adjacencyList.add(new ArrayList<>());
    }
    
    public void addEdge(int from, int to) { this.adjacencyList.get(from).add(to); }
    
    public boolean check(){
        for(int i = 0; i < this.n; i++){
            if(this.visited[i] == 0) return false;
        }
        
        return true;
    }
  
    /*
    * Complexity: O(V(E+V))
    */
    
    public void dfs(int s){
        this.visited[s] = 1;
        
        ArrayList<Integer> edges = this.adjacencyList.get(s);
        for(int e : edges){
            if(this.visited[e] == 0) dfs(e);
        }
    }
    
    public int dfsUtil(){
        for(int i = 0; i < this.n; i++){
            if(this.visited[i] == 0) dfs(i);
            
            if(check()) return i;
            
            this.visited = new int[this.n];
        }
        
        return -1;
    }
    
    /*
    * Complexity: O(E+V)
    */
    
    public optimizedDfsUtil(){
        int root = -1;
        for(int i = 0; i < this.n; i++){
            if(this.visited[i] == 0) dfs(i);
            root = i;
        }
        
        this.visited = new int[this.n];
        dfs(root);
        
        if(check()) return 1;
        else return -1;
    }
}
