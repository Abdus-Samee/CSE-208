import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            int c = 0, n = 0, m = 0;
            Graph g = null;
            BufferedReader reader = new BufferedReader(new FileReader("mst.in"));

            while(true){
                String in = reader.readLine();
                if(in == null) break;

                String[] input = in.split(" ");

                if(c == 0){
                    n = Integer.parseInt(input[0]);
                    m = Integer.parseInt(input[1]);
                    g = new Graph(n);
                    c++;
                }else{
                    int u = Integer.parseInt(input[0]);
                    int v = Integer.parseInt(input[1]);
                    double w = Double.parseDouble(input[2]);

                    g.addEdge(u, v, w);
                }
            }

            reader.close();

            ArrayList<Graph.Edge> ansPrim = g.mst(0);
            System.out.println("Cost of the minimum spanning tree: " + g.getCost());
            System.out.printf("List of edges selected by Prim's: { ");
            for(Graph.Edge e : ansPrim){
                int u = e.either();
                int v = e.other(u);
                System.out.printf("(" + u + ", " + v + ") ");
            }
            System.out.printf("}\n");

            ArrayList<Graph.Edge> ansKruskal = g.kruskal();
            System.out.printf("List of edges selected by Kruskal's: { ");
            for(Graph.Edge e : ansKruskal){
                int u = e.either();
                int v = e.other(u);
                System.out.printf("(" + u + ", " + v + ") ");
            }
            System.out.printf("}\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
