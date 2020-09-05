package BST_Tool.Model.RedBlack;

import BST_Tool.Model.BST.AbstractBST;
import BST_Tool.Model.BST.BSTNode;
import BST_Tool.Model.Tree;

public class RedBlackTree extends AbstractBST implements Tree {


    @Override
    public void addNode(BSTNode node, boolean doTransitions) {

    }

    @Override
    protected BSTNode deleteRec(BSTNode root, int value) {
        return null;
    }

    @Override
    protected BSTNode addRecursive(BSTNode current, int value, Boolean doTransitions) {
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
