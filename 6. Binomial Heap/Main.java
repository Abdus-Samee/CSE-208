import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            int c = 0, n = 0, m = 0;
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

            BinomialHeap binomialHeap = new BinomialHeap();

            while(true){
                String in = reader.readLine();
                if(in == null) break;

                String[] input = in.split(" ");
                String instruction = input[0];

                if(instruction.equals("INS")) binomialHeap.insert(Integer.parseInt(input[1]));
                else if(instruction.equals("PRI")) binomialHeap.levelOrderTraversal();
                else if(instruction.equals("EXT")){
                    BinomialNode max = binomialHeap.extractMax();
                    System.out.println("ExtractMax returned " + max.data);
                }else if(instruction.equals("FIN")){
                    System.out.println("FindMax returned " + binomialHeap.findMax());
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
