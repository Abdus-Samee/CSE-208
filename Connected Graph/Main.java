import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int v = scanner.nextInt();
        int e = scanner.nextInt();
        scanner.nextLine();

        Graph graph = new Graph(v);

        for(int i = 1; i <= e; i++){
            String[] input = scanner.nextLine().split(" ");
            int s1 = Integer.parseInt(input[0]);
            int s2 = Integer.parseInt(input[1]);

            graph.addEdge(s1, s2);
        }

        if(graph.isConnected(0)) System.out.println("Connected...");
        else System.out.println("Not connected...");
    }
}
