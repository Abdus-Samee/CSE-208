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

            System.out.println(g.mst(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
