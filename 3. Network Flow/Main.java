import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            int c = -1, n = 0, m = 0, x = 0, y = 0, p =0, s = 0, d = 0;
            Graph g = null;
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

            while(true){
                String in = reader.readLine();
                if(in == null) break;

                String[] input = in.split(" ");

                if(c == -1){
                    m = Integer.parseInt(input[0]);
                    n = Integer.parseInt(input[1]);
                    c++;
                }else if(c == 0){
                    x = Integer.parseInt(input[0]);
                    y = Integer.parseInt(input[1]);

                    g = new Graph(x+y+2);
                    g.setCardinality(m, n, x, y);
                    c++;
                }else if(c == 1){
                    p = Integer.parseInt(input[0]);
                    g.setPairs(p);
                    c++;
                }else{
                    s = Integer.parseInt(input[0]);
                    d = Integer.parseInt(input[1]);
                    g.addEdge(0, s+1, n);
                    g.addEdge(x+d+1, x+y+1, n);
                    g.addEdge(s+1, x+d+1, m);

                    c++;
                }
            }

            reader.close();

            g.networkFlow();
            } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
