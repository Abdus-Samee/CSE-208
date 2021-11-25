import java.util.Scanner;

class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int d = scanner.nextInt();

        Graph g = new Graph(n);

        scanner.nextLine();
        for(int i = 0; i < d; i++){
            String[] input = scanner.nextLine().split(" ");
            int s1 = Integer.parseInt(input[0]);
            int s2 = Integer.parseInt(input[1]);

            g.addEdge(s1, s2);
        }

        g.traverse();
    }
}
