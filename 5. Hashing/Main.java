import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int c = 1;
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        HashTable hashTable = new HashTable(n);
        List<String> words= new ArrayList<>();

        while(c <= 10000){
            String word = generate();
            if(!words.contains(word)){
                words.add(word);
                hashTable.insertSeparateChaining(word);
                hashTable.insertDoubleHashing(word);
                c++;
            }
        }

        Random random = new Random();
        for(int i = 1; i <= 1000; i++){
            String searching = words.get(random.nextInt(10000));
            hashTable.searchSeparateChaining(searching);
            hashTable.searchDoubleHashing(searching);
        }

        hashTable.reportSeparateChaining();
        hashTable.reportDoubleHashing();
    }

    public static String generate(){
        char[] word = new char[7];
        Random random = new Random();

        for(int i = 0; i < 7; i++){
            word[i] = (char)('a'+random.nextInt(26));
        }

        return new String(word);
    }
}
