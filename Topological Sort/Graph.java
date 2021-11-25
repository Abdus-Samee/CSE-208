import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int n;
    private List<ArrayList<Integer>> adjacencyList;
    private int[] count;
    private List<Integer> s;

    public Graph(int size){
        this.n = size;
        this.adjacencyList = new ArrayList<>();
        this.count = new int[size+1];
        this.s = new ArrayList<>();

        for(int i = 0; i <= size; i++) this.adjacencyList.add(new ArrayList<>());
    }

    public void addEdge(int from, int to){
        this.adjacencyList.get(from).add(to);
        count[to]++;
    }

    public void traverse(){
        for(int i = 1; i <= this.n; i++){
            if(this.count[i] == 0) this.s.add(i);
        }

        ArrayList<Integer> ans = new ArrayList<>();

        while(!this.s.isEmpty()){
            int node = s.remove(0);
            ans.add(node);

            ArrayList<Integer> edgeList = this.adjacencyList.get(node);
            for(int i = 0; i < edgeList.size(); i++){
                this.count[edgeList.get(i)]--;
                if(this.count[edgeList.get(i)] == 0) this.s.add(edgeList.get(i));
            }
        }

        if(ans.size() != this.n) System.out.println("Not possible");
        else{
            for(int i : ans) System.out.print(i + " ");
        }
    }
}
