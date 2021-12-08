import java.util.ArrayList;
import java.util.List;

public class EagerPrim {
    private int n;
    private List<ArrayList<Graph.Edge>> graph;

    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private IPQ<Graph.Edge> ipq;

    private double minCostSum = 0.0;
    private Graph.Edge[] mstEdges;

    public EagerPrim(List<ArrayList<Graph.Edge>> graph){
        this.n = graph.size();
        this.graph = graph;
    }

    public Graph.Edge[] getMst() {
        solve();
        return mstExists ? mstEdges : null;
    }

    public Double getMstCost() {
        solve();
        return mstExists ? minCostSum : null;
    }

    public boolean mstExists(){
        solve();
        return mstExists;
    }

    public void solve(){
        if(solved) return;
        solved = true;

        int m = n-1, edgeCount = 0;
        visited = new boolean[n];
        mstEdges = new Graph.Edge[m];

        int degree = (int)Math.ceil(Math.log(n)/Math.log(2));
        ipq = new IPQ<>(Math.max(2, degree), n);

        relaxEdgesAtNode(0);

        while(!ipq.isEmpty() && edgeCount != m){
            int destNodeIndex = ipq.peekMinKeyIndex();
            Graph.Edge edge = ipq.pollMinValue();

            mstEdges[edgeCount++] = edge;
            minCostSum += edge.getWeight();
            relaxEdgesAtNode(destNodeIndex);
        }

        mstExists = (edgeCount == m);
    }

    private void relaxEdgesAtNode(int currentNodeIndex){
        visited[currentNodeIndex] = true;
        List<Graph.Edge> edges = graph.get(currentNodeIndex);

        for(Graph.Edge edge : edges){
            int destNodeIndex = edge.other(currentNodeIndex);
            if(visited[destNodeIndex]) continue;

            if(ipq.contains(destNodeIndex)) ipq.decrease(destNodeIndex, edge);
            else ipq.insert(destNodeIndex, edge);
        }
    }
}
