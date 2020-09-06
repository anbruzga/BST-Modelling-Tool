package TreeModellingTool.Model;

public interface Node {
    // GETTERS SETTERS
    int getValue();

    void setValue(int value);

    Node getLeft();

    void setLeft(Node left);

    Node getRight();

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
