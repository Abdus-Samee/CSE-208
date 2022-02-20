public class AVL {
    boolean heightInvariant;
    Node root;

    public AVL(){
        this.heightInvariant = true;
        this.root = null;
    }

    public void find(int data){
        Node node = this.root;
        String found = "False";

        while(node != null){
            if(node.data == data){
                found = "True";
                break;
            }
            else if(node.data < data) node = node.right;
            else node = node.left;
        }

        System.out.println(found);
    }

    public void insert(int data){
        this.root = insertHelper(this.root, data);
        if(!this.heightInvariant){
            this.heightInvariant = true;
            System.out.printf("Height invariant violated.\nAfter rebalancing: ");
        }
        printAVL(this.root);
        System.out.println();
    }

    public Node delete(Node parent, int data){
        if(parent == null) return null;

        if(data < parent.data) parent.left = delete(parent.left, data);
        else if(data > parent.data) parent.right = delete(parent.right, data);
        else{
            if(parent.left == null && parent.right == null){
                return null;
            }else if(parent.left == null){
                parent = parent.right;
                return parent;
            }else if(parent.right == null){
                parent = parent.left;
                return parent;
            }else{
                Node temp = parent.right;

                while(temp.left != null) temp = temp.left;

                parent.data = temp.data;
                parent.right = delete(parent.right, temp.data);
            }
        }
        heightBalance(parent);
        return rotate(parent);
    }

    public void printAVL(Node parent){
        if(parent == null) return;

        System.out.printf("" + parent.data);

        if(parent.left == null && parent.right == null) return;

        System.out.printf("(");
        printAVL(parent.left);
        System.out.printf(")(");
        printAVL(parent.right);
        System.out.printf(")");
    }

    public Node insertHelper(Node parent, int data){
        if(parent == null){
            parent = new Node(data);
            return parent;
        }

        if(data < parent.data) parent.left = insertHelper(parent.left, data);
        else if(data > parent.data) parent.right = insertHelper(parent.right, data);

        heightBalance(parent);
        return rotate(parent);
    }

    public Node rotate(Node node){
        int balance = checkBalance(node);
        if (balance > 1) {
            this.heightInvariant = false;
            if (checkBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balance < -1) {
            this.heightInvariant = false;
            if (checkBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    private int checkBalance(Node node){ return node != null ? height(node.left) - height(node.right) : 0; }

    private Node rotateRight(Node node) {
        Node leftNode = node.left;
        Node centerNode = leftNode.right;
        leftNode.right = node;
        node.left = centerNode;
        heightBalance(node);
        heightBalance(leftNode);
        return leftNode;
    }

    private Node rotateLeft(Node node) {
        Node rightNode = node.right;
        Node centerNode = rightNode.left;
        rightNode.left = node;
        node.right = centerNode;
        heightBalance(node);
        heightBalance(rightNode);
        return rightNode;
    }

    public void heightBalance(Node node){
        int maximumHeight = Math.max(height(node.left), height(node.right));
        node.height = maximumHeight+1;
    }

    private int height(Node node){ return node == null? -1 : node.height; }
}
