import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    private int n;
    private List<ArrayList<Integer>> adjacencyList;
    private ArrayList<ArrayList<Integer>> reverseAdjList;
    private Stack<Integer> myStack;
    private int[] visited;
    private int[] colour;

    public Graph(int size){
        this.n = size;
        this.adjacencyList = new ArrayList<>();
        this.myStack = new Stack<>();
        this.visited = new int[size];
        this.colour = new int[size];

        for(int i = 0; i <= size; i++) this.adjacencyList.add(new ArrayList<>());
    }

    public void addEdge(int from, int to){
        this.adjacencyList.get(from).add(to);
    }

    public boolean checkVisit(){
        for(int i = 0; i < this.n; i++){
            if(this.visited[i] == 0) return false;
        }

        return true;
    }

    public void bfs(int s){
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(s);

        while(!queue.isEmpty()){
            int node = queue.remove(0);
            if(this.visited[node] == 0){
                this.visited[node] = 1;
                ArrayList<Integer> edges = this.adjacencyList.get(node);
                for(int i : edges){
                    if(this.visited[i] == 0) queue.add(i);
                }
            }
        }
    }

    public boolean isStronglyConnected(int s){
        bfs(s);

        if(checkVisit()){
            this.visited = new int[this.n];
            this.reverseAdjList = new ArrayList<>();
            for(int i = 0; i < this.n; i++) reverseAdjList.add(new ArrayList<>());

            for(int i = 0; i < this.n; i++){
                ArrayList<Integer> temp = this.adjacencyList.get(i);
                for(int k : temp) reverseAdjList.get(k).add(i);
            }

            bfs(s);

            return checkVisit();
        }

        return false;
    }
    
    public void dfs1(int s){
        this.visited[s] = 1;
        ArrayList<Integer> adj = this.adjacencyList.get(s);
        for(int i : adj){
            if(this.visited[i] == 0) dfs1(i);
        }
        
        myStack.push(s);
    }
    
    public void dfs2(int s){
        System.out.printf(s + " ");
        this.visited[s] = 1;
        ArrayList<Integer> adj = this.adjacencyList.get(s);
        for(int i : adj){
            if(this.visited[i] == 0) dfs2(i);
        }
    }
    
    public void stronglyConnectedComponent(){
        this.visited = new int[this.n];
        for(int i = 0; i < this.n; i++){
            if(this.visited[i] == 0) dfs1(i);
        }
        
        this.visited = new int[this.n];
        this.reverseAdjList = new ArrayList<>();
        for(int i = 0; i < this.n; i++) reverseAdjList.add(new ArrayList<>());

        for(int i = 0; i < this.n; i++){
            ArrayList<Integer> temp = this.adjacencyList.get(i);
            for(int k : temp) reverseAdjList.get(k).add(i);
        }
        
        int count = 1;
        while(!myStack.empty()){
            int node = myStack.peek();
            myStack.pop();
            if(this.visited[node] == 0){
                System.out.println("SCC " + count++ + ":");
                dfs2(node);
                System.out.println();
            }
        }
    }
}
