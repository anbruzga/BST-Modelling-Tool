package BST_Tool;


public class BSTNode {

    // VARIABLES
    private int value; // node data
    private BSTNode left; // left child
    private BSTNode right; // right child

    // CONSTRUCTOR
    public BSTNode(int elem) {
        value = elem;
        left = null;
        right = null;
    }


    // GETTERS SETTERS
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BSTNode getLeft() {
        return left;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BSTNode{" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}