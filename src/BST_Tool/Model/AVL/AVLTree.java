package BST_Tool.Model.AVL;

import BST_Tool.Model.BST.AbstractBST;
import BST_Tool.Model.Node;
import BST_Tool.Model.Tree;

public class AVLTree extends AbstractBST implements Tree {

    private final String IDENTIFIER = "AVL";

    public AVLTree(){
        super.identifier = IDENTIFIER;
    }

    @Override
    public void addNode(Node node, boolean doTransitions) {

    }

    @Override
    protected Node deleteRec(Node root, int value) {
        return null;
    }

    @Override
    protected Node addRecursive(Node current, int value, Boolean doTransitions) {
        return null;
    }

    @Override
    public void addNode(int value, boolean doTransitions) {

    }

    @Override
    public boolean deleteNode(int value) {
        return false;
    }

    @Override
    public String getDiagram(Tree tree) {
        return null;
    }

    @Override
    public Tree deepCopy(Tree toCopy) {
        return null;
    }
}
