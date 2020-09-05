package BST_Tool.Model;

import BST_Tool.Model.BST.BSTNode;

import java.util.List;

public interface Tree {
    // PUBLIC
    void addNode(int value, boolean doTransitions);

    boolean deleteNode(int value);

    String showMinNode();

    BSTNode findMinNode(BSTNode root);

    String showMaxNode();

    BSTNode findNode(int nodeValueToFind, boolean addTransitions);

    String getDiagram();

    String getDiagram(Tree tree);

    void balanceTree();

    List<Integer> getPreOrder(boolean doTransitions);

    List<Integer> getPostOrder();

    List<Integer> getInOrder();

    void clear();

    BSTNode getRoot();

    Tree deepCopy(Tree toCopy);

    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    String printAllNodes();
}
