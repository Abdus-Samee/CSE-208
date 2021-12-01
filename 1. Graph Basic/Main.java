import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String[] in = scanner.nextLine().split(" ");
        int n = Integer.parseInt(in[0]);
        int m = Integer.parseInt(in[1]);
        
        Graph graph = new Graph(n);
        
        for(int i = 1; i <= m; i++){
            String[] input = scanner.nextLine().split(" ");
            int u = Integer.parseInt(input[0]);
            int v = Integer.parseInt(input[1]);
            
            graph.addEdge(u, v);
        }
        
        System.out.println(graph.dfsUtil());
    }
}
