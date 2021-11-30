import java.util.*;

public class Main {
    private static boolean[][] visited = new boolean[1000][1000];

    private static class Pair{
        int u, v;

        public Pair(int u, int v){
            this.u = u;
            this.v = v;
        }
    }

    public static void printPath(Map<Pair, Pair> mp, Pair dest){
        while(true){
            if(dest.u == 0 && dest.v == 0){
                System.out.println("0 0");
                break;
            }

            System.out.println(dest.u + " " + dest.v);

            dest = mp.get(dest);
        }
    }

    public static void solve(int a, int b, int d){
        //can keep a parent attribute in the class Pair instead of Map
        Map<Pair, Pair> mp = new HashMap<>();
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(0, 0));

        while(!queue.isEmpty()){
            Pair pair = queue.peek();
            queue.poll();

            if(!visited[pair.u][pair.v]){
                visited[pair.u][pair.v] = true;

                if(pair.u == d || pair.v == d){
                    if(pair.u == d && pair.v != 0){
                        Pair p = new Pair(pair.u, 0);
                        mp.put(p, pair);
                        System.out.println("Path is:");
                        printPath(mp, p);
                    }else if(pair.v == d && pair.u != 0){
                        Pair p = new Pair(0, pair.v);
                        mp.put(p, pair);
                        System.out.println("Path is:");
                        printPath(mp, p);
                    }

                    return;
                }

                Pair addSecond = new Pair(pair.u, b);
                queue.add(addSecond);
                if(!mp.containsKey(addSecond)) mp.put(addSecond, pair);
                Pair addFirst = new Pair(a, pair.v);
                queue.add(addFirst);
                if(!mp.containsKey(addFirst)) mp.put(addFirst, pair);
                Pair emptySecond = new Pair(a, 0);
                queue.add(emptySecond);
                if(!mp.containsKey(emptySecond)) mp.put(emptySecond, pair);
                Pair emptyFirst = new Pair(0, b);
                queue.add(emptyFirst);
                if(!mp.containsKey(emptyFirst)) mp.put(emptyFirst, pair);

                int transferFromFirst = Math.min(pair.u, b-pair.v);
                int a1 = pair.u-transferFromFirst;
                int b1 = pair.v+transferFromFirst;
                if(a1>=0 && b1<=b){
                    Pair p = new Pair(a1, b1);
                    if(!mp.containsKey(p)){
                        mp.put(p, pair);
                        queue.add(p);
                    }
                }

                int transferFromSecond = Math.min(a-pair.u, b);
                int a2 = pair.u+transferFromSecond;
                int b2 = pair.v-transferFromSecond;

                if(b2>=0 && a2<=a){
                    Pair p = new Pair(a2, b2);
                    if(!mp.containsKey(p)){
                        mp.put(p, pair);
                        queue.add(p);
                    }
                }
            }
        }

        System.out.println("Not Possible...");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int d = scanner.nextInt();

        solve(a, b, d);
    }
}
