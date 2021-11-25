import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Graph {
    private int n;
    private List<ArrayList<Integer>> adjacencyList;
    private int[] visited;
    private int[] colour;

    public Graph(int size){
        this.n = size;
        this.adjacencyList = new ArrayList<>();
        this.visited = new int[size];
        this.colour = new int[size];

        for(int i = 0; i <= size; i++) this.adjacencyList.add(new ArrayList<>());
    }

    public void addEdge(int from, int to){
        this.adjacencyList.get(from).add(to);
        this.adjacencyList.get(to).add(from);
    }

    public boolean isBipartite(int s){
        this.visited[s] = 1;
        this.colour[s] = 1;
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(s);

        while(!queue.isEmpty()){
            int node = queue.remove(0);
            ArrayList<Integer> edges = this.adjacencyList.get(node);
            for(int i : edges){
                if(this.colour[node] == this.colour[i]) return false;
                else if(this.visited[i] == 0){
                    this.visited[i] = 1;
                    this.colour[i] = 3-this.visited[node];
                    queue.add(i);
                }
            }
            this.visited[node] = 2;
        }

        return true;
    }
}