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
    }

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

    public void baseballMaxFlow(){
        int temp = (this.n-2)*(this.n-1)/2;
        for(int i = 0; i < this.n; i++){
            BaseballLeague baseballLeague = new BaseballLeague(i, temp+2+this.n-1, temp);

            int c = 1, satFlow = 0;
            for(int j = 0; j < this.n; j++){
                for(int k = j+1; k < this.n; k++){
                    if(j==i || k==i) continue;
                    else{
                        baseballLeague.addEdge(0, c, this.adjacencyList[j][k]);
                        baseballLeague.addIdxToNode(c, j+"-"+k);

                        if (j - i < 0) baseballLeague.addEdge(c, temp + 1 + j, Integer.MAX_VALUE);
                        else baseballLeague.addEdge(c, temp + j, Integer.MAX_VALUE);

                        if(k - i < 0) baseballLeague.addEdge(c, temp + 1 + k, Integer.MAX_VALUE);
                        else baseballLeague.addEdge(c, temp + k, Integer.MAX_VALUE);

                        satFlow += this.adjacencyList[j][k];
                        c++;
                    }
                }
            }

            baseballLeague.setSatFlow(satFlow);

            int k = 1;
            for(int j = 0; j < this.n; j++){
                if(j != i){
                    if(this.win[j] <= this.win[i]+this.remaining[i]){
                        baseballLeague.addEdge(temp+k, temp+this.n, this.win[i]+this.remaining[i]-this.win[j]);
                        k++;
                    }
                }
            }

            if(!baseballLeague.networkFlow()){
                List<Integer> vertices = new ArrayList<>();
                for(int m = 1; m <= temp; m++){
                    if(baseballLeague.getVal(0, m) > 0){
                        String[] str = baseballLeague.getIdxToNode(m).split("-");
                        if(!vertices.contains(Integer.parseInt(str[0]))) vertices.add(Integer.parseInt(str[0]));
                        if(!vertices.contains(Integer.parseInt(str[1]))) vertices.add(Integer.parseInt(str[1]));
                    }
                }

                int totalWins = 0;
                int teamsLen = 0;
                String teams = "";
                for(int m = 0; m < vertices.size()-1; m++){
                    totalWins += this.win[vertices.get(m)];
                    teams += this.indexToTeam[vertices.get(m)] + "," ;
                    teamsLen++;
                }
                teams += this.indexToTeam[vertices.get(vertices.size()-1)];
                totalWins += this.win[vertices.get(vertices.size()-1)];
                teamsLen++;

                int playEachOther = 0;
                for(int m = 0; m < vertices.size(); m++){
                    for(int l = m+1; l < vertices.size(); l++){
                        playEachOther += this.adjacencyList[vertices.get(m)][vertices.get(l)];
                    }
                }

                System.out.println(this.indexToTeam[i] + " is eliminated.");
                System.out.println("They can win at most " + (this.win[i]+this.remaining[i]) + " games.");
                System.out.println(teams + " have won a total of " + totalWins + " games.");
                System.out.println("They play each other " + playEachOther + " times.");
                System.out.println("So on average, each of the teams wins " + (totalWins+playEachOther) + "/" + teamsLen + " = " + ((double)(totalWins+playEachOther)/teamsLen) + " games.");
                System.out.println();
            }
        }
    }
}
