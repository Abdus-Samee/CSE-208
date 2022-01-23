public class BinomialNode {
    int data;
    int degree;
    BinomialNode parent;
    BinomialNode leftChild;
    BinomialNode rightSibling;

    public BinomialNode(int data){
        this.data = data;
        this.degree = 0;
        this.parent = null;
        this.leftChild = null;
        this.rightSibling = null;
    }
}
