import java.util.*;

public class HashTable {
    private int n, prev_prime, val1, val2, val3;
    private int colSeparateChaining1, colSeparateChaining2;
    private int colDoubleHashing1, colDoubleHashing2;
    private int probeSeparateChaining1, probeSeparateChaining2;
    private int probeDoubleHashing1, probeDoubleHashing2;
    private List<LinkedList<Node>> arr1;
    private List<LinkedList<Node>> arr2;
    private List<Node> arr3;
    private List<Node> arr4;

    public HashTable(int n){
        this.n = n;
        this.arr1 = new ArrayList<>();
        this.arr2 = new ArrayList<>();
        this.arr3 = new ArrayList<>();
        this.arr4 = new ArrayList<>();

        for(int i = 0; i < n; i++) this.arr1.add(new LinkedList<>());
        for(int i = 0; i < n; i++) this.arr2.add(new LinkedList<>());
        for(int i = 0; i < this.n; i++){
            this.arr3.add(new Node("", 0));
            this.arr4.add(new Node("", 0));
        }

//        this.prev_prime = n;
//        if(n%2 == 0) this.prev_prime--;
//        while(true){
//            if(isPrime(this.prev_prime)) break;
//            this.prev_prime -= 2;
//        }
    }

    public int hash1(String s){
        //sdbm
        int hash = 0;

        for(int i = 0; i < s.length(); i++) hash = (int)s.charAt(i) + (hash << 6) + (hash << 16) - hash;

        return Math.abs(hash%this.n);
    }

    public int hash2(String s){
        //djb2
        int hash = 5381;

        for(int i = 0; i < s.length(); i++) hash = ((hash << 5) + hash) + (int)s.charAt(i); /* hash * 33 + c */

        return Math.abs(hash%this.n);
    }

    public int auxHash(String s){
        int hash = 0;
        for(int i = 0; i < s.length(); i++){
            hash += s.charAt(i);
        }

        return hash%this.n;
    }

    public void insertSeparateChaining(String s){
        int idx1 = hash1(s);
        if(!this.arr1.get(idx1).isEmpty()) this.colSeparateChaining1++;
        this.arr1.get(idx1).add(new Node(s, this.val1++));

        int idx2 = hash2(s);
        if(!this.arr2.get(idx2).isEmpty()) this.colSeparateChaining2++;
        this.arr2.get(idx2).add(new Node(s, this.val1++));
    }

    public void insertDoubleHashing(String s){
        int idxAux = auxHash(s);
        int idx1 = hash1(s);
        int idx2 = hash2(s);

        for(int i = 0; i < this.n; i++){
            int valHash1 = (idx1 + i * idxAux)%this.n;
            if(this.arr3.get(valHash1).key.isEmpty()){
                this.arr3.set(valHash1, new Node(s, this.val2++));
                break;
            }
            else this.colDoubleHashing1++;
        }

        for(int i = 0; i < this.n; i++){
            int valHash2 = (idx2 + i * idxAux)%this.n;
            if(this.arr4.get(valHash2).key.isEmpty()){
                this.arr4.set(valHash2, new Node(s, this.val3++));
                break;
            }
            else this.colDoubleHashing2++;
        }
    }

    public void searchSeparateChaining(String s){
        int idx1 = hash1(s);
        Iterator iterator = this.arr1.get(idx1).iterator();
        while(iterator.hasNext()){
            Node node = (Node) iterator.next();
            if(node.key.equals(s)){
                this.probeSeparateChaining1++;
                break;
            }
            this.probeSeparateChaining1++;
        }

        int idx2 = hash2(s);
        iterator = this.arr2.get(idx2).iterator();
        while(iterator.hasNext()){
            Node node = (Node) iterator.next();
            if(node.key.equals(s)){
                this.probeSeparateChaining2++;
                break;
            }
            this.probeSeparateChaining2++;
        }
    }

    public void searchDoubleHashing(String s){
        int idxAux = auxHash(s);
        int idx1 = hash1(s);
        int idx2 = hash2(s);

        for(int i = 0; i < this.n; i++){
            int valHash1 = (idx1 + i * idxAux)%this.n;
            if(this.arr3.get(valHash1).key.equals(s)){
                this.probeDoubleHashing1++;
                break;
            }
            this.probeDoubleHashing1++;
        }

        for(int i = 0; i < this.n; i++){
            int valHash2 = (idx2 + i * idxAux)%this.n;
            if(this.arr4.get(valHash2).key.equals(s)){
                this.probeDoubleHashing2++;
                break;
            }
            this.probeDoubleHashing2++;
        }

    }

    public void reportSeparateChaining(){
        System.out.println("---Separate Chaining---");
        System.out.println(this.colSeparateChaining1 + " " + this.colSeparateChaining2);
        System.out.println(this.probeSeparateChaining1 + " " + this.probeSeparateChaining2);
    }

    public void reportDoubleHashing(){
        System.out.println("---Double Hashing---");
        System.out.println(this.colDoubleHashing1 + " " + this.colDoubleHashing2);
        System.out.println(this.probeDoubleHashing1 + " " + this.probeDoubleHashing2);
    }

    public boolean isPrime(int n){
        int c = 0;
        for(int i = 2; i < n; i++){
            if(n%i == 0) c++;
        }

        return (c == 0)? true : false;
    }

    public boolean isDuplicate(String s){
        boolean duplicate = false;

        int idx = hash1(s);
        Iterator iterator = this.arr1.get(idx).iterator();
        while(iterator.hasNext()){
            Node node = (Node)iterator.next();
            if(node.key.equals(s)){
                duplicate = true;
                break;
            }
        }

        return duplicate;
    }
}
