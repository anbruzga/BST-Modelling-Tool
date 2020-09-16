package TreeModellingTool.Model.RedBlack;

import TreeModellingTool.Model.Node;

public class RedBlackNode implements Node {

    private Integer value; // holds the key
    private RedBlackNode parent; // pointer to the parent
    private RedBlackNode left; // pointer to left child
    private RedBlackNode right; // pointer to right child
    private int color; // 1 . Red, 0 . Black
    private boolean stacked = false;
    private boolean marked = false;


    public RedBlackNode(Integer value){
        if (value == Integer.MIN_VALUE) {
            this.value = null;
        }
        else this.value = value;
    }

    public RedBlackNode(){}

    @Override
    public int getValue() {
        if (value == null){
            return Integer.MIN_VALUE;
        }
        return value;
    }

    @Override
    public void setValue(int value) {
       setValue((Integer) value);
    }

    private void setValue(Integer value) {
        if (value == Integer.MIN_VALUE) {
            this.value = null;
        }
        else this.value = value;
    }

    public RedBlackNode getParent() {
        return parent;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    @Override
    public RedBlackNode getLeft() {
        return left;
    }

    @Override
    public void setLeft(Node left) {
        this.left = (RedBlackNode) left;
    }

    @Override
    public RedBlackNode getRight() {
        return right;
    }

    @Override
    public void setRight(Node right) {
        this.right = (RedBlackNode) right;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
