package BST_Tool;


public class BSTNode {

    // VARIABLES
    private int value; // node data
    private BSTNode left; // left child
    private BSTNode right; // right child
    private boolean marked;
    private boolean stacked;

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

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isStacked() {
        return stacked;
    }

    public void setStacked(boolean stacked) {
        this.stacked = stacked;
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