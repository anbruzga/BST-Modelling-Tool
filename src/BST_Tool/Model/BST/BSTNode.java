package BST_Tool.Model.BST;


import BST_Tool.Model.Node;

public class BSTNode implements Node {

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
    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public BSTNode getLeft() {
        return left;
    }

    @Override
    public void setLeft(Node left) {
        this.left = (BSTNode) left;
    }

    @Override
    public BSTNode getRight() {
        return right;
    }

    @Override
    public void setRight(Node right) {
        this.right = (BSTNode) right;
    }

    @Override
    public boolean isMarked() {
        return marked;
    }

    @Override
    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public boolean isStacked() {
        return stacked;
    }

    @Override
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BSTNode bstNode = (BSTNode) o;

        if (value != bstNode.value) return false;
        if (left != null ? !left.equals(bstNode.left) : bstNode.left != null) return false;
        return right != null ? right.equals(bstNode.right) : bstNode.right == null;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (left != null ? left.hashCode() : 0);
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }
}