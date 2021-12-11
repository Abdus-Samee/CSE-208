import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            int c = 0, n = 0, m = 0, s = 0, d = 0;
            boolean dijkstra = true;
            Graph g = null;
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

            while(true){
                String in = reader.readLine();
                if(in == null) break;

                String[] input = in.split(" ");

                if(c == 0){
                    n = Integer.parseInt(input[0]);
                    m = Integer.parseInt(input[1]);
                    g = new Graph(n);
                    c++;
                }else if(c <= m){
                    int u = Integer.parseInt(input[0]);
                    int v = Integer.parseInt(input[1]);
                    int w = Integer.parseInt(input[2]);
                    c++;

                    g.addEdge(u, v, w);

                    if(w < 0) dijkstra = false;
                }else{
                    s = Integer.parseInt(input[0]);
                    d = Integer.parseInt(input[1]);
                }
            }

            reader.close();

            if(dijkstra) g.dijkstra(s, d);
            else g.bellmanFord(s, d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
