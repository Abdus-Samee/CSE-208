import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            int c = -1, n = 0, m = 0, s = 0, d = 0;
            Graph g = null;
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

            while(true){
                String in = reader.readLine();
                if(in == null) break;

                String[] input = in.split(" ");

                if(c == -1){
                    n = Integer.parseInt(input[0]);
                    g = new Graph(n);
                    c++;
                }else{
                    String team = input[0];
                    int win = Integer.parseInt(input[1]);
                    int loss = Integer.parseInt(input[2]);
                    int remaining = Integer.parseInt(input[3]);
                    g.addStat(c, team, win, loss, remaining);

                    for(int i = 0; i < n; i++){
                        g.addEdge(c, i, Integer.parseInt(input[4+i]));
                    }

                    c++;
                }
            }

            reader.close();

            g.baseballMaxFlow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
