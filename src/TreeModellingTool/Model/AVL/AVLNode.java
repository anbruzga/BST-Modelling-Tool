package TreeModellingTool.Model.AVL;

import TreeModellingTool.Model.BST.BSTNode;
import TreeModellingTool.Model.Node;

public class AVLNode implements Node {

    // VARIABLES
    private int value; // node data
    private AVLNode left; // left child
    private AVLNode right; // right child
    private boolean marked;
    private boolean stacked;
    private int height;
    private int bf;

    public AVLNode(int value) {
        this.value = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public AVLNode getLeft() {
        return left;
    }


    @Override
    public void setLeft(Node left) {
        this.left = (AVLNode) left;
        this.height = ((AVLNode)left).getHeight();
    }

    public void setLeft(AVLNode left){
        this.left = left;
    }

    @Override
    public AVLNode getRight() {
        return right;
    }

    @Override
    public void setRight(Node right) {
        this.right = (AVLNode) right;
        this.right.height = ((AVLNode)right).getHeight();
    }

    public void setRight(AVLNode right){
        this.right = right;
    }

    public int getBf() {
        return bf;
    }

    public void setBf(int bf) {
        this.bf = bf;
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
        return "AVLNode{" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                ", height=" + height +
                '}';
    }
}
