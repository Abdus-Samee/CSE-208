import java.util.ArrayList;

public class BinomialHeap {
    private BinomialNode maximum;
    private int n;
    private BinomialNode head;

    public BinomialHeap(){
        this.maximum = null;
        this.n = 0;
        this.head = null;
    }

    public void insert(int x){
        BinomialNode binomialNode = new BinomialNode(x);
        BinomialHeap binomialHeap = new BinomialHeap();
        binomialHeap.head = binomialNode;
        BinomialHeap newHeap = union(binomialHeap);
        this.head = newHeap.head;

        //updating maximum value
        BinomialNode max = this.head;
        BinomialNode cur = newHeap.head.rightSibling;
        while(cur != null){
            if(cur.data > max.data) max = cur;
            cur = cur.rightSibling;
        }
        this.maximum = max;

        System.out.println("Inserted " + x);
    }

    public BinomialHeap union(BinomialHeap binomialHeap){
        BinomialHeap newheap = mergeBinomialHeaps(binomialHeap);
        if(newheap == null) return null;

        BinomialNode prev = null;
        BinomialNode cur = newheap.head;
        BinomialNode next = cur.rightSibling;
        while(next != null){
            boolean unequal = cur.degree != next.degree;
            boolean equal = (next.rightSibling != null) && (next.degree == next.rightSibling.degree);
            boolean traverse = unequal || equal;

            if(traverse){
                prev = cur;
                cur = next;
            }else{
                if(cur.data >= next.data){
                    cur.rightSibling = next.rightSibling;
                    linkBinomialNodes(cur, next);
                }else{
                    if(prev == null) newheap.head = next;
                    else prev.rightSibling = next;

                    linkBinomialNodes(next, cur);
                    cur = next;
                }
            }

            next = next.rightSibling;
        }

        return newheap;
    }

    public BinomialHeap mergeBinomialHeaps(BinomialHeap binomialHeap){
        if(this.head == null) return binomialHeap;
        if(binomialHeap.head == null) return this;

        BinomialNode newHead = null;
        BinomialNode cur1 = this.head;
        BinomialNode cur2 = binomialHeap.head;

        if(cur1.degree < cur2.degree){
            newHead = cur1;
            cur1 = cur1.rightSibling;
        }else{
            newHead = cur2;
            cur2 = cur2.rightSibling;
        }

        BinomialNode cur = newHead;
        while(cur1 != null && cur2 != null){
            if(cur1.degree <= cur2.degree){
                cur.rightSibling = cur1;
                cur1 = cur1.rightSibling;
            }else{
                cur.rightSibling = cur2;
                cur2 = cur2.rightSibling;
            }

            cur = cur.rightSibling;
        }

        if(cur1 != null) cur.rightSibling = cur1;
        else if(cur2 != null) cur.rightSibling = cur2;

        BinomialHeap newHeap = new BinomialHeap();
        newHeap.head = newHead;
        newHeap.n = this.n + binomialHeap.n;

        return newHeap;
    }

    public void linkBinomialNodes(BinomialNode a, BinomialNode b){
        b.rightSibling = a.leftChild;
        b.parent= a;
        a.leftChild = b;
        a.degree += 1;
    }

    public int findMax() { return this.maximum.data; }

    public BinomialNode extractMax(){
        if(this.maximum == null){
            System.out.println("Priority Queue is empty...");
            return null;
        }

        //removing max node from heap
        BinomialNode cur = this.head;
        BinomialNode prev = null;
        BinomialNode max = this.head;
        BinomialNode prevMax =  null;
        while(cur != null){
            if(cur.data > max.data){
                max = cur;
                prevMax = prev;
            }
            prev = cur;
            cur = cur.rightSibling;
        }

        if(prevMax == null) this.head = this.head.rightSibling;
        else prevMax.rightSibling = max.rightSibling;

        //correcting the heap
        this.n -= 1;
        if(max.leftChild != null){
            BinomialHeap childHeap = new BinomialHeap();
            childHeap.head = max.leftChild;
            BinomialHeap newHeap = union(childHeap);
            this.head = newHeap.head;
        }

        //updating maximum value
        if(this.head == null) this.maximum = null;
        else{
            BinomialNode maxNode = this.head;
            cur = this.head.rightSibling;
            while(cur != null){
                if(cur.data > maxNode.data) maxNode = cur;
                cur = cur.rightSibling;
            }
            this.maximum = maxNode;
        }

        return max;
    }

    public void increaseKey(int prevKey, int newKey){
        if(prevKey >= newKey){
            System.out.println("Provided key cannot be increased...");
            return;
        }

        BinomialNode searchedKey = search(this.head, prevKey);

        if(searchedKey == null){
            System.out.println("Provided key not found...");
            return;
        }

        searchedKey.data = newKey;
        BinomialNode cur = searchedKey;
        BinomialNode par = searchedKey.parent;
        while((par != null) && (par.data < cur.data)){
            par.data = par.data + cur.data;
            cur.data = par.data - cur.data;
            par.data = par.data - cur.data;
            cur = par;
            par = par.parent;
        }

        //updating maximum value
        BinomialNode maxNode = this.head;
        cur = this.head.rightSibling;
        while(cur != null){
            if(cur.data > maxNode.data) maxNode = cur;
            cur = cur.rightSibling;
        }
        this.maximum = maxNode;

        System.out.println("Increased " + prevKey + ". The updated value is " + newKey + ".");
    }

    public BinomialNode search(BinomialNode cur, int data){
        if(cur == null) return null;

        if(cur.data == data) return cur;

        BinomialNode searchNode = search(cur.leftChild, data);
        if(searchNode == null) return search(cur.rightSibling, data);

        return searchNode;
    }

    public void print(){
        System.out.println("Printing Binomial Heap...");
        System.out.println("-------------------------");

        BinomialNode cur = this.head;
        while(cur != null){
            ArrayList<ArrayList<BinomialNode>> res = levelOrderTraversal(cur.leftChild);
            System.out.println("Binomial Tree, B" + res.size());
            System.out.println("Level 0 : " + cur.data);
            for(int i = 0; i < res.size(); i++){
                System.out.printf("Level " + (i+1) + " :");
                for(BinomialNode node : res.get(i)) System.out.printf(" " + node.data);
                System.out.println();
            }
            cur = cur.rightSibling;
        }
        System.out.println("-------------------------");
    }

    public ArrayList<ArrayList<BinomialNode>> levelOrderTraversal(BinomialNode cur){
        int level = 1;
        ArrayList<ArrayList<BinomialNode>> levelNodes = new ArrayList<>();
        while(cur != null){
            //System.out.printf("Level " + level + " : ");
            BinomialNode node = cur;
            ArrayList<BinomialNode> arr = new ArrayList<>();
            while(node != null){
                //System.out.printf(" " + node.data);
                arr.add(node);
                node = node.rightSibling;
            }
            level += 1;
            levelNodes.add(arr);
            cur = cur.leftChild;
        }

        //System.out.println("Binomial Tree, B" + level);
        return levelNodes;
    }
}
