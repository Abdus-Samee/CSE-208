public class DisjointSet {
    private int n;
    private int[] rank;
    private int[] parent;

    public DisjointSet(int n){
        this.n = n;
        this.rank = new int[n];
        this.parent = new int[n];
        makeSet();
    }

    public void makeSet(){
        for(int i = 0; i < this.n; i++) this.parent[i] = i;
    }

    public int find(int s){
        if(this.parent[s] != s){
            this.parent[s] = find(this.parent[s]);
        }

        return this.parent[s];
    }

    public void union(int a, int b){
        int parentA = find(a);
        int parentB = find(b);

        if(this.rank[parentA] > this.rank[parentB]) this.parent[parentB] = parentA;
        else if(this.rank[parentA] < this.rank[parentB]) this.parent[parentA] = parentB;
        else{
            this.parent[parentB] = parentA;
            this.rank[parentA]++;
        }
    }

    public boolean isConnected(int a, int b) { return (this.parent[a] == this.parent[b])? true : false; }
}
