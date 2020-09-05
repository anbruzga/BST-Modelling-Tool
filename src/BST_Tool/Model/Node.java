package BST_Tool.Model;

import BST_Tool.Model.BST.BSTNode;

public interface Node {
    // GETTERS SETTERS
    int getValue();

    void setValue(int value);

    BSTNode getLeft();

    void setLeft(Node left);

    BSTNode getRight();

    void setRight(Node right);

    boolean isMarked();

    void setMarked(boolean marked);

    boolean isStacked();

    void setStacked(boolean stacked);

    @Override
    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
