package TreeModellingTool.Model;

import java.util.List;

public interface Tree {
    // PUBLIC
    void addNode(int value, boolean doTransitions);

    boolean deleteNode(int value);

    String showMinNode();

    Node findMinNode(Node root);

    String showMaxNode();

    Node findNode(int nodeValueToFind, boolean addTransitions);

    String getDiagram();

    String getDiagram(Tree tree);

    void balanceTree();

    List<Integer> getPreOrder(boolean doTransitions);

    List<Integer> getPostOrder();

    List<Integer> getInOrder();

    void clear();

    Node getRoot();

    Tree deepCopy(Tree toCopy);

    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    String printAllNodes();
}
