import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try{
            AVL avl = null;
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

            while(true){
                String in = reader.readLine();
                if(in == null) break;

                String[] input = in.split(" ");

                if(input[0].equals("I")) avl.insert(Integer.parseInt(input[1]));
                else if(input[0].equals("D")) avl.delete(avl.root, Integer.parseInt(input[1]));
                else if(input[0].equals("F")) avl.find(Integer.parseInt(input[1]));
            }

            reader.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
